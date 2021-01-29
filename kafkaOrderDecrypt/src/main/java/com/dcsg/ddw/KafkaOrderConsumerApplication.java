package com.dcsg.ddw;

import static java.util.stream.Collectors.groupingBy;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.context.annotation.Bean;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.kafka.support.SendResult;

import com.dcsg.ddw.ord.OrderDDW;
import com.dcsg.ddw.ord.OrderHeaderDDW;
import com.dcsg.ddw.ord.OrderLineDDW;
import com.dcsg.ddw.ord.OrdersDDW;
import com.dcsg.ddw.transform.DDWOrderBuilder;
import com.dsg.customerorder.avro.Address;
import com.dsg.customerorder.avro.FulfillmentAddress;
import com.dsg.customerorder.avro.FulfillmentDetails;
import com.dsg.customerorder.avro.FulfillmentLocation;
import com.dsg.customerorder.avro.LineItem;
import com.dsg.customerorder.avro.Order;
import com.dsg.customerorder.avro.Payment;
import com.dsg.customerorder.avro.Person;
import com.dsg.customerorder.avro.ShippingDetails;
import com.dsg.customerorder.avro.State;
import com.dsg.customerorder.avro.TaxDetails;

import org.apache.commons.lang.SerializationException;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.json.JSONObject;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.dcsg.ddw.util.RSACryptoUtil;
import com.dcsg.ddw.util.InterfaceAdapter;

import lombok.extern.slf4j.Slf4j;



@Slf4j
@SpringBootApplication
public class KafkaOrderConsumerApplication implements CommandLineRunner { 
	
    @Value("${spring.kafka.output.topic}")
	  private String outputTopic;
    
    @Value("${rsa.privatekey}")
    private String rsaPrivateKeyFile;    
	
	@Autowired
	private DDWOrderBuilder ddwOrderBuilder; 
    @Autowired
    private OraSink oraSink;
    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;
    


    private RSACryptoUtil rsaCryptoUtil;
	
	
	public static void main(String[] args) throws Exception{
		 //ConfigurableApplicationContext context = SpringApplication.run(KafkaOrderConsumerApplication.class, args);
		 //MessageListener listener = context.getBean(MessageListener.class);
		 //listener.greetingLatch.await(10, TimeUnit.SECONDS);
	     //context.close();
		
		SpringApplication.run(KafkaOrderConsumerApplication.class, args);
		
	}

	
	 @Override
	 public void run(String...  args) {
		 
	 }

	 @Bean
	    public MessageListener messageListener() {
	        return new MessageListener();
	    }
	 
	    public class MessageListener {
	    	
	    	/*test
	    	  abstract class IgnoreSchemaProperty
	    	  {
	    	    // You have to use the correct package for JsonIgnore,
	    	    // fasterxml or codehaus
	    	    @JsonIgnore abstract void getSchema();
	    	  }

	    	
	    	
	    	*/


	        //private CountDownLatch greetingLatch = new CountDownLatch(1);

	        @KafkaListener(topics = "co-orders", containerFactory = "kafkaListenerContainerFactory")
	        //public void orderListener(Order o) {
	        public void orderListener( String order, 
	        		 @Header(KafkaHeaders.RECEIVED_MESSAGE_KEY) String key,
	        		 @Header(KafkaHeaders.RECEIVED_PARTITION_ID) Integer partition,
	        		 @Header(KafkaHeaders.OFFSET) Long offset,	        		 
	        		Acknowledgment ack
	        		 ) 	        	        
	        {
	        
	        	
	        	

	        	
	        	try
	        	{
		        	//System.out.printf("Records returned: %d%n", orders.size());
		            //this.greetingLatch.countDown();
		        	if(order!= null) //need null check?
		        	{

			        	 
		        		//System.out.println("Current Thread ID- " + Thread.currentThread().getId() + " For Thread- " + Thread.currentThread().getName());   
		        		
			        	Order orderObj = null;	        	     
			       	   
			        	try {
			        	        
			        		/*test --fail
			        		orderObj = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                                            .configure(DeserializationFeature.ACCEPT_EMPTY_ARRAY_AS_NULL_OBJECT, true)
                                            .addMixIn(Address.class, IgnoreSchemaProperty.class)
                                            .addMixIn(Person.class, IgnoreSchemaProperty.class)
                                            .addMixIn(Payment.class, IgnoreSchemaProperty.class)
            						        .addMixIn(TaxDetails.class, IgnoreSchemaProperty.class)
            						        .addMixIn(ShippingDetails.class, IgnoreSchemaProperty.class)
            						        .addMixIn(FulfillmentAddress.class, IgnoreSchemaProperty.class)
            						        .addMixIn(FulfillmentLocation.class, IgnoreSchemaProperty.class)
            						        .addMixIn(FulfillmentDetails.class, IgnoreSchemaProperty.class)
					        	            .readValue(order, Order.class);

			        		
			        		

			        	    */         
						        	        
			        		
			        		//orig
			        		orderObj = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
			        	             .readValue(order, Order.class);
			        	        
			        	        
			        		/* works!  old
			        	        Gson gson = new GsonBuilder().create();
							    String jsonString = gson.toJson(orderObj);
							    System.out.println(jsonString);
			        	        */

			        	        
			        	        /*failed using interface adaptor
			        	        GsonBuilder builder = new GsonBuilder();
			        	        builder.registerTypeAdapter(Order.class, new InterfaceAdapter());
			        	        Gson gson = builder.create();
			        	       
			        	       

			        	        Order o = gson.fromJson(order, Order.class); 
			        	        
							    String jsonString = gson.toJson(order);
							    System.out.println(jsonString);
							    
							     */
			        	
			        	 
			               } catch (Exception e) { e.getMessage();}
			        	   	
			        	processOrder(key, orderObj);
			        	 
			        	 
		        	}
	
			        	//consumer.commitAsync(); //old logic
		        
		        	//uncomment 1/27/21
		        	ack.acknowledge();  //new logic; passed basic test
	        	}catch(Exception e)
	        	{
	        		log.error("ERROR: " + e.getLocalizedMessage());
	        		log.debug("ERROR: " + e.getLocalizedMessage());
	        		
		        		log.error("ERROR-NotProcessed: partition: {}  offset:{}" ,
		                         partition, offset); 
				    //log.info("Push the messaged to Error Stream : " + e);
	        	}
	        	finally{}
	        	
	        }
	        
			 private Boolean processOrder(String key, Order order) {
				 

				 //OrdersDDW ordersDDW = new OrdersDDW() ; //will hold all orders to be inserted to DDW
				 
				 
		           // if( order.getState().equals(State.placed) )
		            		//|| order.getState().equals(State.canceled) || order.getState().equals(State.in_review) ) //|| o.getSource().equals(Source.DonorsChoose)
		           // 	ddwOrderBuilder.setOrder(order); //skip this call if just decrypting
		            
		           // {
		            	
			        	//decryptPIIAndEncryptWithRSA(order);	
			        	produceDDWOrders(key, order);
		            	
		           // }
		            
		           
		          
					 /*	code for oracle sink processing			 
					 try {

						  //transform avro order to DDW order
					      OrderDDW ddwOrder = ddwOrderBuilder.generateDdwOrder(order); 
					      
					      //add order to master list
					      ordersDDW.getOrderHeaders().add(ddwOrder.getOrderHeader());
					      ordersDDW.getOrderLines().addAll(ddwOrder.getOrderLines());
					      ordersDDW.getOrderPayment().addAll(ddwOrder.getOrderPayment());
					      			   
					      //Gson gson = new GsonBuilder().create();
					      //String jsonString = gson.toJson(ddwOrder.getOrderLines());
					      //System.out.println(jsonString);
					      
					      
					      //log.info("Sent EOM Format to EOM Topic");
					    } catch (Exception e) {
					      log.error("ERROR: Cannot produce DDW order for key: {}", order.getOrderNumber());
					      log.info("Exception While Producing DDW Format");
					    }
					 
		            
			
				 
	                 //  sink to oracle
				     if(ordersDDW.getOrderHeaders().size() > 0) 
				         oraSink.sink(ordersDDW); //check return code for kafka commit //plus output failed messages? 
				  
                     */
				return true;
				 
			 }
			 
			 
			  private void decryptPIIAndEncryptWithRSA(Order order) {
				    try {
				      rsaCryptoUtil = new RSACryptoUtil(
				          rsaPrivateKeyFile
				      );
				      //Order orderObj = new ObjectMapper().readValue(order, Order.class);

				      //log.info("Decrypted first name is {}",rsaCryptoUtil.decryptFromBase64(order.getCustomerDetails().getFirstName().toString()));
				      //log.info("Decrypted email id is {}", rsaCryptoUtil.decryptFromBase64(order.getCustomerDetails().getEmail().toString()));
				      
				      //log.info("first name is {}",order.getCustomerDetails().getFirstName().toString());
				      //order.getCustomerDetails().setFirstName(rsaCryptoUtil.decryptFromBase64(order.getCustomerDetails().getFirstName().toString()));
				      //log.info("Decrypted first name is {}",order.getCustomerDetails().getFirstName().toString());
				      
				      //log.info("Order number is {}",order.getOrderNumber());
				      
				      if(order.getCustomerDetails().getAddress().getAddress1() != null)
				       order.getCustomerDetails().getAddress().setAddress1(rsaCryptoUtil.decryptFromBase64(order.getCustomerDetails().getAddress().getAddress1().toString()));
				      if(order.getCustomerDetails().getAddress().getAddress2() != null)
				       order.getCustomerDetails().getAddress().setAddress2(rsaCryptoUtil.decryptFromBase64(order.getCustomerDetails().getAddress().getAddress2().toString()));
				      if(order.getCustomerDetails().getAddress().getAddress3() != null)
				       order.getCustomerDetails().getAddress().setAddress3(rsaCryptoUtil.decryptFromBase64(order.getCustomerDetails().getAddress().getAddress3().toString()));
				      
				      if(order.getCustomerDetails().getAddress().getCity() != null)
				       order.getCustomerDetails().getAddress().setCity(rsaCryptoUtil.decryptFromBase64(order.getCustomerDetails().getAddress().getCity().toString()));
				      if(order.getCustomerDetails().getAddress().getState() != null)
				       order.getCustomerDetails().getAddress().setState(rsaCryptoUtil.decryptFromBase64(order.getCustomerDetails().getAddress().getState().toString()));
				      if(order.getCustomerDetails().getAddress().getPostalCode() != null)
				       order.getCustomerDetails().getAddress().setPostalCode(rsaCryptoUtil.decryptFromBase64(order.getCustomerDetails().getAddress().getPostalCode().toString()));
				      if(order.getCustomerDetails().getAddress().getCountry() != null)
				       order.getCustomerDetails().getAddress().setCountry(rsaCryptoUtil.decryptFromBase64(order.getCustomerDetails().getAddress().getCountry().toString()));
				      
				      
				      Map<CharSequence, List<LineItem>> lineMap = order.getLineItems().stream()
					          .collect(groupingBy(lineItem -> lineItem.getExternalItemIdentifier()));

					      for (CharSequence c : lineMap.keySet()) {
					        LineItem lineItem = lineMap.get(c).get(0);
					        
					        
					        if(lineItem.getShippingDetails().getShippingRecipient().getAddress().getAddress1() != null)
					        	lineItem.getShippingDetails().getShippingRecipient().getAddress().setAddress1(rsaCryptoUtil.decryptFromBase64(lineItem.getShippingDetails().getShippingRecipient().getAddress().getAddress1().toString()));
					        if(lineItem.getShippingDetails().getShippingRecipient().getAddress().getAddress2() != null)
					        	lineItem.getShippingDetails().getShippingRecipient().getAddress().setAddress2(rsaCryptoUtil.decryptFromBase64(lineItem.getShippingDetails().getShippingRecipient().getAddress().getAddress2().toString()));
					        if(lineItem.getShippingDetails().getShippingRecipient().getAddress().getAddress3() != null)
					        	lineItem.getShippingDetails().getShippingRecipient().getAddress().setAddress3(rsaCryptoUtil.decryptFromBase64(lineItem.getShippingDetails().getShippingRecipient().getAddress().getAddress3().toString()));					        
					        if(lineItem.getShippingDetails().getShippingRecipient().getAddress().getCity() != null)
					          lineItem.getShippingDetails().getShippingRecipient().getAddress().setCity(rsaCryptoUtil.decryptFromBase64(lineItem.getShippingDetails().getShippingRecipient().getAddress().getCity().toString()));
					        if(lineItem.getShippingDetails().getShippingRecipient().getAddress().getState() != null)
					          lineItem.getShippingDetails().getShippingRecipient().getAddress().setState(rsaCryptoUtil.decryptFromBase64(lineItem.getShippingDetails().getShippingRecipient().getAddress().getState().toString()));
					        if(lineItem.getShippingDetails().getShippingRecipient().getAddress().getPostalCode() != null)
						          lineItem.getShippingDetails().getShippingRecipient().getAddress().setPostalCode(rsaCryptoUtil.decryptFromBase64(lineItem.getShippingDetails().getShippingRecipient().getAddress().getPostalCode().toString()));					        
					        if(lineItem.getShippingDetails().getShippingRecipient().getAddress().getCountry() != null)
						          lineItem.getShippingDetails().getShippingRecipient().getAddress().setCountry(rsaCryptoUtil.decryptFromBase64(lineItem.getShippingDetails().getShippingRecipient().getAddress().getCountry().toString()));

					        /*should already be decrypted
					        if(lineItem.getFulfillmentDetails() != null)
					        {
					        
					        if(lineItem.getFulfillmentDetails().getFulfillmentLocation().getFulfillmentAddress().getAddress1() != null)
					        	lineItem.getFulfillmentDetails().getFulfillmentLocation().getFulfillmentAddress().setAddress1(rsaCryptoUtil.decryptFromBase64(lineItem.getFulfillmentDetails().getFulfillmentLocation().getFulfillmentAddress().getAddress1().toString()));
					        if(lineItem.getFulfillmentDetails().getFulfillmentLocation().getFulfillmentAddress().getAddress2() != null)
					        	lineItem.getFulfillmentDetails().getFulfillmentLocation().getFulfillmentAddress().setAddress2(rsaCryptoUtil.decryptFromBase64(lineItem.getFulfillmentDetails().getFulfillmentLocation().getFulfillmentAddress().getAddress2().toString()));
					        if(lineItem.getFulfillmentDetails().getFulfillmentLocation().getFulfillmentAddress().getAddress3() != null)
					        	lineItem.getFulfillmentDetails().getFulfillmentLocation().getFulfillmentAddress().setAddress3(rsaCryptoUtil.decryptFromBase64(lineItem.getFulfillmentDetails().getFulfillmentLocation().getFulfillmentAddress().getAddress3().toString()));
					        if(lineItem.getFulfillmentDetails().getFulfillmentLocation().getFulfillmentAddress().getCity() != null);
					            lineItem.getFulfillmentDetails().getFulfillmentLocation().getFulfillmentAddress().setCity(rsaCryptoUtil.decryptFromBase64(lineItem.getFulfillmentDetails().getFulfillmentLocation().getFulfillmentAddress().getCity().toString()));
					        if(lineItem.getFulfillmentDetails().getFulfillmentLocation().getFulfillmentAddress().getState() != null);
					            lineItem.getFulfillmentDetails().getFulfillmentLocation().getFulfillmentAddress().setState(rsaCryptoUtil.decryptFromBase64(lineItem.getFulfillmentDetails().getFulfillmentLocation().getFulfillmentAddress().getState().toString()));
					        if(lineItem.getFulfillmentDetails().getFulfillmentLocation().getFulfillmentAddress().getPostalCode() != null)
					        	lineItem.getFulfillmentDetails().getFulfillmentLocation().getFulfillmentAddress().setPostalCode(rsaCryptoUtil.decryptFromBase64(lineItem.getFulfillmentDetails().getFulfillmentLocation().getFulfillmentAddress().getPostalCode().toString()));
					        if(lineItem.getFulfillmentDetails().getFulfillmentLocation().getFulfillmentAddress().getCountry() != null)
					        	lineItem.getFulfillmentDetails().getFulfillmentLocation().getFulfillmentAddress().setCountry(rsaCryptoUtil.decryptFromBase64(lineItem.getFulfillmentDetails().getFulfillmentLocation().getFulfillmentAddress().getCountry().toString()));
					        }
					        */
					      }
				      
				      //

				    }catch(JsonMappingException jme){
				      log.error("Exception in mapping {}", jme);
				    } catch (JsonProcessingException e) {
				      log.error("Exception in mapping {}", e);
				    } catch (Exception e) {
				      log.error("Exception in decrypting {}", e);
				    }
				  }
			  
			  //
			  private void produceDDWOrders(String key, Order ord) {
				    
				    try {

				      //System.out.println("producer called!");
				      //TXML eomOrder = eomXmlBuilder.generateEomXml(avroOrder);
				      //log.info("Key is {} and topic is {} ", key, outputTopic);
				      
				      
				      //ProducerRecord<String, Object> record = new ProducerRecord(outputTopic, key,
				      //    eomXmlBuilder.marshallXml(eomOrder));
				      
	      
				       sendMessage(key, ord);
				   
				      
				      //log.info("Sent EOM Format to EOM Topic");
				    } catch (Exception e) {
				      log.error("Cannot produce EOM message for key: {}", key);
				      log.info("Exception While Producing EOM Format");
				    }
				  }
			  
			  
			  public void sendMessage( String key, Order ord) {
				  
				  try {
					  
					    /*test - fails
				        ObjectMapper om = new ObjectMapper();				
				        om.addMixIn(Address.class, IgnoreSchemaProperty.class);
				        om.addMixIn(Person.class, IgnoreSchemaProperty.class);
				        om.addMixIn(Payment.class, IgnoreSchemaProperty.class);
				        om.addMixIn(TaxDetails.class, IgnoreSchemaProperty.class);
				        om.addMixIn(ShippingDetails.class, IgnoreSchemaProperty.class);
				        om.addMixIn(FulfillmentAddress.class, IgnoreSchemaProperty.class);
				        om.addMixIn(FulfillmentLocation.class, IgnoreSchemaProperty.class);
				        om.addMixIn(FulfillmentDetails.class, IgnoreSchemaProperty.class);				        
				        
				        kafkaTemplate.send(outputTopic, om.writeValueAsString(ord));
	        	        */
				        
					    //works
					    Gson gson = new GsonBuilder().serializeNulls().create();
					    kafkaTemplate.send(outputTopic,key,gson.toJson(ord));
				  }
					  
					    catch (SerializationException e) {
						     log.error("SerializationException sending data to producer" + e.toString());
						    }
				        catch (Exception e) { 
			    	        log.error("Error in sending data to producer" + e.toString());
		    	}

			 

	    
	    
	


	    }
			  
			  //
			  
			  
			  /* tested async send...ran slower
			  private void produceDDWOrders(String key, Order ord) {
				         
				       sendMessage(key, ord);				  				      

				  }
			  
			  
			  public void sendMessage( String key, Order ord) {
				  

					    Gson gson = new GsonBuilder().serializeNulls().create();
					    ListenableFuture<SendResult<String, String>> future = kafkaTemplate.send(outputTopic,key,gson.toJson(ord));
					    
					    future.addCallback(new ListenableFutureCallback<SendResult<String, String>>() {

					        @Override
					        public void onSuccess(SendResult<String, String> result) {
					        	;
					            //System.out.println("Sent message=[" + "T" + 
					            //  "] with offset=[" + result.getRecordMetadata().offset() + "]");
					        }
					        @Override
					        public void onFailure(Throwable ex) {
					            System.out.println("Unable to send message=[" 
					              + "T" + "] due to : " + ex.getMessage());
					        }
					    });



	    }*/
}}

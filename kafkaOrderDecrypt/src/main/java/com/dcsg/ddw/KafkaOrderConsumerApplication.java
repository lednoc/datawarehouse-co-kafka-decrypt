package com.dcsg.ddw;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.context.annotation.Bean;
import org.springframework.kafka.annotation.KafkaListener;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.listener.AbstractConsumerSeekAware;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.util.StopWatch;

import org.springframework.kafka.support.Acknowledgment;
import org.springframework.kafka.support.KafkaHeaders;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.commons.lang.SerializationException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.dsg.customerorder.avro.LineItem;
import com.dsg.customerorder.avro.Order;
import com.dcsg.ddw.util.RSACryptoUtil;

import lombok.extern.slf4j.Slf4j;



@Slf4j
@SpringBootApplication
public class KafkaOrderConsumerApplication implements CommandLineRunner { 
	
    @Value("${spring.kafka.output.topic}")
	  private String outputTopic;    

    @Value("${rsa.privatekey}")
    private String rsaPrivateKeyFile;    
    
    @Value("${server.port}")
    private int serverPort;
	
	//@Autowired
	//private DDWOrderBuilder ddwOrderBuilder; 
    //@Autowired
    //private OraSink oraSink;
    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;
    
    private RSACryptoUtil rsaCryptoUtil;
    
	
	
	public static void main(String[] args) throws Exception{

		SpringApplication.run(KafkaOrderConsumerApplication.class, args);
		
	}

	
	 @Override
	 public void run(String...  args) {
		 
	 }

	    @Bean
	    public MessageListener messageListener() {
	        return new MessageListener();
	    }
	 
	    public class MessageListener extends AbstractConsumerSeekAware{
	    	
	    	

	    	
	    	 /*can use to manually set offsets.  will most likely need in DR senerio.  can use seektotimestamp()
	    	 @Override
	    	    public void onPartitionsAssigned(Map<TopicPartition, Long> assignments, ConsumerSeekCallback callback) {
	    	        // Seek all the assigned partition to a certain offset `10234575L`
	    	        //assignments.keySet().forEach(tp -> callback.seekToEnd(tp.topic(), tp.partition()));
	    		 assignments.keySet().forEach(tp -> callback.seekToBeginning(tp.topic(), tp.partition()));
	    	    }	    	
	    	*/


	    	

	        @KafkaListener(id="ddw-order-consumer-spring", topics ="#{@inTopicName}", containerFactory = "kafkaListenerContainerFactory")
	        public void orderListener( Order order, //consume message from input topic
	        		 @Header(KafkaHeaders.RECEIVED_MESSAGE_KEY) String key,
	        		 @Header(KafkaHeaders.RECEIVED_PARTITION_ID) Integer partition,
	        		 @Header(KafkaHeaders.OFFSET) Long offset,
	        		 @Header(KafkaHeaders.RECEIVED_TIMESTAMP) Long ts,

	        		Acknowledgment ack
	        		 ) 	        	        
	        {
	        
	        	
	        	
	        	try
	        	{

		        	if(order!= null) //need null check?
		        	{
                        
		        		System.out.printf("processing:  %s%n", key);
			        	 
		        		//System.out.println("Current Thread ID- " + Thread.currentThread().getId() + " For Thread- " + Thread.currentThread().getName());   		        					        
			        	//StopWatch stopWatch = new StopWatch("KafkaTimer");
			        	//stopWatch.start("initializing");
		        		
		        		decryptPIIWithRSA(order);	
	                    produceDecryptOrders(key, ts, order);
	                    
			        	//stopWatch.stop();
			        	//System.out.println(stopWatch.prettyPrint());
			        	 
		        	}
	
		        	
		        	ack.acknowledge();  //record was processed, so commit offset
	        	}catch(Exception e)
	        	{
	        		log.error("ERROR: " + e.toString());	        			
		        	log.error("ERROR-NotProcessed:  key: {} partition: {}  offset: {}" ,
		        			key, partition, offset); 

	        	}
	        	finally{}
	        	
	        }
	        
		 
			 
			  private void decryptPIIWithRSA(Order order) {
				    try {
				      rsaCryptoUtil = new RSACryptoUtil(
				          rsaPrivateKeyFile
				      );
	
				      
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
				      
				      
						  for (LineItem lineItem : order.getLineItems()) {
						        
					        
					        if(lineItem.getShippingDetails().getShippingRecipient().getAddress().getAddress1() != null )
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

					      }
				      


				    }catch(JsonMappingException jme){
				      log.error("Exception in mapping {}", jme);
				    } catch (JsonProcessingException e) {
				      log.error("Exception in mapping {}", e);
				    } catch (Exception e) {
				      log.error("Exception in decrypting {}", e);
				    }
				  }
			  
			  
			  private void produceDecryptOrders(String key, Long ts, Order ord) {
				    
				    try {

				        //System.out.println("producer called!");				    	
				    	Gson gson = new GsonBuilder().serializeNulls().create();
					    kafkaTemplate.send(outputTopic, null, ts, key,gson.toJson(ord));

				   
				      

				    }
				    catch (SerializationException e) {
					     log.error("SerializationException sending data to producer" + e.toString());
					     throw e;
					    }
			        catch (Exception e) { 
		    	        log.error("Error in sending data to producer" + e.toString());
		    	        throw e;
	    	         }

				    
				    
				  }
			  

}}

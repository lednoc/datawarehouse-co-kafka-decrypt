package com.dcsg.ddw.config;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.kafka.clients.CommonClientConfigs;
import org.apache.kafka.clients.consumer.CommitFailedException;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRebalanceListener;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.config.SslConfigs;
import org.apache.kafka.common.errors.WakeupException;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.listener.ContainerProperties.AckMode;
import org.springframework.kafka.listener.BatchErrorHandler;
import org.springframework.kafka.listener.BatchLoggingErrorHandler;
import org.springframework.kafka.listener.ConsumerRecordRecoverer;
import org.springframework.kafka.listener.DeadLetterPublishingRecoverer;
import org.springframework.kafka.listener.RetryingBatchErrorHandler;
import org.springframework.kafka.listener.SeekToCurrentBatchErrorHandler;
import org.springframework.kafka.listener.SeekToCurrentErrorHandler;
import org.springframework.kafka.support.serializer.ErrorHandlingDeserializer2;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;
import org.springframework.stereotype.Component;
import org.springframework.util.backoff.FixedBackOff;

import com.dsg.customerorder.avro.Order;
//import com.dcsg.ddw.config.KafkaProducerConfig;

import io.confluent.kafka.serializers.KafkaAvroDeserializer;
import io.confluent.kafka.serializers.KafkaAvroDeserializerConfig;

import lombok.extern.slf4j.Slf4j;


@Slf4j
//orig @Component

//test
@EnableKafka
@Configuration
public class KafkaConsumerFactoryDDW {
	
	  @Value("${spring.kafka.schema.registry.url}")
	  private String schemaRegistryUrl;

	  @Value("${spring.kafka.application.id}")
	  private String applicationId;

	  @Value("${spring.kafka.bootstrap.servers}")
	  private String bootstrapServers;

	  @Value("${spring.kafka.serializer.key}")
	  private String keySerializer;

	  @Value("${spring.kafka.deserializer.key}")
	  private String keyDeserializer;
	  
	  @Value("${spring.kafka.deserializer.value}")
	  private String valueDeserializer;

	  @Value("${spring.kafka.serializer.output.value}")
	  private String outputValueSerializer;

	  //@Value("${spring.kafka.serializer.input.value}")
	 // private String inputValueDeserializer;

	  @Value("${spring.kafka.auto.offset.reset}")
	  private String offsetReset;

	  @Value("${spring.kafka.input.topic}")
	  private String inputTopic;

	  @Value("${spring.kafka.output.topic}")
	  private String outputTopic;
	  
	  @Value("${spring.kafka.consumer.group-id}")
	  private String groupId;
	  
	  @Value("${spring.kafka.client-id}")
	  private String clientId;
	  
	  @Value("${spring.kafka.listener.ack-mode}")
	  private String ackMode;


	  @Value("${spring.kafka.security.protocol}")
	  private String securityProtocol;

	  @Value("${spring.kafka.security.ssl.enabled.protocols}")
	  private String enabledProtocols;

	  @Value("${spring.kafka.security.ssl.keystore.location}")
	  private String keystoreLocation;

	  @Value("${spring.kafka.security.ssl.keystore.password}")
	  private String keystorePassword;

	  @Value("${spring.kafka.security.ssl.key.password}")
	  private String keyPassword;

	  @Value("${spring.kafka.security.ssl.truststore.location}")
	  private String truststoreLocation;

	  @Value("${spring.kafka.security.ssl.truststore.password}")
	  private String truststorePassword;
	  
	  @Value("${spring.kafka.sasl.mechanism}")
	  private String sasl_mechanism;
	  
	  @Value("${spring.kafka.sasl.jaas.config}")
	  private String sasl_jaas_config;
	  
	  
	  @Value("${spring.kafka.security.protocol}")
	  private String security_protocol;
	  
	  @Value("${spring.kafka.ssl.endpoint.identification.algorithm}")
	  private String ssl_end_id_algorithm;
	  
	  
	  @Autowired
	  private KafkaTemplate<String, String> kafkaTemplate;
	  
	    @Bean
	    public String inTopicName() {
	        return inputTopic;
	    }
	  

	  public ConsumerFactory<String, Order> consumerFactory() {  //orig String, Object
		  
		  
	        Map<String, Object> props = new HashMap<>();
	        
	        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest"); //used when group 1st initialized; no committed yet 
	        
	        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
	        props.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
	        

	        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, keyDeserializer);            
	        //orig props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, valueDeserializer);
	        
	        //test
	        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonSerializer.class); 
	        
	        
	        /*
										
					When the ErrorHandlingDeserializer2 detects a deserialization exception, these are forwarded to the listener container, which sends them directly to the error handler
					
					If deserial error occurs there is no value() field in the ConsumerRecord (because it couldn't be deserialized).
					The failure is put into one of two headers: ErrorHandlingDeserializer2.VALUE_DESERIALIZER_EXCEPTION_HEADER or ErrorHandlingDeserializer2.KEY_DESERIALIZER_EXCEPTION_HEADER.
					
					You can obtain the details with
					
					Header header = record.headers().lastHeader(headerName);
					DeserializationException ex = (DeserializationException) new ObjectInputStream(
					    new ByteArrayInputStream(header.value())).readObject();
					with the original payload in ex.getData().
			 */
	        
	        props.put(ErrorHandlingDeserializer2.KEY_DESERIALIZER_CLASS, StringDeserializer.class);
	        props.put(ErrorHandlingDeserializer2.VALUE_DESERIALIZER_CLASS, StringDeserializer.class);
	    
	            	       	    
	        
	        props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG,"false"); //false, so need to set ackmode in container	        
	        props.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG,"500");  //10 for dev/testing  //500 is default
	        
	        // time consumer can be out of contact w broker.
	        //not sure sweetspot here?
	        
	        //following maybe were set on non-platform kafka?
	        //props.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG,"50000"); 
	        //props.put(ConsumerConfig.HEARTBEAT_INTERVAL_MS_CONFIG,"10000"); //should be 1/3 session timeout
	        
	        props.put("client.id", clientId + "_" + System.currentTimeMillis());


	        
	        /* 10/20/20 use to connect to platform, dont use w devcon.yml
	        props.put(CommonClientConfigs.SECURITY_PROTOCOL_CONFIG, securityProtocol);
	        props.put(SslConfigs.SSL_TRUSTSTORE_LOCATION_CONFIG, truststoreLocation);
	        props.put(SslConfigs.SSL_TRUSTSTORE_PASSWORD_CONFIG, truststorePassword);
	        props.put(SslConfigs.SSL_KEY_PASSWORD_CONFIG, keyPassword);
	        props.put(SslConfigs.SSL_KEYSTORE_PASSWORD_CONFIG, keystorePassword);
	        props.put(SslConfigs.SSL_KEYSTORE_LOCATION_CONFIG, keystoreLocation);
	        */    
	        
	        //10/20/20 new
	        props.put(CommonClientConfigs.SECURITY_PROTOCOL_CONFIG, security_protocol);
	        props.put(SslConfigs.SSL_ENDPOINT_IDENTIFICATION_ALGORITHM_CONFIG, ssl_end_id_algorithm);
	        props.put("sasl.jaas.config", sasl_jaas_config);
	        props.put("sasl.mechanism", sasl_mechanism);


	        //
	        
	        //6/29 commented out following //look into using for err handle?
	        //props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, ErrorHandlingDeserializer2.class);
	        //props.put(ErrorHandlingDeserializer2.VALUE_FUNCTION, KafkaErrorProvider.class);  
	        
	        
	        //not using schema reg on platform
	        //props.put("schema.registry.url", schemaRegistryUrl);

	        //orig return new DefaultKafkaConsumerFactory<>(props);
            //test
	        
	        return new DefaultKafkaConsumerFactory<>(props,new StringDeserializer(),
	                new JsonDeserializer<>(Order.class));
	         
	         
	    }
      @Bean
      public ConcurrentKafkaListenerContainerFactory<String, Order>  //was String, String //orig String, Order
        kafkaListenerContainerFactory() {
      
          ConcurrentKafkaListenerContainerFactory<String, Order> factory =  //was String, String   //orig String, Order
            new ConcurrentKafkaListenerContainerFactory<>();
          factory.setConsumerFactory(consumerFactory());
          
          //If we have scaling issues; can try to use the following property. 
          //Can set the number of consumers to match the # of partitions in the topic 
          //orig uncommented 1/27/21
          factory.setConcurrency(5);
          
          factory.getContainerProperties().setPollTimeout(5000); //5000 is default; if u dont set this property
          factory.setBatchListener(false);  //return orders in batches
          
          //AckMode.MANUAL if use, would need to call ack() after processing batch
          //AckMode.BATCH = when the listener returns after handling the last message from the poll, the offset is committed.
          //not sure if should do BATCH instead?
          
          //orig uncommented 1/27/21 //didn't work when comment out, could be due to max poll change too!?
          //set to batch in .yml, need to see which setting is used  //thinking manual will give more control
          factory.getContainerProperties().setAckMode(AckMode.MANUAL); //msg listener must handle offset commits w call to ack.acknowledge()
          
 
          
          
          factory.setErrorHandler(new SeekToCurrentErrorHandler(new DeadLetterPublishingRecoverer(kafkaTemplate)));
          
          //need to log err?
          //factory.setBatchErrorHandler(new BatchLoggingErrorHandler()); //I think u can define own custom f() instead of BathcLoggingErrorHandler
           
          /*
             The SeekToCurrentErrorHandler discards remaining records from the poll() and performs seek operations on the consumer to 
             reset the offsets so that the discarded records are fetched again on the next poll. 
             By default, the error handler tracks the failed record, gives up after 10 delivery attempts, and logs the failed record. 
             However, we can also send the failed message to another topic. We call this a dead letter topic, named  topicName.DLT.
             
             //fixedbackoff() is passed time to delay and how many times to retry
              
         */

          
          return factory;
      }
      
      
      @Bean
		public DeadLetterPublishingRecoverer recoverer() {
			return new DeadLetterPublishingRecoverer(kafkaTemplate,
					(record, ex) -> new TopicPartition("ddw-co-order-decrypt-dlq", -1)); //topic for orders that could not be processed
		}
	 
      /*
      @Bean
      public KafkaTemplate<String, String> kafkaTemplate() {
          return new KafkaTemplate<>(producerFactory());
      }
      */
      

//need this?
//	public SchemaRegistryClient getSchemaRegistryClient() {
//		return new CachedSchemaRegistryClient(kConf.getRegistryServer(), 255);}

}

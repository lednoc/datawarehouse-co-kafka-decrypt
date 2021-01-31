package com.dcsg.ddw.config;

import org.apache.kafka.clients.CommonClientConfigs;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.config.SslConfigs;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaProducerConfig {

	  @Value("${spring.kafka.bootstrap.servers}")
	  private String bootstrapServers;
	  
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
	  


    @Bean
    public ProducerFactory<String, String> orderProducerFactory() {
        Map<String, Object> configProps = new HashMap<>();
        configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class); 
        
        //if max batch size or linger is hit, a batch will be sent.
        configProps.put(ProducerConfig.LINGER_MS_CONFIG, 100); //default 0.  is the delay time before the batches are ready to be sent. if 0 batch sent immediately, even if only 1 msg
        configProps.put(ProducerConfig.BATCH_SIZE_CONFIG, 65536); //16384 is default.  max size of a batch
        
        configProps.put(ProducerConfig.BUFFER_MEMORY_CONFIG,33554432); //33mb buffer messages are sent to

        configProps.put(ProducerConfig.ACKS_CONFIG,"1");
        configProps.put(ProducerConfig.RETRIES_CONFIG,"1"); //retry 1 more time if a commit fails
        //
        
        
        
        /* 10/20/20 use to connect to platform, dont use w devcon.yml
        configProps.put(CommonClientConfigs.SECURITY_PROTOCOL_CONFIG, securityProtocol);
        configProps.put(SslConfigs.SSL_TRUSTSTORE_LOCATION_CONFIG, truststoreLocation);
        configProps.put(SslConfigs.SSL_TRUSTSTORE_PASSWORD_CONFIG, truststorePassword);
        configProps.put(SslConfigs.SSL_KEY_PASSWORD_CONFIG, keyPassword);
        configProps.put(SslConfigs.SSL_KEYSTORE_PASSWORD_CONFIG, keystorePassword);
        configProps.put(SslConfigs.SSL_KEYSTORE_LOCATION_CONFIG, keystoreLocation);        
        */
        //10/20/20 new
        configProps.put(CommonClientConfigs.SECURITY_PROTOCOL_CONFIG, security_protocol);
        configProps.put(SslConfigs.SSL_ENDPOINT_IDENTIFICATION_ALGORITHM_CONFIG, ssl_end_id_algorithm);
        configProps.put("sasl.jaas.config", sasl_jaas_config);
        configProps.put("sasl.mechanism", sasl_mechanism);
        
        
        
        //needed to write to topic in devcon i.e ccloud
        //orig the code created the topic if didn't exist, now have to create manually first.
        configProps.put("listeners", "PLAINTEXT://pkc-epzq5.eastus.azure.confluent.cloud:9092");
        configProps.put("advertised.listeners", "PLAINTEXT://pkc-epzq5.eastus.azure.confluent.cloud:9092");
        
        //test
        //configProps.put("listeners", "PLAINTEXT://localhost:9092");
        //configProps.put("advertised.listeners", "PLAINTEXT://pkc-epzq5.eastus.azure.confluent.cloud:9092");
 


        
        
        return new DefaultKafkaProducerFactory<>(configProps);
    }

    @Bean
    public KafkaTemplate<String, String> orderKafkaTemplate() {
        return new KafkaTemplate<>(orderProducerFactory());
    }

}
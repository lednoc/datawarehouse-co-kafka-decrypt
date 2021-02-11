package com.dcsg.ddw;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.config.KafkaListenerEndpointRegistry;
import org.springframework.kafka.listener.MessageListenerContainer;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ConsumerController {

	@Value("${spring.kafka.consumer.group-id}")
	 private String groupId;

	@Autowired
	private KafkaListenerEndpointRegistry kafkaListenerEndpointRegistry;
	
	@GetMapping(path = "/start")
	public String start() {
		MessageListenerContainer listenerContainer = kafkaListenerEndpointRegistry.getListenerContainer("ddw-order-consumer-spring");
		listenerContainer.start();
		return "started listening";
	}

	@GetMapping(path = "/stop")
	public String stop() {
		MessageListenerContainer listenerContainer = kafkaListenerEndpointRegistry.getListenerContainer("ddw-order-consumer-spring");
		listenerContainer.stop();
		return "stopped listening";
	}

}

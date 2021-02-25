package com.dcsg.ddw;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.config.KafkaListenerEndpointRegistry;
import org.springframework.kafka.listener.ContainerProperties;
import org.springframework.kafka.listener.MessageListenerContainer;
import org.springframework.kafka.support.TopicPartitionOffset;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dcsg.ddw.util.OffsetManageUtl;

@RestController
public class ConsumerController {  //Endpoints to start/stop consumer on-demand.
	
	
	@Value("${spring.kafka.consumer.group-id}")
	 private String groupId;

	@Autowired
	private KafkaListenerEndpointRegistry kafkaListenerEndpointRegistry;

    @Autowired
    private OffsetManageUtl omu;

	
	
	//If need to manually update offsets, pass in an epoch timestamp so it can be accessed/used by consumers onPartitionsAssigned()
	@RequestMapping(value = {"/start", "/start/{time}"})
	public String start(@PathVariable(required = false) Long time) {
		
		if(time == null)
		{
	       omu.setTimestamp(0L);
		   MessageListenerContainer listenerContainer = kafkaListenerEndpointRegistry.getListenerContainer("ddw-order-consumer-spring");
		   listenerContainer.start();
		}else
		{  //set offsets based on timestamp
			
            omu.setTimestamp(time.longValue());  
			MessageListenerContainer listenerContainer = kafkaListenerEndpointRegistry.getListenerContainer("ddw-order-consumer-spring");
			listenerContainer.start();
		}

		return "started listening";
	}

	@GetMapping(path = "/stop")
	public String stop() {
		MessageListenerContainer listenerContainer = kafkaListenerEndpointRegistry.getListenerContainer("ddw-order-consumer-spring");
		listenerContainer.stop();
		return "stopped listening";
	}
		
	@GetMapping(path = "/pause")
		public String pause() {
			MessageListenerContainer listenerContainer = kafkaListenerEndpointRegistry.getListenerContainer("ddw-order-consumer-spring");
			listenerContainer.pause();
			return "paused listening";
		}
	

}

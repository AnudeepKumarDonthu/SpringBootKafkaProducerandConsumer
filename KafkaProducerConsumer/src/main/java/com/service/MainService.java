package com.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.stereotype.Service;

import com.util.ApplicationProperties;
import com.util.KafkaUtil;

@Service
public class MainService {
	
	@Autowired
	private ApplicationProperties applicationProperties;
	
	@Autowired
	private KafkaUtil kafkaUtil;
	
	public MainService() {
		
	}
	
	public void publishMessgae(Map<Object,Object> publishData) {
		kafkaUtil.publishMessage(applicationProperties.getProperty("kafka.topic.practice"),publishData);
	}
	
	public void startKafkaServer() {
		kafkaUtil.startKafkaConsumer();
	}

}

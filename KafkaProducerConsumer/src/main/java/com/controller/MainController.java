package com.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.service.MainService;
import com.util.ApplicationProperties;

@RestController
public class MainController {
	
	@Autowired
	private MainService MainService;

	@RequestMapping(value = "/health",method = RequestMethod.GET)
	public String health() {
		return "Application is Running";
	}

	@RequestMapping(value = "/publishmessage",method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.TEXT_PLAIN_VALUE)
	public String publishMessageToKafka(@RequestBody Map<Object,Object> messageJson) {
		MainService.publishMessgae(messageJson);
		return "Message Publihed successfully to Kafka";
	}
	
	@RequestMapping(value = "/startkafkaconsumer",method = RequestMethod.GET)
	public String startKafkaServer() {
		MainService.startKafkaServer();
		return "Kafka Cnonsumer Started Successfully";
	}

}

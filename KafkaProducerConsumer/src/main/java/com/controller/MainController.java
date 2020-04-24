package com.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.service.MainService;

@RestController
public class MainController {

	@Autowired
	private MainService MainService;

	@RequestMapping(value = "/health", method = RequestMethod.GET)
	public ResponseEntity<String> health() {
		return new ResponseEntity<>("Application is Running", HttpStatus.OK);
	}

	@RequestMapping(value = "/publishmessage", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.TEXT_PLAIN_VALUE)
	public ResponseEntity<String> publishMessageToKafka(@RequestBody Map<Object, Object> messageJson) {
		MainService.publishMessgae(messageJson);
		return new ResponseEntity<>("Message Publihed successfully to Kafka", HttpStatus.OK);
	}

	@RequestMapping(value = "/startkafkaconsumer", method = RequestMethod.GET)
	public ResponseEntity<String> startKafkaServer() {
		MainService.startKafkaServer();
		return new ResponseEntity<>("Kafka Cnonsumer Started Successfully", HttpStatus.OK);
	}

}

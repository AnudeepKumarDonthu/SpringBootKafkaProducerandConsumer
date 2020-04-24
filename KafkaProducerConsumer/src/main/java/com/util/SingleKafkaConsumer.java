package com.util;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.consumer.OffsetAndMetadata;
import org.apache.kafka.common.TopicPartition;

public class SingleKafkaConsumer {
	private KafkaConsumer<String, String> kafkaConsumer;

	public SingleKafkaConsumer(String theTechCheckTopicName, Properties consumerProperties) {

	    kafkaConsumer = new KafkaConsumer<>(consumerProperties);
	    kafkaConsumer.subscribe(Arrays.asList(theTechCheckTopicName));
	}
	
	public void runSingleWorker() {

	    while(true) {

	        @SuppressWarnings("deprecation")
			ConsumerRecords<String, String> records = kafkaConsumer.poll(100);

	        for (ConsumerRecord<String, String> record : records) {

	            String message = record.value();

	            System.out.println("Received message: " + message);

	           
	            {
	                Map<TopicPartition, OffsetAndMetadata> commitMessage = new HashMap<>();

	                commitMessage.put(new TopicPartition(record.topic(), record.partition()),
	                        new OffsetAndMetadata(record.offset() + 1));

	                kafkaConsumer.commitSync(commitMessage);

	            }
	        }
	    }
	}
}

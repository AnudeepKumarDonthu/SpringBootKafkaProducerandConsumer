package com.util;

import java.util.Map;
import java.util.Properties;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;

@Component
public class KafkaUtil {

	@Autowired
	private ApplicationProperties applicationProperties;

	private Gson gson = new Gson();

	public Properties getKafkaPublishProperties() {
		Properties properties = new Properties();
		properties.put("bootstrap.servers", applicationProperties.getProperty("kafka.bootstrap.servers"));
		properties.put("acks", "all");
		properties.put("retries", 0);
		properties.put("batch.size", 16384);
		properties.put("linger.ms", 1);
		properties.put("buffer.memory", 33554432);
		properties.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
		properties.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
		return properties;
	}

	public void publishMessage(String topicName, Map<Object, Object> payLoad) {
		KafkaProducer<String, String> producer = new KafkaProducer<>(getKafkaPublishProperties());
		System.out.println("Sending Kafka message: " + payLoad);
		producer.send(new ProducerRecord<>(topicName, gson.toJson(payLoad)));

	}

	public Properties getKafkaConsumerProperties() {
		Properties properties = new Properties();
		properties.put("bootstrap.servers", applicationProperties.getProperty("kafka.bootstrap.servers"));
		properties.put("group.id", applicationProperties.getProperty("zookeeper.groupId"));
		properties.put("zookeeper.session.timeout.ms", "6000");
		properties.put("zookeeper.sync.time.ms", "2000");
		properties.put("auto.commit.enable", "false");
		properties.put("auto.commit.interval.ms", "1000");
		properties.put("consumer.timeout.ms", "-1");
		properties.put("max.poll.records", "1");
		properties.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
		properties.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
		return properties;
	}

	public void startKafkaConsumer() {
		Thread kafkaConsumer = new Thread(() -> {
			SingleKafkaConsumer simpleKafkaConsumer = new SingleKafkaConsumer(
					applicationProperties.getProperty("kafka.topic.practice"), getKafkaConsumerProperties());
			simpleKafkaConsumer.runSingleWorker();
		},"kafkaConsumerThread");
		kafkaConsumer.start();
	}
}

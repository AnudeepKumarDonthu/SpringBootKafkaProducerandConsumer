package com.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;


@Component
public class ApplicationProperties {

	@Autowired
	private Environment environment;

	public String getProperty(String propertyKey) {
		String keyValues = environment.getProperty(propertyKey);
		return keyValues != null && !keyValues.isEmpty() ? keyValues : "";
	}

}

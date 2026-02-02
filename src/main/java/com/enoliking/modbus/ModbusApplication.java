package com.enoliking.modbus;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan
public class ModbusApplication {

	public static void main(String[] args) {
		SpringApplication.run(ModbusApplication.class, args);
	}

}

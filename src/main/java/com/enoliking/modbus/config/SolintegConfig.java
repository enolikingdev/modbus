package com.enoliking.modbus.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "solinteg")
public record SolintegConfig(String hostname, int port, int unitId) implements DeviceConfig {
}

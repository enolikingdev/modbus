package com.enoliking.modbus.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "honors")
public record HonorsConfig(String hostname, int port, int unitId) implements DeviceConfig {
}

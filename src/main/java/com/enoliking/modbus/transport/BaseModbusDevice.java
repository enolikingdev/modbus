
package com.enoliking.modbus.transport;

import com.digitalpetri.modbus.client.ModbusClient;
import com.digitalpetri.modbus.client.ModbusRtuClient;
import com.digitalpetri.modbus.exceptions.ModbusExecutionException;
import com.digitalpetri.modbus.tcp.client.NettyRtuClientTransport;
import com.enoliking.modbus.config.DeviceConfig;
import lombok.Getter;

public abstract class BaseModbusDevice implements ModbusDevice {

    protected final DeviceConfig config;

    @Getter
    private final ModbusClient client;

    protected BaseModbusDevice(DeviceConfig config) throws ModbusExecutionException {
        this.config = config;
        var transport = NettyRtuClientTransport.create(cfg -> {
            cfg.setHostname(config.hostname());
            cfg.setPort(config.port());
        });
        client = ModbusRtuClient.create(transport);
        client.connect();
    }

    @Override
    public int getUnitId() {
        return config.unitId();
    }
}

package com.enoliking.modbus.transport;

import com.digitalpetri.modbus.client.ModbusClient;
import com.digitalpetri.modbus.exceptions.ModbusExecutionException;
import com.enoliking.modbus.config.DeviceConfig;
import lombok.Getter;

public abstract class BaseModbusDevice implements ModbusDevice {

    protected final DeviceConfig config;

    @Getter
    private ModbusClient client;

    protected BaseModbusDevice(DeviceConfig config) throws ModbusExecutionException {
        this.config = config;
        client = getProtocolSpecificClient();
        client.connect();
    }

    protected abstract ModbusClient getProtocolSpecificClient();

    @Override
    public int getUnitId() {
        return config.unitId();
    }
}

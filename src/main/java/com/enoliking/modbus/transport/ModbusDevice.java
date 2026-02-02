package com.enoliking.modbus.transport;

import com.digitalpetri.modbus.client.ModbusClient;

public interface ModbusDevice {
    ModbusClient getClient();
    int getUnitId();
}

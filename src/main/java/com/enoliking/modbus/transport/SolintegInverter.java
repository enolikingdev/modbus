package com.enoliking.modbus.transport;

import com.digitalpetri.modbus.exceptions.ModbusExecutionException;
import com.enoliking.modbus.config.SolintegConfig;
import org.springframework.stereotype.Component;

@Component
public class SolintegInverter extends BaseModbusDevice {

    public SolintegInverter(SolintegConfig config) throws ModbusExecutionException {
        super(config);
    }
}

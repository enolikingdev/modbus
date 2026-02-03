package com.enoliking.modbus.transport;

import com.digitalpetri.modbus.client.ModbusClient;
import com.digitalpetri.modbus.client.ModbusRtuClient;
import com.digitalpetri.modbus.exceptions.ModbusExecutionException;
import com.digitalpetri.modbus.tcp.client.NettyRtuClientTransport;
import com.enoliking.modbus.config.SolintegConfig;
import org.springframework.stereotype.Component;

@Component
public class SolintegInverter extends BaseModbusDevice {

    public SolintegInverter(SolintegConfig config) throws ModbusExecutionException {
        super(config);
    }

    @Override
    protected ModbusClient getProtocolSpecificClient() {
        var transport = NettyRtuClientTransport.create(cfg -> {
            cfg.setHostname(config.hostname());
            cfg.setPort(config.port());
        });
        return ModbusRtuClient.create(transport);
    }
}

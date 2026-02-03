package com.enoliking.modbus.transport;

import com.digitalpetri.modbus.client.ModbusClient;
import com.digitalpetri.modbus.client.ModbusTcpClient;
import com.digitalpetri.modbus.exceptions.ModbusExecutionException;
import com.digitalpetri.modbus.tcp.client.NettyTcpClientTransport;
import com.enoliking.modbus.config.HonorsConfig;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Component
@Primary
public class HonorsCharger extends BaseModbusDevice {

    public HonorsCharger(HonorsConfig config) throws ModbusExecutionException {
        super(config);
    }

    @Override
    protected ModbusClient getProtocolSpecificClient() {
        var transport = NettyTcpClientTransport.create(cfg -> {
            cfg.setHostname(config.hostname());
            cfg.setPort(config.port());
        });
        return ModbusTcpClient.create(transport);
    }
}

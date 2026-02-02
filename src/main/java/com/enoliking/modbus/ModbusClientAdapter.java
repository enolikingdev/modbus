package com.enoliking.modbus;

import com.digitalpetri.modbus.exceptions.ModbusExecutionException;
import com.digitalpetri.modbus.exceptions.ModbusResponseException;
import com.digitalpetri.modbus.exceptions.ModbusTimeoutException;
import com.digitalpetri.modbus.pdu.ReadHoldingRegistersRequest;
import com.digitalpetri.modbus.pdu.ReadHoldingRegistersResponse;
import com.enoliking.modbus.transport.ModbusDevice;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ModbusClientAdapter {

    private final ModbusDevice device;

    public ReadHoldingRegistersResponse readHoldingRegisters(int address, int quantity)
            throws ModbusExecutionException, ModbusTimeoutException, ModbusResponseException {
        return device.getClient().readHoldingRegisters(
                device.getUnitId(),
                new ReadHoldingRegistersRequest(address, quantity)
        );
    }

}

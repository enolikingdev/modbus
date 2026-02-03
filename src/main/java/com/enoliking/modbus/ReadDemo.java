package com.enoliking.modbus;

import com.digitalpetri.modbus.client.ModbusClient;
import com.digitalpetri.modbus.exceptions.ModbusExecutionException;
import com.digitalpetri.modbus.exceptions.ModbusResponseException;
import com.digitalpetri.modbus.exceptions.ModbusTimeoutException;
import com.digitalpetri.modbus.pdu.ReadHoldingRegistersRequest;
import com.digitalpetri.modbus.pdu.ReadHoldingRegistersResponse;
import com.enoliking.modbus.transport.ModbusDevice;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ReadDemo {

//    private final ModbusDevice solintegInverter;
    private final ModbusDevice honorsCharger;
    private final ModbusClientAdapter modbusClient;

    @Autowired
    private ResponseHandler responseHandler;

    public ReadDemo(ModbusDevice modbusDevice) {
//        solintegInverter = modbusDevice;
        honorsCharger = modbusDevice;
        modbusClient = new ModbusClientAdapter(modbusDevice);
    }

    @PostConstruct
    public void readModbusData() throws ModbusExecutionException, ModbusTimeoutException, ModbusResponseException {
//        ModbusClient client = solintegInverter.getClient();
        ModbusClient client = honorsCharger.getClient();

        var inverterSnResponse = modbusClient.readHoldingRegisters(38,3);
        responseHandler.handleSerialNumberFrom3RegistersAsHexDigits("SN: ", inverterSnResponse);

        var maxCurrent = modbusClient.readHoldingRegisters(91,1);
        responseHandler.handleUInt16("maxCurrent: ", maxCurrent);

        var meterValue = modbusClient.readHoldingRegisters(28,2);
        responseHandler.handleUInt32("meterValue: ", meterValue);


    }
}

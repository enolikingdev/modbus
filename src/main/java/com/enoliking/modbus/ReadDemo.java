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

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

@Component
public class ReadDemo {

    private final ModbusDevice solintegInverter;
    private final ModbusClientAdapter solintegClient;

    @Autowired
    private ResponseHandler responseHandler;

    public ReadDemo(ModbusDevice solintegInverter) {
        this.solintegInverter = solintegInverter;
        solintegClient = new ModbusClientAdapter(solintegInverter);
    }

    @PostConstruct
    public void readModbusData() throws ModbusExecutionException, ModbusTimeoutException, ModbusResponseException {
        ModbusClient client = solintegInverter.getClient();

        var inverterSnResponse = solintegClient.readHoldingRegisters(10000, 8);
        responseHandler.handleAsString("Inverter SN: ", inverterSnResponse);

        var equipmentInfoResponse = solintegClient.readHoldingRegisters(33000, 1);
        responseHandler.handleSingleByte("SOC: ", equipmentInfoResponse);

        var totalPV = solintegClient.readHoldingRegisters(11020, 2);
        responseHandler.handleDoubleByte("totalPV: ", totalPV);

        // Firmware Version - register 10011
        ReadHoldingRegistersResponse fwVersionResponse = client.readHoldingRegisters(
                252,
                new ReadHoldingRegistersRequest(10011, 2)
        );
        long firmwareVersion = ByteBuffer.wrap(fwVersionResponse.registers())
                .order(ByteOrder.BIG_ENDIAN)
                .getInt() & 0xFFFFFFFFL;
        System.out.println("Firmware Version (U32): " + firmwareVersion);

        // Date/Time - continuous registers 10100 to 10102
        ReadHoldingRegistersResponse dateTimeResponse = client.readHoldingRegisters(
                252,
                new ReadHoldingRegistersRequest(10100, 3)
        );
        ByteBuffer dateTimeBuf = ByteBuffer.wrap(dateTimeResponse.registers())
                .order(ByteOrder.BIG_ENDIAN);

        int yearMonth = dateTimeBuf.getShort() & 0xFFFF;
        int dayHour = dateTimeBuf.getShort() & 0xFFFF;
        int minuteSecond = dateTimeBuf.getShort() & 0xFFFF;

        int year = yearMonth >> 8;
        int month = yearMonth & 0xFF;
        int day = dayHour >> 8;
        int hour = dayHour & 0xFF;
        int minute = minuteSecond >> 8;
        int second = minuteSecond & 0xFF;

        System.out.printf("Date/Time: %04d-%02d %02d:%02d:%02d%n", year, month, day, hour, minute, second);
    }
}

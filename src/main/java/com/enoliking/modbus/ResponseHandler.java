package com.enoliking.modbus;

import com.digitalpetri.modbus.pdu.ReadHoldingRegistersResponse;
import org.springframework.stereotype.Component;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.charset.StandardCharsets;

@Component
public class ResponseHandler {
    public void handleAsString(String label, ReadHoldingRegistersResponse response) {
        String inverterSn = new String(response.registers(), StandardCharsets.ISO_8859_1);
        System.out.println(label + inverterSn);
    }

    public void handleSingleByte(String label, ReadHoldingRegistersResponse response) {
        int equipmentInfo = ByteBuffer.wrap(response.registers())
                .order(ByteOrder.BIG_ENDIAN)
                .getShort() & 0xFFFF;
        System.out.println(label + equipmentInfo);
    }

    public void handleDoubleByte(String label, ReadHoldingRegistersResponse response) {
        long equipmentInfo = ByteBuffer.wrap(response.registers())
                .order(ByteOrder.BIG_ENDIAN)
                .getInt() & 0xFFFFFFFFL;
        System.out.println(label + equipmentInfo);
    }
}

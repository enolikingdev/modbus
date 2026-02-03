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

    public void handleUInt16(String label, ReadHoldingRegistersResponse response) {
        byte[] b = response.registers();
        if (b.length < 2) {
            throw new IllegalArgumentException("Expected at least 2 bytes (1 register), got " + b.length);
        }

        int value = ByteBuffer.wrap(b)
                .order(ByteOrder.BIG_ENDIAN)
                .getShort() & 0xFFFF;

        System.out.println(label + value);
    }

    public void handleUInt32(String label, ReadHoldingRegistersResponse response) {
        byte[] b = response.registers();
        if (b.length < 4) {
            throw new IllegalArgumentException("Expected at least 4 bytes (2 registers), got " + b.length);
        }

        long value = ByteBuffer.wrap(b)
                .order(ByteOrder.BIG_ENDIAN)
                .getInt() & 0xFFFFFFFFL;

        System.out.println(label + value);
    }

    public void handleSerialNumberFrom3RegistersAsHexDigits(String label, ReadHoldingRegistersResponse response) {
        byte[] b = response.registers();
        if (b.length < 6) {
            throw new IllegalArgumentException("Expected at least 6 bytes (3 registers), got " + b.length);
        }

        ByteBuffer buf = ByteBuffer.wrap(b).order(ByteOrder.BIG_ENDIAN);

        int r1 = buf.getShort() & 0xFFFF;
        int r2 = buf.getShort() & 0xFFFF;
        int r3 = buf.getShort() & 0xFFFF;

        String sn = String.format("%04X%04X%04X", r1, r2, r3);
        System.out.println(label + sn);
    }
}

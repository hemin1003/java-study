package com.minbo.javademo.bytes;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class Test9 {

	public static void main(String[] args) {
		byte[] bytes = { (byte) -50, (byte) 10, (byte) -68, (byte) 132 };
		ByteBuffer buffer = ByteBuffer.wrap(bytes);
		int sumTemp = buffer.order(ByteOrder.LITTLE_ENDIAN).getInt();
		String sum = null;
		System.out.println("##########readInt###########" + sum + ">>>>>>" + sumTemp);
	}

	
	//Java的高低字节序转化
	public static void shortToByte_LH(short shortVal, byte[] b, int offset) {
		b[0 + offset] = (byte) (shortVal & 0xff);
		b[1 + offset] = (byte) (shortVal >> 8 & 0xff);
	}

	public static short byteToShort_HL(byte[] b, int offset) {
		short result;
		result = (short) ((((b[offset + 1]) << 8) & 0xff00 | b[offset] & 0x00ff));
		return result;
	}

	public static void intToByte_LH(int intVal, byte[] b, int offset) {
		b[0 + offset] = (byte) (intVal & 0xff);
		b[1 + offset] = (byte) (intVal >> 8 & 0xff);
		b[2 + offset] = (byte) (intVal >> 16 & 0xff);
		b[3 + offset] = (byte) (intVal >> 24 & 0xff);
	}

	public static int byteToInt_HL(byte[] b, int offset) {
		int result;
		result = (((b[3 + offset] & 0x00ff) << 24) & 0xff000000) | (((b[2 + offset] & 0x00ff) << 16) & 0x00ff0000)
				| (((b[1 + offset] & 0x00ff) << 8) & 0x0000ff00) | ((b[0 + offset] & 0x00ff));
		return result;
	}

}

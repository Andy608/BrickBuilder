package com.bountive.util;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import org.lwjgl.BufferUtils;

public class BufferUtil {

	public static IntBuffer toReadableIntBuffer(int[] bufferData) {
		IntBuffer b = BufferUtils.createIntBuffer(bufferData.length);
		b.put(bufferData).flip();
		return b;
	}
	
	public static FloatBuffer toReadableFloatBuffer(float[] bufferData) {
		FloatBuffer b = BufferUtils.createFloatBuffer(bufferData.length);
		b.put(bufferData).flip();
		return b;
	}
}

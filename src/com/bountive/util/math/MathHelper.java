package com.bountive.util.math;

public class MathHelper {

	private static float[] SIN_TABLE = new float[65536];
	
	/**
	 * Clamps the value given between the min and max.
	 * @return : The clamped value.
	 * @throws IllegalArgumentException when the min is greater than the max, max is less than the min, or min is equal to the max.
	 */
	public static final int clampInt(int value, int min, int max) /*throws IllegalArgumentException*/ {
//		if (min > max) throw new IllegalArgumentException("Min cannot be greater than the max.");
//		else if (max < min) throw new IllegalArgumentException("Max cannot be less than the min.");
//		else if (min == max) throw new IllegalArgumentException("Max and min cannot be equal");
//		else if (value > max) return max;
//		else if (value < min) return min;
//		else return value;
		return (value > max) ? value : (value < min) ? min : value; 
	}
	
	public static final float clampFloat(float value, float min, float max) {
		return (value > max) ? max : (value < min) ? min : value;
	}
	
	public static final float sin(float degrees) {
        return SIN_TABLE[(int)(Math.toRadians(degrees) * 10430.378F) & 65535];
    }

    public static final float cos(float degrees) {
    	if (degrees == 90 || degrees == 270) return 0f;
    	else return SIN_TABLE[(int)(Math.toRadians(degrees) * 10430.378F + 16384.0F) & 65535];
    }
	
	static {
		for (int i = 0; i < SIN_TABLE.length; i++) {
            SIN_TABLE[i] = (float)Math.sin((double)i * Math.PI * 2.0D / 65536.0D);
        }
	}
}

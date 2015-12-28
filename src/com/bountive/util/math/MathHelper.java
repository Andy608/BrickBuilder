package com.bountive.util.math;

public class MathHelper {

	/**
	 * Clamps the value given between the min and max.
	 * @return : The clamped value.
	 * @throws IllegalArgumentException when the min is greater than the max, max is less than the min, or min is equal to the max.
	 */
	public static int clamp(int min, int max, int value) throws IllegalArgumentException {
		if (min > max) throw new IllegalArgumentException("Min cannot be greater than the max.");
		else if (max < min) throw new IllegalArgumentException("Max cannot be less than the min.");
		else if (min == max) throw new IllegalArgumentException("Max and min cannot be equal");
		else if (value > max) return max;
		else if (value < min) return min;
		else return value;
	}
}

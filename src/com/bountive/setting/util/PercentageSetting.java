package com.bountive.setting.util;

import com.bountive.util.math.MathHelper;

public class PercentageSetting extends AbstractFileSetting {

	private final float MIN, MAX;
	
	private float defaultPercentage;
	private float customPercentage;
	private float defaultValue;
	
	public PercentageSetting(String name, float valueAtFullPercent, float defaultPercent, float minPercent, float maxPercent) throws IllegalArgumentException {
		super(name);
		
		defaultValue = valueAtFullPercent;
		defaultPercentage = MathHelper.clampFloat(defaultPercent, minPercent, maxPercent);
		customPercentage = defaultPercentage;
		MIN = minPercent;
		MAX = maxPercent;
	}
	
	/**
	 * Sets the customPercentage.
	 * @param customPercent : The percentage as a float value. Ex: 0.8 is 80%.
	 */
	public void setCustomPercentage(float customPercent) {
		customPercentage = MathHelper.clampFloat(customPercent, MIN, MAX);
	}
	
	public void resetPercentage() {
		customPercentage = defaultPercentage;
	}
	
	public float getDefaultPercentage() {
		return defaultPercentage;
	}
	
	public float getCustomPercentage() {
		return customPercentage;
	}
	
	public float getMaxPercentage() {
		return MAX;
	}
	
	public float getMinPercentage() {
		return MIN;
	}
	
	/**
	 * Calculates what the value would be at the current percentage.
	 */
	public float getValue() {
		return defaultValue * customPercentage; 
	}
}

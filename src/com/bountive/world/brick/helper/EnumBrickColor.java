package com.bountive.world.brick.helper;

import com.bountive.util.math.MathHelper;

public enum EnumBrickColor {

	WHITE(255, 255, 255),
	GRAY(161, 165, 162),
	BLACK(0, 0, 0);
	
	public static final float MAX_COLOR = 255f;
	public final float RED, GREEN, BLUE;
	
	/**
	 * Converts from 0 - 255 format to a 0 - 1 format.
	 * @param: Takes in (r, g, b) color each from 0 - 255. Values are clamped if outside this range. (Alpha value is calculated in the shader with alpha percentage from brick class).
	 */
	private EnumBrickColor(int r, int g, int b) {
		RED = MathHelper.clampInt(r, 0, (int)MAX_COLOR) / MAX_COLOR;
		GREEN =  MathHelper.clampInt(g, 0, (int)MAX_COLOR) / MAX_COLOR;
		BLUE =  MathHelper.clampInt(b, 0, (int)MAX_COLOR) / MAX_COLOR;
	}
	
	/**
	 * Shows the color in (r, g, b) from 0 - 255 format.
	 */
	@Override
	public String toString() {
		return super.name() + "(" + RED * MAX_COLOR + ", " + GREEN * MAX_COLOR + ", " + BLUE * MAX_COLOR + ")";
	}
}

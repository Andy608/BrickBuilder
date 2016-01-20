package com.bountive.world.brick.material;

import com.bountive.util.math.MathHelper;

public enum EnumBrickColor {
	
	WHITE(255, 255, 255, 255),
	GRAY(161, 165, 162, 255),
	BRIGHT_RED(196, 40, 27,  255),
	BRIGHT_YELLOW(245, 205, 47,  255),
	BLACK(0, 0, 0, 255);
	
	public static final float MAX_COLOR = 255f;
	public final float R, G, B, A;
	
	/**
	 * Converts from 0 - 255 format to a 0 - 1 format.
	 * @param: Takes in (r, g, b) color each from 0 - 255. Values are clamped if outside this range. (Alpha value is calculated in the shader with alpha percentage from brick class).
	 */
	private EnumBrickColor(int r, int g, int b, int a) {
		R = MathHelper.clampInt(r, 0, (int)MAX_COLOR) / MAX_COLOR;
		G = MathHelper.clampInt(g, 0, (int)MAX_COLOR) / MAX_COLOR;
		B = MathHelper.clampInt(b, 0, (int)MAX_COLOR) / MAX_COLOR;
		A = MathHelper.clampInt(a, 0, (int)MAX_COLOR) / MAX_COLOR;
	}
	
	/**
	 * Shows the color in (r, g, b) from 0 - 255 format.
	 */
	@Override
	public String toString() {
		return super.name() + "(" + R * MAX_COLOR + ", " + G * MAX_COLOR + ", " + B * MAX_COLOR + ", " + A + ")";
	}
}

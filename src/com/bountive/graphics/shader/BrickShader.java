package com.bountive.graphics.shader;

import com.bountive.util.resource.FileResourceLocation;
import com.bountive.util.resource.FileResourceLocation.EnumFileExtension;

public class BrickShader extends AbstractShader {
	
//	private int brickColorID;
	
	public BrickShader() {
		super(new FileResourceLocation(RESOURCE_DIRECTORY, "brickVertexShader", EnumFileExtension.VS), new FileResourceLocation(RESOURCE_DIRECTORY, "brickFragmentShader", EnumFileExtension.FS));
	}

	@Override
	protected void bindUniformVariables() {
		super.bindUniformVariables();
//		brickColorID = getUniformLocation("brickColorTint");
	}
	
//	public void loadBrickColor(float red, float green, float blue) {
//		super.loadFloats(brickColorID, red, green, blue);
//	}
}

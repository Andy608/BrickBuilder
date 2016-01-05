package com.bountive.graphics.shader;

import com.bountive.util.resource.FileResourceLocation;
import com.bountive.util.resource.FileResourceLocation.EnumFileExtension;

public class EntityShader extends AbstractShader {

	public EntityShader() {
		super(new FileResourceLocation(RESOURCE_DIRECTORY, "entityVertexShader", EnumFileExtension.VS), new FileResourceLocation(RESOURCE_DIRECTORY, "entityFragmentShader", EnumFileExtension.FS));
	}
}

package com.bountive.graphics.shader;

import com.bountive.util.resource.ResourceLocation;

public class EntityShader extends AbstractShader {

	public EntityShader() {
		super(new ResourceLocation(RESOURCE_DIRECTORY, "entityVertexShader.vs", true), new ResourceLocation(RESOURCE_DIRECTORY, "entityFragmentShader.fs", true));
	}
}

package com.bountive.graphics.shader;

import com.bountive.util.resource.ResourceLocation;

public class EntityShader extends AbstractShader {

	public EntityShader() {
		super(new ResourceLocation(RESOURCE_DIRECTORY, "entityVertexShader.vs"), new ResourceLocation(RESOURCE_DIRECTORY, "entityFragmentShader.fs"));
	}

	@Override
	protected void bindUniformVariables() {}
}

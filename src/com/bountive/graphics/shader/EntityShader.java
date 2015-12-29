package com.bountive.graphics.shader;

import math.Matrix4f;

import com.bountive.util.resource.ResourceLocation;

public class EntityShader extends AbstractShader {

	private int projectionMatrixID;
	private int transformationMatrixID;
	private int viewMatrixID;
	
	public EntityShader() {
		super(new ResourceLocation(RESOURCE_DIRECTORY, "entityVertexShader.vs"), new ResourceLocation(RESOURCE_DIRECTORY, "entityFragmentShader.fs"));
	}

	@Override
	protected void bindUniformVariables() {
		projectionMatrixID = super.getUniformLocation("projectionMatrix");
		transformationMatrixID = super.getUniformLocation("transformationMatrix");
		viewMatrixID = super.getUniformLocation("viewMatrix");
	}
	
	public void loadProjectionMatrix(Matrix4f matrix) {
		loadMatrix(projectionMatrixID, matrix);
	}
	
	public void loadTransformationMatrix(Matrix4f matrix) {
		loadMatrix(transformationMatrixID, matrix);
	}
	
	public void loadViewMatrix(Matrix4f matrix) {
		loadMatrix(viewMatrixID, matrix);
	}
}

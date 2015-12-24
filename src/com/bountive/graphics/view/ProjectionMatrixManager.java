package com.bountive.graphics.view;

public class ProjectionMatrixManager {

	//private float FOV; //This will not be a value in here. Use GraphicSettings.FOV instead.
	//private EnumProjectionType currentProjection; //This will not be a value in here. Use GraphicSettings.currentProjection instead.
	
	//SET UP THIS CLASS AFTER THE GRAPHICS OPTIONS CLASS!
	//Save variables like FOV and currentProjection and then load them up into this class for use.
	
	public ProjectionMatrixManager() {
		
	}
	
	public enum EnumProjectionType {
		PERSPECTIVE,
		ORTHOGRAPHIC;
	}
}

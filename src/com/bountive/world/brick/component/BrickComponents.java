package com.bountive.world.brick.component;

import com.bountive.graphics.model.util.ModelComponents;
import com.bountive.util.resource.FileResourceLocation;
import com.bountive.util.resource.FileResourceLocation.EnumFileExtension;
import com.bountive.util.resource.ResourceDirectory;

public class BrickComponents {

	private static final ResourceDirectory BRICK_MODEL_DIR = new ResourceDirectory("res/model/brick", "component", true);
	private static final ResourceDirectory BOTTOM_DIR = new ResourceDirectory(BRICK_MODEL_DIR.getFullDirectory(), "bottom", true);
	private static final ResourceDirectory TOP_DIR = new ResourceDirectory(BRICK_MODEL_DIR.getFullDirectory(), "top", true);
	private static final ResourceDirectory SIDE_DIR = new ResourceDirectory(BRICK_MODEL_DIR.getFullDirectory(), "l_r_f_b", true);
	private static final ResourceDirectory TOP_BEVEL_DIR = new ResourceDirectory(BRICK_MODEL_DIR.getFullDirectory(), "top_bevel", true);
	private static final ResourceDirectory SIDE_BEVEL_DIR = new ResourceDirectory(BRICK_MODEL_DIR.getFullDirectory(), "side_bevel", true);
	
	//LOW POLY BRICK MODELS
	public static ModelComponents LOW_POLY_FLAT_BOTTOM;
	public static ModelComponents LOW_POLY_FLAT_TOP;
	public static ModelComponents LOW_POLY_FLAT_SIDE;
	public static ModelComponents LOW_POLY_FLAT_TOP_BEVEL;
	public static ModelComponents LOW_POLY_FLAT_SIDE_BEVEL;
	
	public static void loadBrickComponents() {
		System.out.println("LOADING...");
		
		LOW_POLY_FLAT_BOTTOM = new ModelComponents(new FileResourceLocation(BOTTOM_DIR, "bottom_type_1", EnumFileExtension.OBJ));
		LOW_POLY_FLAT_TOP = new ModelComponents(new FileResourceLocation(TOP_DIR, "top_type_0", EnumFileExtension.OBJ));
		LOW_POLY_FLAT_SIDE = new ModelComponents(new FileResourceLocation(SIDE_DIR, "side_type_2", EnumFileExtension.OBJ));
		LOW_POLY_FLAT_TOP_BEVEL = new ModelComponents(new FileResourceLocation(TOP_BEVEL_DIR, "top_bevel_type_0", EnumFileExtension.OBJ));
		LOW_POLY_FLAT_SIDE_BEVEL = new ModelComponents(new FileResourceLocation(SIDE_BEVEL_DIR, "side_bevel_type_0", EnumFileExtension.OBJ));
	}
}

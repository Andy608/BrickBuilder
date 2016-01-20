package com.bountive.world.brick.component;

import com.bountive.graphics.model.util.ModelComponents;
import com.bountive.util.resource.FileResourceLocation;
import com.bountive.util.resource.FileResourceLocation.EnumFileExtension;
import com.bountive.util.resource.ResourceDirectory;

public class BrickComponents {

	private static final ResourceDirectory LOW_POLY_DIR = new ResourceDirectory("res/model", "low_poly", true);
//	private static final ResourceDirectory MEDIUM_POLY_DIR = new ResourceDirectory("res/model", "medium_poly", true);
//	private static final ResourceDirectory HIGH_POLY_DIR = new ResourceDirectory("res/model", "high_poly", true);
//	private static final ResourceDirectory ULTRA_POLY_DIR = new ResourceDirectory("res/model", "ultra_poly", true);
//	private static final ResourceDirectory INSANE_POLY_DIR = new ResourceDirectory("res/model", "insane_poly", true);
	
	private static final ResourceDirectory LOW_POLY_BRICK_MODEL_DIR = new ResourceDirectory(LOW_POLY_DIR.getFullDirectory(), "brick/component", true);
//	private static final ResourceDirectory MEDIUM_POLY_BRICK_MODEL_DIR = new ResourceDirectory(MEDIUM_POLY_DIR.getFullDirectory(), "brick/component", true);
	
	private static final ResourceDirectory LOW_POLY_BOTTOM_DIR = new ResourceDirectory(LOW_POLY_BRICK_MODEL_DIR.getFullDirectory(), "bottom", true);
	private static final ResourceDirectory LOW_POLY_TOP_DIR = new ResourceDirectory(LOW_POLY_BRICK_MODEL_DIR.getFullDirectory(), "top", true);
	private static final ResourceDirectory LOW_POLY_SIDE_DIR = new ResourceDirectory(LOW_POLY_BRICK_MODEL_DIR.getFullDirectory(), "l_r_f_b", true);
	private static final ResourceDirectory LOW_POLY_TOP_BEVEL_DIR = new ResourceDirectory(LOW_POLY_BRICK_MODEL_DIR.getFullDirectory(), "top_bevel", true);
	private static final ResourceDirectory LOW_POLY_SIDE_BEVEL_DIR = new ResourceDirectory(LOW_POLY_BRICK_MODEL_DIR.getFullDirectory(), "side_bevel", true);
	
//	private static final ResourceDirectory MEDIUM_POLY_BOTTOM_DIR = new ResourceDirectory(MEDIUM_POLY_BRICK_MODEL_DIR.getFullDirectory(), "bottom", true);
//	private static final ResourceDirectory MEDIUM_POLY_TOP_DIR = new ResourceDirectory(MEDIUM_POLY_BRICK_MODEL_DIR.getFullDirectory(), "top", true);
//	private static final ResourceDirectory MEDIUM_POLY_SIDE_DIR = new ResourceDirectory(MEDIUM_POLY_BRICK_MODEL_DIR.getFullDirectory(), "l_r_f_b", true);
//	private static final ResourceDirectory MEDIUM_POLY_TOP_BEVEL_DIR = new ResourceDirectory(MEDIUM_POLY_BRICK_MODEL_DIR.getFullDirectory(), "top_bevel", true);
//	private static final ResourceDirectory MEDIUM_POLY_SIDE_BEVEL_DIR = new ResourceDirectory(MEDIUM_POLY_BRICK_MODEL_DIR.getFullDirectory(), "side_bevel", true);
	
	//LOW POLY BRICK MODELS
	public static ModelComponents LOW_POLY_FLAT_BOTTOM;
	public static ModelComponents LOW_POLY_FLAT_TOP;
	public static ModelComponents LOW_POLY_FLAT_SIDE;
	public static ModelComponents LOW_POLY_FLAT_TOP_BEVEL;
	public static ModelComponents LOW_POLY_FLAT_SIDE_BEVEL;
	
	public static ModelComponents LOW_POLY_FULL_BOTTOM;
	public static ModelComponents LOW_POLY_FULL_TOP;
	public static ModelComponents LOW_POLY_FULL_SIDE;
	public static ModelComponents LOW_POLY_FULL_TOP_BEVEL;
	public static ModelComponents LOW_POLY_FULL_SIDE_BEVEL;
	
	public static void loadBrickComponents() {
		System.out.println("LOADING...");
		
		LOW_POLY_FLAT_BOTTOM = new ModelComponents(new FileResourceLocation(LOW_POLY_BOTTOM_DIR, "flat_bottom_type_1", EnumFileExtension.OBJ));
		LOW_POLY_FLAT_TOP = new ModelComponents(new FileResourceLocation(LOW_POLY_TOP_DIR, "flat_top_type_0", EnumFileExtension.OBJ));
		LOW_POLY_FLAT_SIDE = new ModelComponents(new FileResourceLocation(LOW_POLY_SIDE_DIR, "flat_side_type_2", EnumFileExtension.OBJ));
		LOW_POLY_FLAT_TOP_BEVEL = new ModelComponents(new FileResourceLocation(LOW_POLY_TOP_BEVEL_DIR, "flat_top_bevel_type_0", EnumFileExtension.OBJ));
		LOW_POLY_FLAT_SIDE_BEVEL = new ModelComponents(new FileResourceLocation(LOW_POLY_SIDE_BEVEL_DIR, "flat_side_bevel_type_0", EnumFileExtension.OBJ));
		
		LOW_POLY_FULL_BOTTOM = new ModelComponents(new FileResourceLocation(LOW_POLY_BOTTOM_DIR, "full_bottom_type_1", EnumFileExtension.OBJ));
		LOW_POLY_FULL_TOP = new ModelComponents(new FileResourceLocation(LOW_POLY_TOP_DIR, "full_top_type_0", EnumFileExtension.OBJ));
		LOW_POLY_FULL_SIDE = new ModelComponents(new FileResourceLocation(LOW_POLY_SIDE_DIR, "full_side_type_2", EnumFileExtension.OBJ));
		LOW_POLY_FULL_TOP_BEVEL = new ModelComponents(new FileResourceLocation(LOW_POLY_TOP_BEVEL_DIR, "full_top_bevel_type_0", EnumFileExtension.OBJ));
		LOW_POLY_FULL_SIDE_BEVEL = new ModelComponents(new FileResourceLocation(LOW_POLY_SIDE_BEVEL_DIR, "full_side_bevel_type_0", EnumFileExtension.OBJ));
	}
}

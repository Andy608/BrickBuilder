package com.bountive.world.brick.material;

import com.bountive.util.resource.FileResourceLocation;

public class BrickTexturedMaterial extends BrickMaterial {

	protected FileResourceLocation textureLocation;
	
	public BrickTexturedMaterial(byte id, FileResourceLocation textureLoc) {
		super(id);
		textureLocation = textureLoc;
	}
	
	public FileResourceLocation getTextureLocation() {
		return textureLocation;
	}
}

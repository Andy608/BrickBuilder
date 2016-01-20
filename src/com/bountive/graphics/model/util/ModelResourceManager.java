package com.bountive.graphics.model.util;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.opengl.GL30;

import com.bountive.graphics.model.ModelMesh;

public class ModelResourceManager {

	private static ModelResourceManager manager;
	public List<ModelMesh> activeModels;
	
	private ModelResourceManager() {
		activeModels = new ArrayList<>();
	}
	
	public static void init() {
		if (manager == null) {
			manager = new ModelResourceManager();
		}
	}
	
	public void addModel(ModelMesh model) {
		System.out.println("ADDING MODEL");
		activeModels.add(model);
	}
	
	public void rebuildVAOs() {
		System.out.println("REBUILDING VAOs");
		deleteVAOs();
		
		for (ModelMesh model : activeModels) {
			model.rebuildVAO();
		}
	}
	
	public void release() {
		System.out.println("RELEASING VAOs AND VBOs");
		deleteVAOs();
		
		for (ModelMesh model : activeModels) {
			for (VBOWrapper vbo : model.getVBOs()) {
				GL30.glDeleteVertexArrays(vbo.getID());
			}
		}
	}
	
	private void deleteVAOs() {
		System.out.println("DELETING VAOs");
		for (ModelMesh model : activeModels) {
			GL30.glDeleteVertexArrays(model.getVaoID());
		}
	}
	
	public static ModelResourceManager getManager() {
		return manager;
	}
}

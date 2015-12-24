package com.bountive.graphics.model.util;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL30;

public class ModelManager {

	private static ModelManager manager;
	
	private List<Integer> vaoList;
	private List<Integer> vboList;
	
	private ModelManager() {
		vaoList = new ArrayList<>();
		vboList = new ArrayList<>();
	}
	
	public static void init() {
		if (manager == null) {
			manager = new ModelManager();
		}
	}
	
	public void addVAO(int vaoID) {
		vaoList.add(vaoID);
	}
	
	public void addVBO(int vboID) {
		vboList.add(vboID);
	}
	
	public void releaseVAOs() {
//		System.out.println(vaoList == null);
		
		for (int vao : vaoList) {
			GL30.glDeleteVertexArrays(vao);
		}
	}
	
	public void release() {
		releaseVAOs();
		
		for (int vbo : vboList)
			GL15.glDeleteBuffers(vbo);
	}
	
	public static ModelManager getManager() {
		return manager;
	}
}

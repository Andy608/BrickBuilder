package com.bountive.model.util;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL30;

public class ModelManager {

	public static ModelManager manager;
	
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
	
	public void release() {
		for (int vao : vaoList)
			GL30.glDeleteVertexArrays(vao);
		
		for (int vbo : vboList)
			GL15.glDeleteBuffers(vbo);
	}
}

package com.bountive.model.util;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

import com.bountive.model.ModelMesh;

public class ModelBuilder {

	private static ModelBuilder builder;
	
	private ModelBuilder() {
		builder = this;
	}
	
	public static void init() {
		if (builder == null) {
			builder = new ModelBuilder();
		}
	}
	
	/**
	 * Creates a ModelMesh for a 2D Texture Rectangle Model.
	 * @param positions : An array of (x, y) coordinates per vertex. Counter clockwise starting from the top left.
	 * @param UVTextureCoords : The UV Texture coordinates for the model. Counter clockwise starting from the top left.
	 * @return : A new ModelMesh that has the correct UV Coordinates for a 2D texture.
	 */
	//TODO: Implement this when needed!
//	public ModelMesh build2DTextureRecModel(float[] positions, float[] UVTextureCoords) {
//		return new ModelMesh();
//	}
	
	/**
	 * Creates a ModelMesh for a 2D Colored Rectangle Model.
	 * @param positions : An array of (x, y) coordinates per vertex. Counter clockwise starting from the top left.
	 * @param colorCoords : An array of (r,g,b,a) values per vertex. Counter clockwise starting from the top left.
	 * @return : A new colored ModelMesh.
	 */
	public ModelMesh build2DColorRecModel(float[] positions, float[] colorCoords) {
		int vao = createVAO();
		int[] indices = new int[] {0, 1, 2, 0, 2, 3};
		bindIndicesBuffer(indices);
		bindVAO(0, positions, 2);
		bindVAO(1, colorCoords, 4);
		unbindVAO();
		return new ModelMesh(vao, indices.length);
	}
	
	/**
	 * Create new Model from an object file.
	 * @param objFileLocation : The location of the file.
	 * @return : A new model with the information from the object file.
	 */
//	public ModelMesh buildModelFromFile(ResourceLocation objFileLocation) {
//		return new ModelMesh();
//	}
	
	private int createVAO() {
		int vaoID = GL30.glGenVertexArrays();
		ModelManager.manager.addVAO(vaoID);
		GL30.glBindVertexArray(vaoID);
		return vaoID;
	}
	
	/**
	 * @param arrayIndex : The index slot in the Vertex Array Object.
	 * @param bufferData : The data list that will be bound to this index. Example: positions or normals array.
	 * @param dataLengthPerVertex : The amount of spots in bufferData one vertex needs. Example: 3D positions 
	 * 		  (x, y, z) require 3 spots per vertex.
	 */
	private void bindVAO(int arrayIndex, float[] bufferData, int dataLengthPerVertex) {
		int vbo = GL15.glGenBuffers();
		ModelManager.manager.addVBO(vbo);
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vbo);
		GL15.glBufferData(GL15.GL_ARRAY_BUFFER, toReadableFloatBuffer(bufferData), GL15.GL_STATIC_DRAW);
		GL20.glVertexAttribPointer(arrayIndex, dataLengthPerVertex, GL11.GL_FLOAT, false, 0, 0);
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
		GL20.glEnableVertexAttribArray(arrayIndex);
	}
	
	private void unbindVAO() {
		GL30.glBindVertexArray(0);
	}
	
	private void bindIndicesBuffer(int[] indices) {
		int vboID = GL15.glGenBuffers();
		ModelManager.manager.addVBO(vboID);
		GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, vboID);
		GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, toReadableIntBuffer(indices), GL15.GL_STATIC_DRAW);
	}
	
	private IntBuffer toReadableIntBuffer(int[] bufferData) {
		IntBuffer b = BufferUtils.createIntBuffer(bufferData.length);
		b.put(bufferData).flip();
		return b;
	}
	
	private FloatBuffer toReadableFloatBuffer(float[] bufferData) {
		FloatBuffer b = BufferUtils.createFloatBuffer(bufferData.length);
		b.put(bufferData).flip();
		return b;
	}
}

package com.bountive.graphics.model.util;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

import com.bountive.graphics.model.ModelMesh;
import com.bountive.util.BufferUtil;
import com.bountive.util.resource.FileResourceLocation;
import com.bountive.world.zone.ModelZone;

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
		int[] vbos = new int[2];
		
		bindVAO(vao);
		bindIndicesBuffer(indices);
		vbos[0] = bindAttribWithVAOStatic(0, positions, 2);
		vbos[1] = bindAttribWithVAOStatic(1, colorCoords, 4);
		unbindVAO();
		return new ModelMesh(vao, vbos, indices.length);
	}
	
	public ModelMesh build3DColorModel(float[] positions, int[] indices, float[] colorCoords) {
		int vao = createVAO();
		int[] vbos = new int[2];
		
		bindVAO(vao);
		bindIndicesBuffer(indices);
		vbos[0] = bindAttribWithVAOStatic(0, positions, 3);
		vbos[1] = bindAttribWithVAOStatic(1, colorCoords, 4);
		unbindVAO();
		return new ModelMesh(vao, vbos, indices.length);
	}
	
	public ModelZone buildZone(float[] positions, float[] colorCoords, float[] normals, float[] texCoords) {
		int vao = createVAO();
		int vbos[] = new int[4];
		bindVAO(vao);
		vbos[0] = bindAttribWithVAODynamic(0, positions, 3);
		vbos[1] = bindAttribWithVAODynamic(1, colorCoords, 4);
		vbos[2] = bindAttribWithVAODynamic(2, normals, 3);
		vbos[3] = bindAttribWithVAODynamic(3, texCoords, 2);
		unbindVAO();
		return new ModelZone(vao, positions.length / 3, vbos);
	}
	
	/**
	 * Create new Model from an object file.
	 * @param objFileLocation : The location of the file.
	 * @return : A new model with the information from the object file.
	 */
	public ModelMesh buildModelFromFile(FileResourceLocation objFileLocation) {
		int vao = createVAO();
		int vbos[] = new int[2];
		
		ModelComponents modelComponents = new ModelComponents(objFileLocation);
		
		bindVAO(vao);
//		bindIndicesBuffer(modelComponents.getIndices());
		vbos[0] = bindAttribWithVAOStatic(0, modelComponents.getPositionsAsFloat(), 3);
//		bindAttribWithVAO(1, modelComponents.getTextureUVs(), 2);
		vbos[1] = bindAttribWithVAOStatic(1, modelComponents.getNormalsAsFloat(), 3);
		unbindVAO();
		return new ModelMesh(vao, vbos, modelComponents.getPositionsAsFloat().length / 3);
	}
	
	private int createVAO() {
		int vaoID = GL30.glGenVertexArrays();
		ModelManager.getManager().addVAO(vaoID);
		return vaoID;
	}
	
	private void bindVAO(int vaoID) {
		GL30.glBindVertexArray(vaoID);
	}
	
	/**
	 * @param arrayIndex : The index slot in the Vertex Array Object.
	 * @param bufferData : The data list that will be bound to this index. Example: positions or normals array.
	 * @param dataLengthPerVertex : The amount of spots in bufferData one vertex needs. Example: 3D positions 
	 * 		  (x, y, z) require 3 spots per vertex.
	 */
	private int bindAttribWithVAOStatic(int arrayIndex, float[] bufferData, int dataLengthPerVertex) {
		int vbo = GL15.glGenBuffers();
		ModelManager.getManager().addVBO(vbo);
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vbo);
		GL15.glBufferData(GL15.GL_ARRAY_BUFFER, BufferUtil.toReadableFloatBuffer(bufferData), GL15.GL_STATIC_DRAW);
		GL20.glVertexAttribPointer(arrayIndex, dataLengthPerVertex, GL11.GL_FLOAT, false, 0, 0);
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
		GL20.glEnableVertexAttribArray(arrayIndex);
		return vbo;
	}
	
	private int bindAttribWithVAODynamic(int arrayIndex, float[] bufferData, int dataLengthPerVertex) {
		int vbo = GL15.glGenBuffers();
		ModelManager.getManager().addVBO(vbo);
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vbo);
		GL15.glBufferData(GL15.GL_ARRAY_BUFFER, BufferUtil.toReadableFloatBuffer(bufferData), GL15.GL_DYNAMIC_DRAW);
		GL20.glVertexAttribPointer(arrayIndex, dataLengthPerVertex, GL11.GL_FLOAT, false, 0, 0);
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
		GL20.glEnableVertexAttribArray(arrayIndex);
		return vbo;
	}
	
	private void unbindVAO() {
		GL30.glBindVertexArray(0);
	}
	
	private void bindIndicesBuffer(int[] indices) {
		int vboID = GL15.glGenBuffers();
		ModelManager.getManager().addVBO(vboID);
		GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, vboID);
		GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, BufferUtil.toReadableIntBuffer(indices), GL15.GL_STATIC_DRAW);
	}
	
	public static ModelBuilder getBuilder() {
		return builder;
	}
}

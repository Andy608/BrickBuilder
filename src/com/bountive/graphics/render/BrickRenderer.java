package com.bountive.graphics.render;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import math.Vector3f;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

import com.bountive.graphics.model.ModelMesh;
import com.bountive.graphics.model.util.ModelBrickList;
import com.bountive.graphics.shader.EntityShader;
import com.bountive.graphics.view.AbstractCamera;
import com.bountive.graphics.view.CameraMatrixManager;
import com.bountive.util.math.MatrixMathHelper;
import com.bountive.world.object.brick.AbstractBrick;
import com.bountive.world.object.brick.AbstractBrick.EnumBrickModel;

public class BrickRenderer implements IRenderer {

	protected EntityShader shader;
	private Map<EnumBrickModel, List<AbstractBrick>> bricks;
	
	public BrickRenderer() {
		setShader(new EntityShader());
		bricks = new HashMap<EnumBrickModel, List<AbstractBrick>>();
	}
	
	//For now a new brick will be taken in. But in the future, a brick object will be static with no position or anything 
	//and the world will keep track of which blocks are at which position in the grid.
	//
	//I'm picturing this method taking in a chunk of bricks and then rendering the bricks relative to the chunk's position and the order they are in.
	public void addBrickToBatch(AbstractBrick b) {
		List<AbstractBrick> modelBatch = bricks.get(b.getModelType());
		if (modelBatch != null) {
			modelBatch.add(b);
		}
		else {
			List<AbstractBrick> batch = new ArrayList<AbstractBrick>();
			batch.add(b);
			bricks.put(b.getModelType(), batch);
		}
	}
	
	@Override
	public void render(AbstractCamera c) {
		shader.bind();
		shader.loadProjectionMatrix(CameraMatrixManager.manager.getProjectionMatrix());
		shader.loadViewMatrix(c.getViewMatrix());
		for (EnumBrickModel modelType : bricks.keySet()) {
			
			ModelMesh mesh = bindModel(modelType);
			for (AbstractBrick b : bricks.get(modelType)) {
				//Instead of brick, it should take in the properties(position/rotation/scale) of the brick for the transformation matrix.
				renderBrick(b, mesh);
			}
			unbindModel();
		}
		shader.unbind();
		bricks.clear();
	}
	
	private ModelMesh bindModel(EnumBrickModel modelType) {
		ModelMesh brickModel;
		
		switch (modelType) {
		case FULL_1x1: brickModel = ModelBrickList.FULL_1X1_BRICK; break;
//		case FLAT_1x1: brickModel = ModelBrickList.FLAT_1X1_BRICK; break;
		default: brickModel = ModelBrickList.FLAT_1X1_BRICK;
		}
		
		GL30.glBindVertexArray(brickModel.getVaoID());
		return brickModel;
	}
	
	private Vector3f POS = new Vector3f(0, 0, -10);		//TEMP
	private Vector3f ROT = new Vector3f(0, 0, 0);		//TEMP
	private Vector3f SCALE = new Vector3f(1, 1, 1);		//TEMP
	private void renderBrick(AbstractBrick b, ModelMesh m) {
		shader.loadTransformationMatrix(MatrixMathHelper.buildTransformationMatrix(POS, ROT, SCALE));//TEMP - should use the transformation of the brick.
		
		GL20.glEnableVertexAttribArray(0);
		GL20.glEnableVertexAttribArray(1);
		GL11.glDrawElements(GL11.GL_TRIANGLES, m.getVertexCount(), GL11.GL_UNSIGNED_INT, 0);
		GL20.glDisableVertexAttribArray(1);
		GL20.glDisableVertexAttribArray(0);
	}
	
	private void unbindModel() {
		GL30.glBindVertexArray(0);
	}

	public void setShader(EntityShader s) {
		shader = s;
	}

	@Override
	public void release() {
		shader.release();
	}
}

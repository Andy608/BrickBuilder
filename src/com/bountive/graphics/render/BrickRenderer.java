package com.bountive.graphics.render;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import math.Vector3f;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

import com.bountive.brick.Brick;
import com.bountive.graphics.model.ModelMesh;
import com.bountive.graphics.model.util.ModelBrickList;
import com.bountive.graphics.shader.EntityShader;
import com.bountive.graphics.view.AbstractCamera;
import com.bountive.graphics.view.CameraMatrixManager;
import com.bountive.util.math.MatrixMathHelper;

public class BrickRenderer {

	private EntityShader brickShader;
	
	private Map<Brick.EnumBrickModel, List<Brick>> bricks;
	
	public BrickRenderer() {
		brickShader = new EntityShader();
		bricks = new HashMap<Brick.EnumBrickModel, List<Brick>>();
	}
	
	//For now a new brick will be taken in. But in the future, a brick object will be static with no position or anything 
	//and the world will keep track of which blocks are at which position in the grid.
	//
	//I'm picturing this method taking in a chunk of bricks and then rendering the bricks relative to the chunk's position and the order they are in.
	public void addBrickToBatch(Brick b) {
		List<Brick> modelBatch = bricks.get(b.getModelType());
		if (modelBatch != null) {
			modelBatch.add(b);
		}
		else {
			List<Brick> batch = new ArrayList<Brick>();
			batch.add(b);
			bricks.put(b.getModelType(), batch);
		}
	}
	
	public void render(AbstractCamera c) {
		brickShader.bind();
		brickShader.loadProjectionMatrix(CameraMatrixManager.manager.getProjectionMatrix());
		brickShader.loadViewMatrix(c.getViewMatrix());
		for (Brick.EnumBrickModel modelType : bricks.keySet()) {
			
			ModelMesh mesh = bindModel(modelType);
			for (Brick b : bricks.get(modelType)) {
				//Instead of brick, it should take in the properties(position/rotation/scale) of the brick for the transformation matrix.
				renderBrick(b, mesh);
			}
			unbindModel();
		}
		brickShader.unbind();
		bricks.clear();
	}
	
	private ModelMesh bindModel(Brick.EnumBrickModel modelType) {
		ModelMesh brickModel;
		
		switch (modelType) {
		case FULL_1x1: brickModel = ModelBrickList.FULL_1X1_BRICK; break;
//		case FLAT_1x1: brickModel = ModelBrickList.FLAT_1X1_BRICK; break;
		default: brickModel = ModelBrickList.FLAT_1X1_BRICK;
		}
		
		GL30.glBindVertexArray(brickModel.getVaoID());
		return brickModel;
	}
	
	private void renderBrick(Brick b, ModelMesh m) {
		brickShader.loadTransformationMatrix(MatrixMathHelper.buildTransformationMatrix(new Vector3f(0, 0, -10), new Vector3f(0, 0, 0), new Vector3f(1, 1, 1)));//TEMP - should use the transformation of the brick.
		
		GL20.glEnableVertexAttribArray(0);
		GL20.glEnableVertexAttribArray(1);
		GL11.glDrawElements(GL11.GL_TRIANGLES, m.getVertexCount(), GL11.GL_UNSIGNED_INT, 0);
		GL20.glDisableVertexAttribArray(1);
		GL20.glDisableVertexAttribArray(0);
	}
	
	private void unbindModel() {
		GL30.glBindVertexArray(0);
	}
	
	public void release() {
		brickShader.release();
	}
}

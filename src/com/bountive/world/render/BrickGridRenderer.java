package com.bountive.world.render;

import math.Matrix4f;
import math.Vector3f;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL30;

import com.bountive.graphics.model.ModelMesh;
import com.bountive.graphics.model.util.ModelBrickList;
import com.bountive.graphics.render.IRenderer;
import com.bountive.graphics.shader.BrickShader;
import com.bountive.graphics.view.AbstractCamera;
import com.bountive.graphics.view.CameraMatrixManager;
import com.bountive.util.math.MatrixMathHelper;
import com.bountive.world.brick.AbstractBrick;
import com.bountive.world.brick.helper.EnumBrickColor;
import com.bountive.world.brick.helper.EnumBrickModel;
import com.bountive.world.zone.Zone;

public class BrickGridRenderer implements IRenderer {
	
	private BrickShader shader;
	private Zone zone;
	private ModelMesh boundModel;
	
	public BrickGridRenderer() {
		useShader(new BrickShader());
		customTransformMatrix = new Matrix4f();
		defaultScaleMatrix = new Matrix4f();
		rotationMatrix = new Matrix4f();
		translationMatrix = new Matrix4f();
		brickOffset = new Vector3f();
	}
	
	public void useShader(BrickShader s) {
		if (shader != null) {
			shader.release();
		}
		shader = s;
	}
	
	public void addZoneToBatch(Zone zone) {
		this.zone = zone;
	}
	
	@Override
	public void render(AbstractCamera c) {
		shader.bind();
		shader.loadProjectionMatrix(CameraMatrixManager.manager.getProjectionMatrix());
		shader.loadViewMatrix(c.getViewMatrix());
		
		//For all model types, bind them, then send the bound model and positions to render
		
//		HashMap<AbstractBrick, List<Integer>> brickIndexes = zone.getBrickIndexes();
//		HashMap<EnumBrickModel, List<AbstractBrick>> modelBatch = zone.getModelBatch();
		
		
//		GL11.glEnable(GL11.GL_CULL_FACE);
//		for (EnumBrickModel modelType : modelBatch.keySet()) {
//			ModelMesh brickModel = bindModel(modelType);
//			List<AbstractBrick> sameModelBricks = modelBatch.get(modelType);
//			
//			for (AbstractBrick brick : sameModelBricks) {
//				renderBricks(brick, brickModel, brickIndexes.get(brick));
//			}
//			unbindModel();
//		}
		
		for (int i = 0; i < zone.getZoneSize(); i++) {
			if (zone.getBrick(i) != null) {
				bindModel(zone.getBrick(i).getModelID());
				renderBrick(zone.getBrick(i), boundModel, i);
				unbindModel();
			}
		}
		
//		GL11.glDisable(GL11.GL_CULL_FACE);
		
		
		zone = null;
		shader.unbind();
	}
	
	private ModelMesh bindModel(EnumBrickModel modelID) {
		ModelMesh brickModel;
		
		switch (modelID) {
		case FLAT_1X1: brickModel = ModelBrickList.FLAT_1X1_BRICK; break;
		case REGULAR_1X1: brickModel = ModelBrickList.REGULAR_1X1_BRICK; break;
		default: brickModel = ModelBrickList.FLAT_1X1_BRICK;
		}
		
		GL30.glBindVertexArray(brickModel.getVaoID());
		boundModel = brickModel;
		return brickModel;
	}
	
//	private void renderBrick(AbstractBrick brick, ModelMesh brickModel, Vector3f position) {
//		shader.loadTransformationMatrix(MatrixMathHelper.buildTransformationMatrix(Vector3f.add(zone.getPosition(), position, null), zone.getRotation()));//TEMP - should use the transformation of the brick.
//		shader.loadBrickColor(brick.getColorID().RED, brick.getColorID().GREEN, brick.getColorID().BLUE);
//		
//		GL20.glEnableVertexAttribArray(0);
//		GL20.glEnableVertexAttribArray(1);
//		GL11.glDrawElements(GL11.GL_TRIANGLES, brickModel.getVertexCount(), GL11.GL_UNSIGNED_INT, 0);
//		GL20.glDisableVertexAttribArray(1);
//		GL20.glDisableVertexAttribArray(0);
//	}
	
	private Matrix4f customTransformMatrix;	//USE A MATRIX4f POOL INSTEAD
	private Matrix4f defaultScaleMatrix;	//USE A MATRIX4f POOL INSTEAD
	private Matrix4f rotationMatrix;		//USE A MATRIX4f POOL INSTEAD
	private Matrix4f translationMatrix;		//USE A MATRIX4f POOL INSTEAD
	private Vector3f brickOffset;			//USE A VECTOR3f POOL INSTEAD;
//	private void renderBricks(AbstractBrick brickType, ModelMesh brickModel, List<Integer> indexes) {
//		
//		int vertCount = brickModel.getVertexCount();
//		
//		for (int index : indexes) {
//			customTransformMatrix.setIdentity();
//			rotationMatrix.setIdentity();
//			translationMatrix.setIdentity();
//			brickOffset.set(0, 0, 0);
//			
//			shader.loadTransformationMatrix(customTransformMatrix);
//			
//			shader.loadTransformationMatrix(
//			MatrixMathHelper.buildCustomTransformationMatrix(customTransformMatrix, 
//							defaultScaleMatrix, 
//							MatrixMathHelper.rotateMatrix(rotationMatrix, zone.getRotation()), 
//							MatrixMathHelper.translateMatrix(translationMatrix, Vector3f.add(zone.getPosition(), zone.zoneIndexToZoneCoord(brickOffset, index), null))));
//		
//			EnumBrickColor colorID = brickType.getColorID();
//			shader.loadBrickColor(colorID.R, colorID.G, colorID.B);
//			
//			GL11.glDrawElements(GL11.GL_TRIANGLES, vertCount, GL11.GL_UNSIGNED_INT, 0);
//		}
//	}
	
	private void renderBrick(AbstractBrick brickType, ModelMesh brickModel, int index) {
		
		int vertCount = brickModel.getVertexCount();
		customTransformMatrix.setIdentity();
		rotationMatrix.setIdentity();
		translationMatrix.setIdentity();
		brickOffset.set(0, 0, 0);
		
//		shader.loadTransformationMatrix(customTransformMatrix);
		
		//RETHINK THIS! IT ONLY WORKS CORRECTLY WHEN ZONE IS AT (0,0,0)
		zone.zoneIndexToZoneCoord(brickOffset, index);
		brickOffset.y = brickOffset.y * Zone.BRICK_CONTAINER_HEIGHT;
		
		shader.loadTransformationMatrix(
		MatrixMathHelper.buildCustomTransformationMatrix(customTransformMatrix, 
						defaultScaleMatrix, 
						MatrixMathHelper.rotateMatrix(rotationMatrix, zone.getRotation()), 
						MatrixMathHelper.translateMatrix(translationMatrix, Vector3f.add(zone.getPosition(), brickOffset, null))));
	
		EnumBrickColor colorID = brickType.getColorID();
		shader.loadBrickColor(colorID.R, colorID.G, colorID.B);
		GL11.glDrawElements(GL11.GL_TRIANGLES, vertCount, GL11.GL_UNSIGNED_INT, 0);
	}
	
	private void unbindModel() {
		GL30.glBindVertexArray(0);
	}

	@Override
	public void release() {
		shader.release();
	}
}

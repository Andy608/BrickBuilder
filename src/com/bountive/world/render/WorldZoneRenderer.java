package com.bountive.world.render;

import math.Matrix4f;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL30;

import com.bountive.graphics.shader.BrickShader;
import com.bountive.graphics.view.AbstractCamera;
import com.bountive.graphics.view.CameraMatrixManager;
import com.bountive.util.math.MatrixMathHelper;
import com.bountive.world.zone.ModelZone;
import com.bountive.world.zone.WorldZoneManager;
import com.bountive.world.zone.Zone;

public class WorldZoneRenderer {

	private BrickShader shader;
	
	private Matrix4f transformationMatrix;
	
	public WorldZoneRenderer() {
		useShader(new BrickShader());
		transformationMatrix = new Matrix4f();
	}
	
	public void useShader(BrickShader s) {
		if (shader != null) {
			shader.release();
		}
		shader = s;
	}
	
	public void render(AbstractCamera c, WorldZoneManager zoneManager) {
		shader.bind();
		shader.loadProjectionMatrix(CameraMatrixManager.manager.getProjectionMatrix());
		shader.loadViewMatrix(c.getViewMatrix());
//		GL11.glEnable(GL11.GL_CULL_FACE);
		
		for (int i = 0; i < zoneManager.getSize(); i++) {
			for (int j = 0; j < zoneManager.getSize(); j++) {
				Zone zone = zoneManager.getZone(i, j);
				
				if (zone == null) continue;
				
				ModelZone zoneModel = zone.getModel();
				bindZone(zoneModel);
				
				transformationMatrix.setIdentity();
				MatrixMathHelper.translateMatrix(transformationMatrix, zone.getPosition());
				MatrixMathHelper.rotateMatrix(transformationMatrix, zone.getRotation());
				shader.loadTransformationMatrix(transformationMatrix);
				
				GL11.glDrawArrays(GL11.GL_TRIANGLES, 0, zoneModel.getVertexCount());
				unbindZone();
			}
		}
		
//		GL11.glDisable(GL11.GL_CULL_FACE);
		shader.unbind();
	}
	
	private void bindZone(ModelZone z) {
		GL30.glBindVertexArray(z.getVaoID());
	}
	
	private void unbindZone() {
		GL30.glBindVertexArray(0);
	}

	public void release() {
		shader.release();
	}
}

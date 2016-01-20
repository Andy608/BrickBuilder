package com.bountive.world.zone;

import com.bountive.graphics.model.ModelMesh;
import com.bountive.graphics.model.util.VBOWrapper;

public class ModelZone extends ModelMesh {
	
	public ModelZone(int vertexArrayID, int vertCount, VBOWrapper[] vboHandles) {
		super(vertexArrayID, vertCount, vboHandles);
		
	}
	
//	public void update(float[] pos, float[] cols, float[] norms, float[] tex) {
//		GL30.glBindVertexArray(vaoID);
//		updateVBOs(pos, cols, norms, tex);
//		GL30.glBindVertexArray(0);
//	}
}

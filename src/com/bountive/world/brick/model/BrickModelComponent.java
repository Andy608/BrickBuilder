package com.bountive.world.brick.model;

import java.util.ArrayList;
import java.util.List;

import math.Matrix4f;
import math.Vector2f;
import math.Vector3f;
import math.Vector4f;

import com.bountive.graphics.model.util.ModelComponents;
import com.bountive.util.math.MatrixMathHelper;
import com.bountive.world.brick.model.BrickPlaceableModel.EnumComponentType;

public class BrickModelComponent {

	private EnumComponentType componentType;
	
	private List<Float> vertexPos;
	private List<Float> normalPos;
	private List<Float> texCoords;
	
	private List<Vector4f> vertexVecPos;
	private List<Vector4f> normalVecPos;
	
	public BrickModelComponent(EnumComponentType type) {
		componentType = type;
		vertexPos = new ArrayList<>();
		normalPos = new ArrayList<>();
		texCoords = new ArrayList<>();
		
		vertexVecPos = new ArrayList<>();
		normalVecPos = new ArrayList<>();
	}
	
	public void addModel(ModelComponents model, Vector3f translation, Vector3f rotation) {
		Vector4f helper4f = new Vector4f();
		Matrix4f transformationMatrix = new Matrix4f();
		MatrixMathHelper.buildNormalTransformationMatrix(transformationMatrix, translation, rotation);
		
		for (Vector3f pos : model.getPositionsAsVector()) {
			helper4f.set(pos.x, pos.y, pos.z, 1);
			Matrix4f.transform(transformationMatrix, helper4f, helper4f);
//			Vector3f.add(pos, offset, helper3f);
			vertexPos.add(helper4f.x);
			vertexPos.add(helper4f.y);
			vertexPos.add(helper4f.z);
			vertexVecPos.add(new Vector4f(helper4f));
		}
		for (Vector3f norm : model.getNormalsAsVector()) {
			helper4f.set(norm.x, norm.y, norm.z, 1);
			Matrix4f.transform(transformationMatrix, helper4f, helper4f);
//			Vector3f.add(norm, offset, helper3f);
			normalPos.add(helper4f.x);
			normalPos.add(helper4f.y);
			normalPos.add(helper4f.z);
			normalVecPos.add(new Vector4f(helper4f));
		}
		for (Vector2f tex : model.getTextureUVsAsVector()) {
			texCoords.add(tex.x);
			texCoords.add(tex.y);
		}
	}
	
	public EnumComponentType getComponentType() {
		return componentType;
	}
	
	public List<Float> getPositions() {
		return vertexPos;
	}
	
	public List<Vector4f> getPositionsAsVec() {
		List<Vector4f> copy = new ArrayList<Vector4f>(vertexVecPos.size());
		for (Vector4f v : vertexVecPos) {
			copy.add(new Vector4f(v));
		}
		return copy;
	}
	
	public List<Float> getNormals() {
		return normalPos;
	}
	
	public List<Vector4f> getNormalsAsVec() {
		List<Vector4f> copy = new ArrayList<Vector4f>(normalVecPos.size());
		for (Vector4f v : normalVecPos) {
			copy.add(new Vector4f(v));
		}
		return copy;
	}
	
	public List<Float> getTexCoords() {
		return texCoords;
	}
	
	@Override
	public String toString() {
		return "Component Type: " + componentType;
	}
}

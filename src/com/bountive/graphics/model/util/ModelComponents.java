package com.bountive.graphics.model.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import math.Vector2f;
import math.Vector3f;

import com.bountive.util.FileUtil;
import com.bountive.util.logger.LoggerUtil;
import com.bountive.util.resource.ResourceLocation;

public final class ModelComponents {

	private ResourceLocation modelFileLocation;
	
	private static final String POSITION_PREFIX = "v ";
	private static final String UV_TEXTURE_PREFIX = "vt ";
	private static final String NORMAL_PREFIX = "vn ";
	private static final String FACE_PREFIX = "f ";
	
	private int[] indices;
	private float[] positions;
	private float[] uvTextureCoordinates;
	private float[] normals;
	
	private boolean isTextureCoords, isNormals;
	
	private long startTime, endTime;
	
	public ModelComponents(ResourceLocation location) {
		modelFileLocation = location;
		dissectFile();
	}
	
	public void dissectFile() {
		startTime = System.nanoTime();
		try {
			String[] fileContents = FileUtil.getAllLinesFromInternalFileAsArray(modelFileLocation);
			
			List<Vector3f> vertexPositions = new ArrayList<>();
			List<Vector2f> textureCoords = new ArrayList<>();
			List<Vector3f> normalsList = new ArrayList<>();
			
			String[] values;
			int faceIndex = 0;
			for (int i = 0; i < fileContents.length; i++) {
				values = fileContents[i].split(" ");
				if (fileContents[i].startsWith(POSITION_PREFIX)) {
					vertexPositions.add(new Vector3f(Float.parseFloat(values[1]), Float.parseFloat(values[2]), Float.parseFloat(values[3])));
				}
				else if (fileContents[i].startsWith(UV_TEXTURE_PREFIX)) {
					textureCoords.add(new Vector2f(Float.parseFloat(values[1]), Float.parseFloat(values[2])));
				}
				else if (fileContents[i].startsWith(NORMAL_PREFIX)) {
					normalsList.add(new Vector3f(Float.parseFloat(values[1]), Float.parseFloat(values[2]), Float.parseFloat(values[3])));
				}
				else if (fileContents[i].startsWith(FACE_PREFIX)) {
					faceIndex = i;
					positions = new float[vertexPositions.size() * 3];
					
					if (textureCoords.size() > 0) {
						isTextureCoords = true;
						uvTextureCoordinates = new float[vertexPositions.size() * 2];
					}
					
					if (normalsList.size() > 0) {
						isNormals = true;
						normals = new float[vertexPositions.size() * 3];
					}
					break;
				}
			}
			
			indices = new int[(fileContents.length - faceIndex) * 3];
			int indiceIndex = 0;
			for (int i = faceIndex; i < fileContents.length; i++) {
				values = fileContents[i].split(" ");
				
				String[] vertex1 = values[1].split("/");
				String[] vertex2 = values[2].split("/");
				String[] vertex3 = values[3].split("/");
				
				dissectVertex(vertex1, indiceIndex, textureCoords, normalsList); indiceIndex++;
				dissectVertex(vertex2, indiceIndex, textureCoords, normalsList); indiceIndex++;
				dissectVertex(vertex3, indiceIndex, textureCoords, normalsList); indiceIndex++;
			}
			
			int counter = 0;
			for (int i = 0; i < vertexPositions.size(); i++) {
				positions[counter++] = vertexPositions.get(i).x;
				positions[counter++] = vertexPositions.get(i).y;
				positions[counter++] = vertexPositions.get(i).z;
			}
		} catch (Exception e) {
			LoggerUtil.logError(getClass(), Thread.currentThread(), e);
		}
		endTime = System.nanoTime();
		System.out.println(endTime - startTime);
	}
	
	private void dissectVertex(String[] vertexData, int indiceIndex, List<Vector2f> textures, List<Vector3f> normalsList) {
		int vertex = Integer.parseInt(vertexData[0]) - 1;
		indices[indiceIndex] = vertex;
		
		if (isTextureCoords) {
			Vector2f currentTexture = textures.get(Integer.parseInt(vertexData[1]) - 1);
			uvTextureCoordinates[vertex * 2] = currentTexture.x;
			uvTextureCoordinates[vertex * 2 + 1] = 1 - currentTexture.y;
		}
		
		if (isNormals) {
			Vector3f currentNormal = normalsList.get(Integer.parseInt(vertexData[2]) - 1);
			normals[vertex * 3] = currentNormal.x;
			normals[vertex * 3 + 1] = currentNormal.y;
			normals[vertex * 3 + 2] = currentNormal.z;
		}
	}
	
	public int[] getIndices() {
		return Arrays.copyOf(indices, indices.length);
	}
	
	public float[] getPositions() {
		return Arrays.copyOf(positions, positions.length);
	}
	
	public float[] getTextureUVs() {
		if (isTextureCoords) return Arrays.copyOf(uvTextureCoordinates, uvTextureCoordinates.length);
		else {
			LoggerUtil.logWarn(getClass(), "This model does not have any texture coordinates.");
			return null;
		}
	}
	
	public float[] getNormals() {
		if (isNormals) return Arrays.copyOf(normals, normals.length);
		else {
			LoggerUtil.logWarn(getClass(), "This model does not have normals.");
			return null;
		}
	}
}

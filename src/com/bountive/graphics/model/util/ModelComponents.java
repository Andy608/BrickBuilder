//package com.bountive.graphics.model.util;
//
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.List;
//
//import math.Vector2f;
//import math.Vector3f;
//
//import com.bountive.util.FileUtil;
//import com.bountive.util.logger.LoggerUtil;
//import com.bountive.util.resource.FileResourceLocation;
//
////TODO:REDO THIS DAMN FILTH
//
//public final class ModelComponents {
//
//	private static final String POSITION_PREFIX = "v ";
//	private static final String UV_TEXTURE_PREFIX = "vt ";
//	private static final String NORMAL_PREFIX = "vn ";
//	private static final String FACE_PREFIX = "f ";
//	
//	private int[] indices;
//	private float[] positions;
//	private float[] uvTextureCoordinates;
//	private float[] normals;
//	
//	List<Vector3f> vertexPositions;
//	List<Vector2f> textureCoords;
//	List<Vector3f> normalsList;
//	
//	private boolean isTextureCoords, isNormals;
//	
//	private long startTime, endTime;
//	
//	public ModelComponents(FileResourceLocation location) {
//		dissectFile(location);
//	}
//	
//	public void dissectFile(FileResourceLocation location) {
//		startTime = System.nanoTime();
//		try {
//			String[] fileContents = FileUtil.getAllLinesFromInternalFileAsArray(location);
//			
//			vertexPositions = new ArrayList<>();
//			textureCoords = new ArrayList<>();
//			normalsList = new ArrayList<>();
//			
//			String[] values;
//			int faceIndex = 0;
//			for (int i = 0; i < fileContents.length; i++) {
//				values = fileContents[i].split(" ");
//				if (fileContents[i].startsWith(POSITION_PREFIX)) {
//					vertexPositions.add(new Vector3f(Float.parseFloat(values[1]), Float.parseFloat(values[2]), Float.parseFloat(values[3])));
//				}
//				else if (fileContents[i].startsWith(UV_TEXTURE_PREFIX)) {
//					textureCoords.add(new Vector2f(Float.parseFloat(values[1]), Float.parseFloat(values[2])));
//				}
//				else if (fileContents[i].startsWith(NORMAL_PREFIX)) {
//					normalsList.add(new Vector3f(Float.parseFloat(values[1]), Float.parseFloat(values[2]), Float.parseFloat(values[3])));
//				}
//				else if (fileContents[i].startsWith(FACE_PREFIX)) {
//					faceIndex = i;
//					positions = new float[vertexPositions.size() * 3];
//					
//					if (textureCoords.size() > 0) {
//						isTextureCoords = true;
//						uvTextureCoordinates = new float[vertexPositions.size() * 2];
//					}
//					
//					if (normalsList.size() > 0) {
//						isNormals = true;
//						normals = new float[vertexPositions.size() * 3];
//					}
//					break;
//				}
//			}
//			
//			indices = new int[(fileContents.length - faceIndex) * 3];
//			int indiceIndex = 0;
//			for (int i = faceIndex; i < fileContents.length; i++) {
//				values = fileContents[i].split(" ");
//				
//				String[] vertex1 = values[1].split("/");
//				String[] vertex2 = values[2].split("/");
//				String[] vertex3 = values[3].split("/");
//				
//				dissectVertex(vertex1, indiceIndex, textureCoords, normalsList); indiceIndex++;
//				dissectVertex(vertex2, indiceIndex, textureCoords, normalsList); indiceIndex++;
//				dissectVertex(vertex3, indiceIndex, textureCoords, normalsList); indiceIndex++;
//			}
//			
//			int counter = 0;
//			for (int i = 0; i < vertexPositions.size(); i++) {
//				positions[counter++] = vertexPositions.get(i).x;
//				positions[counter++] = vertexPositions.get(i).y;
//				positions[counter++] = vertexPositions.get(i).z;
//			}
//		} catch (Exception e) {
//			LoggerUtil.logError(getClass(), Thread.currentThread(), e);
//		}
//		endTime = System.nanoTime();
//		System.out.println(endTime - startTime);
//	}
//	
//	//TODO: FIX THE MODEL PARSER TO READ IN ALL VALUES OF VERTICES INSTEAD OF JUST INDICES.
//	private void dissectVertex(String[] vertexData, int indiceIndex, List<Vector2f> textures, List<Vector3f> normalsList) {
//		int vertex = Integer.parseInt(vertexData[0]) - 1;
//		indices[indiceIndex] = vertex;
//		
//		if (isTextureCoords) {
//			Vector2f currentTexture = textures.get(Integer.parseInt(vertexData[1]) - 1);
//			uvTextureCoordinates[vertex * 2] = currentTexture.x;
//			uvTextureCoordinates[vertex * 2 + 1] = 1 - currentTexture.y;
//		}
//		
//		if (isNormals) {
//			Vector3f currentNormal = normalsList.get(Integer.parseInt(vertexData[2]) - 1);
//			normals[vertex * 3] = currentNormal.x;
//			normals[vertex * 3 + 1] = currentNormal.y;
//			normals[vertex * 3 + 2] = currentNormal.z;
//		}
//	}
//	
//	public int[] getIndices() {
//		return Arrays.copyOf(indices, indices.length);
//	}
//	
//	public float[] getPositionsAsFloat() {
//		return Arrays.copyOf(positions, positions.length);
//	}
//	
//	public List<Vector3f> getPositionsAsVector() {
//		return vertexPositions;
//	}
//	
//	public float[] getTextureUVsAsFloat() {
//		if (isTextureCoords) return Arrays.copyOf(uvTextureCoordinates, uvTextureCoordinates.length);
//		else {
//			LoggerUtil.logWarn(getClass(), "This model does not have any texture coordinates.");
//			return null;
//		}
//	}
//	
//	public List<Vector2f> getTextureUVsAsVector() {
//		return textureCoords;
//	}
//	
//	public float[] getNormalsAsFloat() {
//		if (isNormals) return Arrays.copyOf(normals, normals.length);
//		else {
//			LoggerUtil.logWarn(getClass(), "This model does not have normals.");
//			return null;
//		}
//	}
//	
//	public List<Vector3f> getNormalsAsVector() {
//		return normalsList;
//	}
//}

package com.bountive.graphics.model.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import math.Vector2f;
import math.Vector3f;

import com.bountive.util.FileUtil;
import com.bountive.util.logger.LoggerUtil;
import com.bountive.util.resource.FileResourceLocation;

public final class ModelComponents {

	private static final String POSITION_PREFIX = "v ";
	private static final String UV_TEXTURE_PREFIX = "vt ";
	private static final String NORMAL_PREFIX = "vn ";
	private static final String FACE_PREFIX = "f ";
	
	private boolean isTextureCoords, isNormals;
	
	private long startTime, endTime;
	
	private List<Vector3f> vertexPositions;
	private List<Vector2f> textureCoords;
	private List<Vector3f> normalsList;
	
	private float[] positions;
	private float[] texCoords;
	private float[] normals;
	
	public ModelComponents(FileResourceLocation location) {
		dissectFile(location);
	}
	
	public void dissectFile(FileResourceLocation location) {
		startTime = System.nanoTime();
		try {
			String[] fileContents = FileUtil.getAllLinesFromInternalFileAsArray(location);
			
			List<Vector3f> rawPos = new ArrayList<>();
			List<Vector2f> rawtexCoords = new ArrayList<>();
			List<Vector3f> rawNorms = new ArrayList<>();
			
			String[] values;
			int faceIndex = 0;
			for (int i = 0; i < fileContents.length; i++) {
				values = fileContents[i].split(" ");
				if (fileContents[i].startsWith(POSITION_PREFIX)) {
					rawPos.add(new Vector3f(Float.parseFloat(values[1]), Float.parseFloat(values[2]), Float.parseFloat(values[3])));
				}
				else if (fileContents[i].startsWith(UV_TEXTURE_PREFIX)) {
					rawtexCoords.add(new Vector2f(Float.parseFloat(values[1]), Float.parseFloat(values[2])));
				}
				else if (fileContents[i].startsWith(NORMAL_PREFIX)) {
					rawNorms.add(new Vector3f(Float.parseFloat(values[1]), Float.parseFloat(values[2]), Float.parseFloat(values[3])));
				}
				else if (fileContents[i].startsWith(FACE_PREFIX)) {
					faceIndex = i;
					if (rawtexCoords.size() > 0) {
						isTextureCoords = true;
					}
					
					if (rawNorms.size() > 0) {
						isNormals = true;
					}
					break;
				}
			}
			
			vertexPositions = new ArrayList<>();
			textureCoords = new ArrayList<>();
			normalsList = new ArrayList<>();
			
			for (int i = faceIndex; i < fileContents.length; i++) {
				values = fileContents[i].split(" ");
				
				String[] vertex1 = values[1].split("/");
				String[] vertex2 = values[2].split("/");
				String[] vertex3 = values[3].split("/");
				
				vertexPositions.add(rawPos.get(Integer.parseInt(vertex1[0]) - 1));
				vertexPositions.add(rawPos.get(Integer.parseInt(vertex2[0]) - 1));
				vertexPositions.add(rawPos.get(Integer.parseInt(vertex3[0]) - 1));
				
				
				if (isTextureCoords) {
					textureCoords.add(rawtexCoords.get(Integer.parseInt(vertex1[1]) - 1));
					textureCoords.add(rawtexCoords.get(Integer.parseInt(vertex2[1]) - 1));
					textureCoords.add(rawtexCoords.get(Integer.parseInt(vertex3[1]) - 1));
				}
				
				if (isNormals) {
					normalsList.add(rawNorms.get(Integer.parseInt(vertex1[2]) - 1));
					normalsList.add(rawNorms.get(Integer.parseInt(vertex2[2]) - 1));
					normalsList.add(rawNorms.get(Integer.parseInt(vertex3[2]) - 1));
				}
			}
			
			positions = new float[vertexPositions.size() * 3];
			texCoords = new float[textureCoords.size() * 2];
			normals = new float[normalsList.size() * 3];
			
			int counter = 0;
			for (int i = 0; i < vertexPositions.size(); i++) {
				positions[counter++] = vertexPositions.get(i).x;
				positions[counter++] = vertexPositions.get(i).y;
				positions[counter++] = vertexPositions.get(i).z;
			}
			
			counter = 0;
			for (int i = 0; i < textureCoords.size(); i++) {
				texCoords[counter++] = textureCoords.get(i).x;
				texCoords[counter++] = textureCoords.get(i).y;
			}
			
			counter = 0;
			for (int i = 0; i < normalsList.size(); i++) {
				normals[counter++] = normalsList.get(i).x;
				normals[counter++] = normalsList.get(i).y;
				normals[counter++] = normalsList.get(i).z;
			}
			
		} catch (Exception e) {
			LoggerUtil.logError(getClass(), Thread.currentThread(), e);
		}
		endTime = System.nanoTime();
		System.out.println("FINISHED LOADING MODEL IN: " + ((float)(endTime - startTime) / 1000000f) + " MILLISECONDS");
	}
	
//	private void add(String[] vertex) {
//		
//		
//		if (isTextureCoords) {
//			Vector2f currentTexture = textureCoords.get(Integer.parseInt(vertexData[1]) - 1);
//			texCoords[vertex * 2] = currentTexture.x;
//			texCoords[vertex * 2 + 1] = 1 - currentTexture.y;
//		}
//		
//		if (isNormals) {
//			Vector3f currentNormal = normalsList.get(Integer.parseInt(vertexData[2]) - 1);
//			normals[vertex * 3] = currentNormal.x;
//			normals[vertex * 3 + 1] = currentNormal.y;
//			normals[vertex * 3 + 2] = currentNormal.z;
//		}
//	}
	
//	public int[] getIndices() {
//		return Arrays.copyOf(indices, indices.length);
//	}
	
	public float[] getPositionsAsFloat() {
		return Arrays.copyOf(positions, positions.length);
	}
	
	public List<Vector3f> getPositionsAsVector() {
		return vertexPositions;
	}
	
	public float[] getTextureUVsAsFloat() {
		if (isTextureCoords) return Arrays.copyOf(texCoords, texCoords.length);
		else {
			LoggerUtil.logWarn(getClass(), "This model does not have any texture coordinates.");
			return null;
		}
	}
	
	public List<Vector2f> getTextureUVsAsVector() {
		return textureCoords;
	}
	
	public float[] getNormalsAsFloat() {
		if (isNormals) return Arrays.copyOf(normals, normals.length);
		else {
			LoggerUtil.logWarn(getClass(), "This model does not have normals.");
			return null;
		}
	}
	
	public List<Vector3f> getNormalsAsVector() {
		return normalsList;
	}
}


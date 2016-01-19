package com.bountive.world.zone;

import java.util.ArrayList;
import java.util.List;

import math.Matrix4f;
import math.Vector3f;
import math.Vector4f;

import com.bountive.graphics.model.util.ModelBuilder;
import com.bountive.util.ArrayUtil;
import com.bountive.util.math.MatrixMathHelper;
import com.bountive.world.brick.material.EnumBrickColor;
import com.bountive.world.brick.model.BrickModelComponent;
import com.bountive.world.brick.model.BrickPlaceableModel.EnumComponentType;
import com.bountive.world.brick.model.BrickStandardModel;

public class ZoneModelFactory {

	private List<Float> positions;
	private List<Float> colors;
	private List<Float> normals;
	private List<Float> texCoords;
	
	private Matrix4f transformationMatrix;
//	private Matrix4f translationMatrix;
//	private Matrix4f rotationMatrix;
//	private Matrix4f scaleMatrix;
	
	private Vector3f offset;
	
	private EnumComponentType[] typesInUse;
	private int iterator;
	
	public ZoneModelFactory() {
		positions = new ArrayList<>();
		colors = new ArrayList<>();
		normals = new ArrayList<>();
		texCoords = new ArrayList<>();
		
		transformationMatrix = new Matrix4f();
//		translationMatrix = new Matrix4f();
//		rotationMatrix = new Matrix4f();
//		scaleMatrix = new Matrix4f();
		
		offset = new Vector3f();
	}
	
//	public void updateZoneModel(ModelZone zoneModel, Zone currentZone, Zone northZone, Zone southZone, Zone eastZone, Zone westZone) {
//		
//		positions.clear();
//		colors.clear();
//		normals.clear();
//		texCoords.clear();
//		
//		if (currentZone == null) return;
//		
//		//TAKE OUT SIDES EVENTUALLY!!
//		
//		for (int i = 0; i < Zone.ZONE_LENGTH; i++) {
//			ZoneBrick zoneBrick = currentZone.getZoneBrick(i);
//			
//			if (zoneBrick != null) {
//				List<BrickModelComponent> components = zoneBrick.getModel().getComponents();
//				
//				transformationMatrix.setIdentity();
//				
//				offset.set(0, 0, 0);
//				currentZone.getCoordinateFromZoneIndex(offset, i);
////				MatrixMathHelper.buildNormalTransformationMatrix(transformationMatrix, Vector3f.add(currentZone.getPosition(), offset, offset), currentZone.getRotation(), null);
//				
//				EnumBrickColor c = zoneBrick.getColor();
//				colors.add(c.R);
//				colors.add(c.G);
//				colors.add(c.B);
//				colors.add(c.A);
//				
//				for (BrickModelComponent comp : components) {
//					List<Vector4f> pos = comp.getPositionsAsVec();
//					for (Vector4f p : pos) {
//						Matrix4f.transform(transformationMatrix, p, p);
//						positions.add(p.x);
//						positions.add(p.y);
//						positions.add(p.z);
//					}
//					
//					List<Vector4f> norms = comp.getNormalsAsVec();
//					for(Vector4f n : norms) {
//						Matrix4f.transform(transformationMatrix, n, n);
//						positions.add(n.x);
//						positions.add(n.y);
//						positions.add(n.z);
//					}
//					
//					List<Float> tex = comp.getTexCoords();
//					for (float t : tex) {
//						texCoords.add(t);
//					}
//				}
//			}
//		}
//		zoneModel.update(ArrayUtil.convertListToFloatArray(positions), 
//				ArrayUtil.convertListToFloatArray(colors), ArrayUtil.convertListToFloatArray(normals), ArrayUtil.convertListToFloatArray(texCoords));
//	}
	
	private long startTime, endTime;
	private float percentage;
	
	public ModelZone buildNewZoneModel(Zone currentZone, Zone topZone, Zone bottomZone, Zone backZone, Zone frontZone, Zone leftZone, Zone rightZone) {
		if (currentZone == null) return null;
		
		startTime = System.nanoTime();
		
		positions.clear();
		colors.clear();
		normals.clear();
		texCoords.clear();
		
		//TAKE OUT SIDES EVENTUALLY!!
		
		for (int x = 0; x < Zone.ZONE_WIDTH; x++) {
			for (int z = 0; z < Zone.ZONE_WIDTH; z++) {
				for (int y = 0; y < Zone.ZONE_HEIGHT; y++) {
					ZoneBrick brick = currentZone.getZoneBrick(x, y, z);
					if (brick == null) continue;
					
					typesInUse = new EnumComponentType[brick.getModel().getComponents().size()];
					iterator = 0;
					
					BrickStandardModel model = brick.getModel();
					
					//LEFT SIDE
					if ((x == 0 && (leftZone == null || leftZone.getZoneBrick(Zone.ZONE_WIDTH - 1, y, z) == null)) || (x > 0 && currentZone.getZoneBrick(x - 1, y, z) == null)) {
						addComponentsToZone(currentZone, brick, x, y, z, model.left, model.leftFrontSideBevel, model.leftBackSideBevel, model.leftTopBevel, model.backTopBevel, model.frontTopBevel, model.bottom);
					}
					
					//BACK SIDE
					if (((z == 0) && (backZone == null || backZone.getZoneBrick(Zone.ZONE_WIDTH - 1, y, z) == null)) || (z > 0 && currentZone.getZoneBrick(x, y, z - 1) == null)) {
						addComponentsToZone(currentZone, brick, x, y, z, model.back, model.leftBackSideBevel, model.rightBackSideBevel, model.backTopBevel, model.rightTopBevel, model.leftTopBevel, model.bottom);
					}
					
					//RIGHT SIDE
					if ((x == (Zone.ZONE_WIDTH - 1) && (rightZone == null || rightZone.getZoneBrick(x, y, 0) == null)) || (x < Zone.ZONE_WIDTH - 1 && currentZone.getZoneBrick(x + 1, y, z) == null)) {
						addComponentsToZone(currentZone, brick, x, y, z, model.right, model.rightFrontSideBevel, model.rightBackSideBevel, model.rightTopBevel, model.frontTopBevel, model.backTopBevel, model.bottom);
					}
					
					//FRONT SIDE
					if ((z == (Zone.ZONE_WIDTH - 1) && (frontZone == null || frontZone.getZoneBrick(0, y, z) == null)) || (z < Zone.ZONE_WIDTH - 1 && currentZone.getZoneBrick(x, y, z + 1) == null)) {
						addComponentsToZone(currentZone, brick, x, y, z, model.front, model.leftFrontSideBevel, model.rightFrontSideBevel, model.frontTopBevel, model.leftTopBevel, model.rightTopBevel, model.bottom);
					}
					
					//BOTTOM SIDE
					if (((y == 0) && (bottomZone == null || bottomZone.getBrickModel(x, Zone.ZONE_HEIGHT - 1, z) == null)) || (y > 0 && currentZone.getZoneBrick(x, y - 1, z) == null)) {
						addComponentsToZone(currentZone, brick, x, y, z, model.bottom, model.leftFrontSideBevel, model.rightFrontSideBevel, model.rightBackSideBevel, model.leftBackSideBevel);
					}
					
					//TOP SIDE
					if ((y == (Zone.ZONE_HEIGHT - 1) && (topZone == null || topZone.getZoneBrick(x, 0, z) == null)) || (y < Zone.ZONE_HEIGHT - 1 && currentZone.getZoneBrick(x, y + 1, z) == null)) {
						addComponentsToZone(currentZone, brick, x, y, z, model.top, model.leftTopBevel, model.frontTopBevel, model.rightTopBevel, model.backTopBevel, 
								model.leftFrontSideBevel, model.rightFrontSideBevel, model.rightBackSideBevel, model.leftBackSideBevel);
					}
					
					int i = currentZone.getIndexFromZoneCoordinate(x, y, z);
					if (i % (Zone.ZONE_WIDTH * Zone.ZONE_WIDTH * 5) == 0 || i == Zone.ZONE_ARRAY_LENGTH - 1) {
						percentage = (float)Math.ceil(((float)i / Zone.ZONE_ARRAY_LENGTH) * 100);
						System.out.println("LOADING... " + percentage + "%");
					}
				}
			}
		}
		endTime = System.nanoTime();
		System.out.println("FINISHED LOADING ZONE MODEL IN: " + ((float)(endTime - startTime) / 1000000f) + " MILLISECONDS");
		return ModelBuilder.getBuilder().buildZone(ArrayUtil.convertListToFloatArray(positions), 
				ArrayUtil.convertListToFloatArray(colors), ArrayUtil.convertListToFloatArray(normals), ArrayUtil.convertListToFloatArray(texCoords));
	}
	
	public void addComponentsToZone(Zone currentZone, ZoneBrick brick, int x, int y, int z, BrickModelComponent... components) {
		transformationMatrix.setIdentity();
		offset.set(x, y * Zone.ZONEBRICK_HEIGHT, z);
		MatrixMathHelper.translateMatrix(transformationMatrix, offset);
		
		EnumBrickColor c = brick.getColor();
		
		for (BrickModelComponent comp : components) {
			for (int i = 0; i < typesInUse.length; i++) {
				
				if (typesInUse[i] == comp.getComponentType()) {
//					System.out.println("SAME MODEL TYPE!!");
					break;
				}
				else if (i == typesInUse.length - 1) {
					typesInUse[iterator++] = comp.getComponentType();
					
					List<Vector4f> pos = comp.getPositionsAsVec();
					List<Vector4f> norms = comp.getNormalsAsVec();
//					List<Float> tex = comp.getTexCoords();
					
					for (int index = 0; index < pos.size(); index++) {
						
						colors.add(c.R);
						colors.add(c.G);
						colors.add(c.B);
						colors.add(c.A);
						
						Vector4f vec = pos.get(index);
						Matrix4f.transform(transformationMatrix, vec, vec);
						positions.add(vec.x);
						positions.add(vec.y);
						positions.add(vec.z);
						
						vec = norms.get(index);
						Matrix4f.transform(transformationMatrix, vec, vec);
						normals.add(vec.x);
						normals.add(vec.y);
						normals.add(vec.z);
						
						//TODO: WHEN TEXCOORDS ARE ADDED, ADD THEM IN HERE!
//						List<Float> tex = comp.getTexCoords();
//						for (float t : tex) {
//							texCoords.add(t);
//						}
					}
				}
			}
		}
	}
		
		
//		for (int i = 0; i < Zone.ZONE_ARRAY_LENGTH; i++) {
//			ZoneBrick zoneBrick = currentZone.getZoneBrick(i);
//			
//			if (zoneBrick != null) {
//				List<BrickModelComponent> components = zoneBrick.getModel().getComponents();
//				EnumBrickColor c = zoneBrick.getColor();
//				colors.add(c.R);
//				colors.add(c.G);
//				colors.add(c.B);
//				colors.add(c.A);
//				
//				transformationMatrix.setIdentity();
//				
//				currentZone.getZoneCoordinateFromZoneIndex(offset, i);
//				
//				MatrixMathHelper.rotateMatrix(transformationMatrix, currentZone.getRotation());
//				MatrixMathHelper.translateMatrix(transformationMatrix, Vector3f.add(currentZone.getPosition(), offset, offset));
//				
//				for (BrickModelComponent comp : components) {
//					List<Vector4f> pos = comp.getPositionsAsVec();
//					List<Vector4f> norms = comp.getNormalsAsVec();
////					List<Float> tex = comp.getTexCoords();
//					
//					for (int index = 0; index < pos.size(); index++) {
//						
//						Vector4f vec = pos.get(index);
//						Matrix4f.transform(transformationMatrix, vec, vec);
//						positions.add(vec.x);
//						positions.add(vec.y);
//						positions.add(vec.z);
//						
//						vec = norms.get(index);
//						Matrix4f.transform(transformationMatrix, vec, vec);
//						normals.add(vec.x);
//						normals.add(vec.y);
//						normals.add(vec.z);
//						
//						//TODO: WHEN TEXCOORDS ARE ADDED, ADD THEM IN HERE!
////						List<Float> tex = comp.getTexCoords();
////						for (float t : tex) {
////							texCoords.add(t);
////						}
//					}
//				}
//			}
//			
//			if (i % (Zone.ZONE_WIDTH * Zone.ZONE_HEIGHT * (Zone.ZONE_WIDTH / 8)) == 0) {
//				percentage = (float)Math.ceil(((float)i / Zone.ZONE_LENGTH) * 100);
//				System.out.println("LOADING... " + percentage + "%");
//			}
//		}
//		endTime = System.nanoTime();
//		System.out.println("FINISHED LOADING ZONE MODEL IN: " + ((float)(endTime - startTime) / 1000000f) + " MILLISECONDS");
//		return ModelBuilder.getBuilder().buildZone(ArrayUtil.convertListToFloatArray(positions), 
//				ArrayUtil.convertListToFloatArray(colors), ArrayUtil.convertListToFloatArray(normals), ArrayUtil.convertListToFloatArray(texCoords));
//	}
}

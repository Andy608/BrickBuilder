package com.bountive.world.zone;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import math.Matrix4f;
import math.Vector3f;
import math.Vector4f;

import com.bountive.graphics.model.util.ModelBuilder;
import com.bountive.util.ArrayUtil;
import com.bountive.util.math.MatrixMathHelper;
import com.bountive.world.brick.material.BrickMaterial;
import com.bountive.world.brick.material.EnumBrickColor;
import com.bountive.world.brick.model.BrickModel;
import com.bountive.world.brick.model.BrickModelComponent;
import com.bountive.world.brick.model.BrickPlaceableModel.EnumComponentType;
import com.bountive.world.brick.model.BrickStandardModel;
import com.bountive.world.zone.ZoneBrick.EnumBrickSide;

public class Zone {

	public static final int ZONE_WIDTH = 18;
	public static final int ZONE_HEIGHT = 45;
	public static final int ZONE_ARRAY_LENGTH = ZONE_WIDTH * ZONE_WIDTH * ZONE_HEIGHT;
	public static final float ZONEBRICK_HEIGHT = 0.4f;
	
	private static Random rand = new Random();
	
	private ZoneBrick[] zoneBricks;
	
	private boolean[] isOccupied;
	
	private Vector3f position;
	public Vector3f rotation;
	
	private List<Float> positions;
	private List<Float> colors;
	private List<Float> normals;
	private List<Float> texCoords;
	
	private Matrix4f transformationMatrix;
	private Vector3f offset;
	
	private EnumComponentType[] typesInUse;
	private int iterator;
	
	private long startTime, endTime;
	private float percentage;
	
	private ModelZone zoneModel;
	
	public Zone( Vector3f pos) {
		this(pos, new Vector3f());
	}
	
//	//JUST FOR FUN
//	public Zone(Vector3f pos, Vector3f rot, boolean randomize) {
//		position = pos;
//		ROTATION = rot;
//		zoneBricks = new ZoneBrick[ZONE_ARRAY_LENGTH];
//		
//		Random rand = new Random();
//		for (int i = 0; i < Zone.ZONE_ARRAY_LENGTH; i++) {
//			if (rand.nextInt(4) == 0)
//				zoneBricks[i] = ZoneBrick.stoneBrick;
//		}
//	}
	
	public Zone(Vector3f pos, Vector3f rot) {
		position = pos;
		rotation = rot;
		zoneBricks = new ZoneBrick[ZONE_ARRAY_LENGTH];
		isOccupied = new boolean[ZONE_ARRAY_LENGTH];
		
		for (int x = 8; x < Zone.ZONE_WIDTH - 2; x++) {
			for (int z = 8; z < Zone.ZONE_WIDTH - 2; z++) {
				for (int y = 8; y < Zone.ZONE_HEIGHT; y++) {
					
					int random = rand.nextInt(2);
					ZoneBrick brick = random == 0 ? ZoneBrick.flatStoneBrick : ZoneBrick.fullStoneBrick;
					
					random = rand.nextInt(8);
					
					if (random != 0) continue;
					
					if (!isZoneOccupied(brick, x, y, z)) {
						occupyZone(brick, x, y, z);
					}
				}
			}
		}
		
		occupyZone(ZoneBrick.flatStoneBrick, 1, 0, 0);
		occupyZone(ZoneBrick.flatStoneBrick, 1, 1, 0);
		occupyZone(ZoneBrick.flatStoneBrick, 1, 2, 0);
		occupyZone(ZoneBrick.flatStoneBrick, 1, 2, 2);
		occupyZone(ZoneBrick.flatStoneBrick, 0, 1, 1);
		occupyZone(ZoneBrick.flatStoneBrick, 2, 3, 1);
		occupyZone(ZoneBrick.fullStoneBrick, 1, 0, 1);
		
		occupyZone(ZoneBrick.fullStoneBrick, 8, 0, 1);
		occupyZone(ZoneBrick.fullStoneBrick, 9, 0, 1);
		occupyZone(ZoneBrick.fullStoneBrick, 10, 0, 1);
		occupyZone(ZoneBrick.flatStoneBrick, 10, 3, 1);
		
		positions = new ArrayList<>();
		colors = new ArrayList<>();
		normals = new ArrayList<>();
		texCoords = new ArrayList<>();
		transformationMatrix = new Matrix4f();
		offset = new Vector3f();
		
		zoneModel = createZoneModel();
	}
	
	public ModelZone createZoneModel() {
		
		startTime = System.nanoTime();
		
//		for (int x = 0; x < Zone.ZONE_WIDTH; x++) {
//			for (int z = 0; z < Zone.ZONE_WIDTH; z++) {
//				for (int y = 0; y < Zone.ZONE_HEIGHT; y++) {
//					
//					int i = getIndexFromZoneCoordinate(x, y, z);
//					if (i % (Zone.ZONE_WIDTH * Zone.ZONE_WIDTH) == 0 || i == Zone.ZONE_ARRAY_LENGTH - 1) {
//						percentage = (float)Math.ceil(((float)i / Zone.ZONE_ARRAY_LENGTH) * 100);
//						System.out.println("LOADING... " + percentage + "%");
//					}
//					
//					ZoneBrick brick = getZoneBrick(x, y, z);
//					if (brick == null) continue;
////					
//					typesInUse = new EnumComponentType[brick.getModel().getComponents().size()];
//					iterator = 0;
//					
//					BrickStandardModel model = brick.getModel();
//					
//					//LEFT SIDE
//					if (/*(x == 0 && (leftZone == null || leftZone.getZoneBrick(Zone.ZONE_WIDTH - 1, y, z) == null)) || */
//							(x == 0) || (x > 0 && getZoneBrick(x - 1, y, z) == null)) {
//						addComponentsToZone(brick, x, y, z, model.left, model.leftFrontSideBevel, model.leftBackSideBevel, model.leftTopBevel, model.backTopBevel, model.frontTopBevel, model.bottom);
//					}
//					
//					//BACK SIDE
//					if (/*((z == 0) && (backZone == null || backZone.getZoneBrick(Zone.ZONE_WIDTH - 1, y, z) == null)) || */
//							(z == 0) || (z > 0 && getZoneBrick(x, y, z - 1) == null)) {
//						addComponentsToZone(brick, x, y, z, model.back, model.leftBackSideBevel, model.rightBackSideBevel, model.backTopBevel, model.rightTopBevel, model.leftTopBevel, model.bottom);
//					}
//					
//					//RIGHT SIDE
//					if (/*(x == (Zone.ZONE_WIDTH - 1) && (rightZone == null || rightZone.getZoneBrick(x, y, 0) == null)) || */
//							(x == (Zone.ZONE_WIDTH - 1)) || (x < Zone.ZONE_WIDTH - 1 && getZoneBrick(x + 1, y, z) == null)) {
//						addComponentsToZone(brick, x, y, z, model.right, model.rightFrontSideBevel, model.rightBackSideBevel, model.rightTopBevel, model.frontTopBevel, model.backTopBevel, model.bottom);
//					}
//					
//					//FRONT SIDE
//					if (/*(z == (Zone.ZONE_WIDTH - 1) && (frontZone == null || frontZone.getZoneBrick(0, y, z) == null)) || */
//							(z == (Zone.ZONE_WIDTH - 1)) || (z < Zone.ZONE_WIDTH - 1 && getZoneBrick(x, y, z + 1) == null)) {
//						addComponentsToZone(brick, x, y, z, model.front, model.leftFrontSideBevel, model.rightFrontSideBevel, model.frontTopBevel, model.leftTopBevel, model.rightTopBevel, model.bottom);
//					}
//					
//					//BOTTOM SIDE
//					if (/*((y == 0) && (bottomZone == null || bottomZone.getBrickModel(x, Zone.ZONE_HEIGHT - 1, z) == null)) || */
//							(y == 0) || (y > 0 && getZoneBrick(x, y - 1, z) == null)) {
//						addComponentsToZone(brick, x, y, z, model.bottom, model.leftFrontSideBevel, model.rightFrontSideBevel, model.rightBackSideBevel, model.leftBackSideBevel);
//					}
//					
//					//TOP SIDE
//					if (/*(y == (Zone.ZONE_HEIGHT - 1) && (topZone == null || topZone.getZoneBrick(x, 0, z) == null)) || */
//							(y == (Zone.ZONE_HEIGHT - 1)) || (y < Zone.ZONE_HEIGHT - 1 && getZoneBrick(x, y + 1, z) == null)) {
//						addComponentsToZone(brick, x, y, z, model.top, model.leftTopBevel, model.frontTopBevel, model.rightTopBevel, model.backTopBevel, 
//								model.leftFrontSideBevel, model.rightFrontSideBevel, model.rightBackSideBevel, model.leftBackSideBevel);
//					}
//				}
//			}
//		}
		
		for (int x = 0; x < Zone.ZONE_WIDTH; x++) {
			for (int z = 0; z < Zone.ZONE_WIDTH; z++) {
				for (int y = 0; y < Zone.ZONE_HEIGHT; y++) {
					
					int i = getIndexFromZoneCoordinate(x, y, z);
					if (i % (Zone.ZONE_WIDTH * Zone.ZONE_WIDTH) == 0 || i == Zone.ZONE_ARRAY_LENGTH - 1) {
						percentage = (float)Math.ceil(((float)i / Zone.ZONE_ARRAY_LENGTH) * 100);
						System.out.println("LOADING... " + percentage + "%");
					}
					
					ZoneBrick brick = getZoneBrick(x, y, z);
					if (brick == null) continue;
					
					typesInUse = new EnumComponentType[brick.getModel().getComponents().size()];
					iterator = 0;
					
					BrickStandardModel model = brick.getModel();
					
					//LEFT SIDE
					if (/*(x == 0 && (leftZone == null || leftZone.getZoneBrick(Zone.ZONE_WIDTH - 1, y, z) == null)) || */
							(x == 0) || (x > 0 && renderSide(brick, x - 1, y, z, EnumBrickSide.LEFT))) {
						addComponentsToZone(brick, x, y, z, model.left, model.leftFrontSideBevel, model.leftBackSideBevel, model.leftTopBevel, model.backTopBevel, model.frontTopBevel, model.bottom);
					}
					
					//RIGHT SIDE
					if (/*(x == (Zone.ZONE_WIDTH - 1) && (rightZone == null || rightZone.getZoneBrick(x, y, 0) == null)) || */
							(x + model.BRICK_LENGTH - 1 >= (Zone.ZONE_WIDTH - 1)) || (x + model.BRICK_LENGTH - 1 < Zone.ZONE_WIDTH - 1 && renderSide(brick, x + model.BRICK_LENGTH, y, z, EnumBrickSide.RIGHT))) {
						addComponentsToZone(brick, x, y, z, model.right, model.rightFrontSideBevel, model.rightBackSideBevel, model.rightTopBevel, model.frontTopBevel, model.backTopBevel, model.bottom);
					}
					
					//BACK SIDE
					if (/*((z == 0) && (backZone == null || backZone.getZoneBrick(Zone.ZONE_WIDTH - 1, y, z) == null)) || */
							(z == 0) || (z > 0 && renderSide(brick, x, y, z - 1, EnumBrickSide.BACK))) {
						addComponentsToZone(brick, x, y, z, model.back, model.leftBackSideBevel, model.rightBackSideBevel, model.backTopBevel, model.rightTopBevel, model.leftTopBevel, model.bottom);
					}
					
					//FRONT SIDE
					if (/*(z == (Zone.ZONE_WIDTH - 1) && (frontZone == null || frontZone.getZoneBrick(0, y, z) == null)) || */
							(z + model.BRICK_WIDTH - 1 >= (Zone.ZONE_WIDTH - 1)) || (z + model.BRICK_WIDTH - 1 < Zone.ZONE_WIDTH - 1 && renderSide(brick, x, y, z + model.BRICK_WIDTH, EnumBrickSide.FRONT))) {
						addComponentsToZone(brick, x, y, z, model.front, model.leftFrontSideBevel, model.rightFrontSideBevel, model.frontTopBevel, model.leftTopBevel, model.rightTopBevel, model.bottom);
					}
					
					//BOTTOM SIDE
					if (/*((y == 0) && (bottomZone == null || bottomZone.getBrickModel(x, Zone.ZONE_HEIGHT - 1, z) == null)) || */
							(y == 0) || (y > 0 && renderSide(brick, x, y - 1, z, EnumBrickSide.BOTTOM))) {
						addComponentsToZone(brick, x, y, z, model.bottom, model.leftFrontSideBevel, model.rightFrontSideBevel, model.rightBackSideBevel, model.leftBackSideBevel);
					}
					
					//TOP SIDE
					if (/*(y == (Zone.ZONE_HEIGHT - 1) && (topZone == null || topZone.getZoneBrick(x, 0, z) == null)) || */
							(y + model.BRICK_HEIGHT - 1 >= (Zone.ZONE_HEIGHT - 1)) || (y + model.BRICK_HEIGHT - 1 < Zone.ZONE_HEIGHT - 1 && renderSide(brick, x, y + model.BRICK_HEIGHT, z, EnumBrickSide.TOP))) {
						addComponentsToZone(brick, x, y, z, model.top, model.leftTopBevel, model.frontTopBevel, model.rightTopBevel, model.backTopBevel, 
								model.leftFrontSideBevel, model.rightFrontSideBevel, model.rightBackSideBevel, model.leftBackSideBevel);
					}
				}
			}
		}
		
		endTime = System.nanoTime();
		System.out.println("FINISHED LOADING ZONE MODEL IN: " + ((float)(endTime - startTime) / 1000000f) + " MILLISECONDS");
		return ModelBuilder.getBuilder().buildZone(ArrayUtil.convertListToFloatArray(positions), 
				ArrayUtil.convertListToFloatArray(colors), ArrayUtil.convertListToFloatArray(normals), ArrayUtil.convertListToFloatArray(texCoords));
	}
	
	public void addComponentsToZone(ZoneBrick brick, int x, int y, int z, BrickModelComponent... components) {
		transformationMatrix.setIdentity();
		offset.set(x, y * Zone.ZONEBRICK_HEIGHT, z);
		MatrixMathHelper.translateMatrix(transformationMatrix, offset);
		
		EnumBrickColor c = brick.getColor();
		
		for (BrickModelComponent comp : components) {
			for (int i = 0; i < typesInUse.length; i++) {
				
				if (typesInUse[i] == comp.getComponentType()) {
					break;
				}
				else if (i == typesInUse.length - 1) {
					typesInUse[iterator++] = comp.getComponentType();
					
					List<Vector4f> pos = comp.getPositionsAsVec();
					List<Vector4f> norms = comp.getNormalsAsVec();
					//List<Float> tex = comp.getTexCoords();
					
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
					}
				}
			}
		}
	}
	
	private boolean renderSide(ZoneBrick brick, int x, int y, int z, EnumBrickSide flatSide) {
		BrickModel model = brick.getModel();
		
		int length = model.BRICK_LENGTH;
		int width = model.BRICK_WIDTH;
		int height = model.BRICK_HEIGHT;
		
		
		switch (flatSide) {
		case LEFT: case RIGHT: length = 1; break;
		case BACK: case FRONT: width = 1; break;
		case BOTTOM: case TOP: height = 1; break;
		}
		
		for (int lengthIndex = 0; lengthIndex < length; lengthIndex++) {
			for (int widthIndex = 0; widthIndex < width; widthIndex++) {
				for (int heightIndex = 0; heightIndex < height; heightIndex++) {
					int index = getIndexFromZoneCoordinate(lengthIndex + x, heightIndex + y, widthIndex + z);
					
					if (index > isOccupied.length || (index < isOccupied.length && !isOccupied[index])) {
						return true;
					}
				}
			}
		}
		return false;
	}
	
	public boolean isZoneOccupied(ZoneBrick brick, int startingX, int startingY, int startingZ) {
		if (brick == null) return false;
		
		BrickModel model = brick.getModel();
		
		for (int lengthIndex = 0; lengthIndex < model.BRICK_LENGTH; lengthIndex++) {
			for (int widthIndex = 0; widthIndex < model.BRICK_WIDTH; widthIndex++) {
				for (int heightIndex = 0; heightIndex < model.BRICK_HEIGHT; heightIndex++) {
					int index = getIndexFromZoneCoordinate(lengthIndex + startingX, heightIndex + startingY, widthIndex + startingZ);
					
					if (index < isOccupied.length && isOccupied[index]) {
						return true;
					}
				}
			}
		}
		return false;
	}
	
	public void occupyZone(ZoneBrick brick, int startingX, int startingY, int startingZ) {
		BrickModel model = brick.getModel();
		
		for (int lengthIndex = 0; lengthIndex < model.BRICK_LENGTH; lengthIndex++) {
			for (int widthIndex = 0; widthIndex < model.BRICK_WIDTH; widthIndex++) {
				for (int heightIndex = 0; heightIndex < model.BRICK_HEIGHT; heightIndex++) {
					int index = getIndexFromZoneCoordinate(lengthIndex + startingX, heightIndex + startingY, widthIndex + startingZ);
					
					if (index < isOccupied.length && !isOccupied[index]) {
						isOccupied[index] = true;
//						System.out.println("OCCUPYING: " + (lengthIndex + startingX) + " | " + (heightIndex + startingY) + " | " + (widthIndex + startingZ));
					}
				}
			}
		}
		zoneBricks[getIndexFromZoneCoordinate(startingX, startingY, startingZ)] = brick;
//		System.out.println("PLACING BLOCK AT: " + startingX + " | " + startingY + " | " + startingZ);
	}
	
	public int getIndexFromZoneCoordinate(int x, int y, int z) {
		return x + (z * ZONE_WIDTH) + (y * ZONE_WIDTH * ZONE_WIDTH);
	}
	
	public Vector3f getZoneCoordinateFromZoneIndex(Vector3f source, int index) {
		source.x = index % ZONE_WIDTH;
		index /= ZONE_WIDTH;
		source.z = index % ZONE_WIDTH;
		index /= ZONE_WIDTH;
		source.y = index * ZONEBRICK_HEIGHT;
		return source;
	}
	
	public ZoneBrick getZoneBrick(int x, int y, int z) {
		return zoneBricks[getIndexFromZoneCoordinate(x, y, z)];
	}
	
	public ZoneBrick getZoneBrick(int i) {
		return zoneBricks[i];
	}
	
	public BrickMaterial getBrickMaterial(int x, int y, int z) {
		return getZoneBrick(x, y, z).getMaterial();
	}
	
	public BrickMaterial getBrickMaterial(int i) {
		return getZoneBrick(i).getMaterial();
	}
	
	public BrickModel getBrickModel(int x, int y, int z) {
		return getZoneBrick(x, y, z).getModel();
	}
	
	public BrickModel getBrickModel(int i) {
		return getZoneBrick(i).getModel();
	}
	
	public Vector3f getPosition() {
		return new Vector3f(position);
	}
	
	public Vector3f getRotation() {
		return new Vector3f(rotation);
	}
	
	public ModelZone getModel() {
		return zoneModel;
	}
}

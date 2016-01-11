package com.bountive.world.brick.list;

import java.util.List;

import com.bountive.util.FileUtil;
import com.bountive.util.math.MathHelper;
import com.bountive.world.brick.AbstractBrick;
import com.bountive.world.brick.helper.EnumBrickColor;
import com.bountive.world.brick.helper.EnumBrickModel;

public abstract class AbstractBrickList {

	private AbstractBrick[][] brickList;
	private static StringBuilder toString = new StringBuilder();
	
	public AbstractBrickList(int materialID, List<EnumBrickColor> colorTypes, List<EnumBrickModel> modelTypes) {
		brickList = new AbstractBrick[colorTypes.size()][modelTypes.size()];
		
		for (int i = 0; i < brickList.length; i++) {
			for (int j = 0; j < brickList[i].length; j++) {
				brickList[i][j] = createBrick(materialID, colorTypes.get(i), modelTypes.get(j));
			}
		}
	}
	
	public AbstractBrickList(int materialID, EnumBrickColor[] colorTypes, EnumBrickModel[] modelTypes) {
		brickList = new AbstractBrick[colorTypes.length][modelTypes.length];
		
		for (int i = 0; i < brickList.length; i++) {
			for (int j = 0; j < brickList[i].length; j++) {
				brickList[i][j] = createBrick(materialID, colorTypes[i], modelTypes[j]);
			}
		}
	}
	
	protected abstract AbstractBrick createBrick(int materialID, EnumBrickColor colorID, EnumBrickModel modelID);
	
	public final int getMaterialID() {
		return brickList[0][0].getMaterialID();
	}
	
	public final int getColorAmount() {
		return brickList.length;
	}
	
	public final int getModelAmount() {
		return brickList[0].length;
	}
	
	public final AbstractBrick getBrick(int colorIndex, int modelIndex) {
		return brickList[MathHelper.clampInt(colorIndex, 0, brickList.length)][MathHelper.clampInt(modelIndex, 0, brickList[0].length)];
	}
	
	@Override
	public String toString() {
		
		for (int i = 0; i < brickList.length; i++) {
			for (int j = 0; j < brickList[0].length; j++) {
				toString.append((j == 0 ? "{" : "") + brickList[i][j] + (j == brickList[0].length - 1 ? "}" + FileUtil.ENTER : " <> "));
			}
		}
		return toString.toString();
	}
}

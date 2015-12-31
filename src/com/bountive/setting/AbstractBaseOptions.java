package com.bountive.setting;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import math.Vector2f;

import com.bountive.util.ArrayUtil;
import com.bountive.util.SystemUtil;
import com.bountive.util.SystemUtil.EnumOS;
import com.bountive.util.logger.LoggerUtil;
import com.bountive.util.resource.ResourceLocation;

public abstract class AbstractBaseOptions {

	protected static final String EXTENSION = ".txt";
	protected static final char DEFAULT_DELIMITER = '=', SEPARATOR = ',';
	protected static final EnumOS os = SystemUtil.getOSType();
	protected static final ResourceLocation OPTIONS_DIR = new ResourceLocation(ResourceLocation.APPDATA_DIRECTORY, "options");
	
	protected abstract void initDefaultOptions();
	public abstract void loadOptionsFromFile();
	protected abstract void setDefaultOptions();
	public abstract void storeOptionsInFile();
	
	/**
	 * Reads all information in from a options file and breaks up each line into a string and puts it in an array of Strings.
	 * Then trims the array to take out any lines with blank space in them.
	 * @param optionsFile
	 * @return
	 */
	protected String[] readOptionsFile(File optionsFile) throws IOException {
		List<String> lineValues = null;
		lineValues = Files.readAllLines(Paths.get(optionsFile.getPath()), StandardCharsets.UTF_8);
		return trimExtraSpace(lineValues);
	}
	
	protected String[] trimExtraSpace(List<String> list) {
		
		for (int i = 0; i < list.size(); i++) {
			list.set(i, list.get(i).trim());
			if (list.get(i).isEmpty()) {
				list.remove(i);
				i--;
			}
			else {
				list.set(i, list.get(i).replaceAll("\\s+",""));
			}
		}
		return list.toArray(new String[list.size()]);
	}
	
	protected Boolean parseBoolean(String s) {
		if (s != null) {
			if (s.equalsIgnoreCase("true")) return true;
			else if (s.equalsIgnoreCase("false")) return false;
		}
		return null;
	}
	
	protected boolean getBooleanValue(Boolean savedValue, boolean defaultValue) {
		if (savedValue == null) {
			LoggerUtil.logWarn(Thread.currentThread(), "Boolean value cannot be null. Return default.");
			return defaultValue;
		}
		else return savedValue.booleanValue();
	}
	
	protected int getIntValue(Integer savedValue, int defaultValue) {
		if (savedValue == null) return defaultValue;
		else return savedValue.intValue();
	}
	
	protected Vector2f getVector2fValue(Vector2f savedValue, Vector2f defaultValue) throws IllegalStateException {
		if (defaultValue == null && savedValue == null) {
			throw new IllegalStateException("Both savedValue and defaultValue cannot be null! Cannot set value to a null reference.");
		}
		
		if (savedValue == null) return defaultValue;
		else return savedValue;
	}
	
	protected boolean getSingleBooleanFromOption(String fileOption, boolean defaultValue, char delimiter) {
		return getBooleanValue(parseBoolean(fileOption.substring(fileOption.indexOf(delimiter) + 1)), defaultValue);
	}
	
	protected int getSingleIntegerFromOption(String fileOption, int defaultValue, char delimiter) throws NumberFormatException {
		try {
			return Integer.parseInt(fileOption.substring(fileOption.indexOf(delimiter) + 1));
		} catch (NumberFormatException e) {
			LoggerUtil.logWarn(Thread.currentThread(), e, "Could not parse integer from string. Returning default.", false);
			return defaultValue;
		}
	}
	
	protected int getSingleIntegerFromOption(String fileOption, int defaultValue, char startingDelimiter, char endingDelimiter) {
		try {
			return Integer.parseInt(fileOption.substring(fileOption.indexOf(startingDelimiter) + 1, fileOption.indexOf(endingDelimiter)));
		} catch (NumberFormatException e) {
			LoggerUtil.logWarn(Thread.currentThread(), e, "Could not parse integer from string. Returning default.", false);
			return defaultValue;
		}
	}
	
	protected float getSingleFloatFromOption(String fileOption, float defaultValue, char delimiter) {
		try {
			return Float.parseFloat(fileOption.substring(fileOption.indexOf(delimiter) + 1));
		} catch (NumberFormatException e) {
			LoggerUtil.logWarn(Thread.currentThread(), e, "Could not parse float from string. Returning default.", false);
			return defaultValue;
		}
	}
	
	protected int[] getMultipleIntegersFromOption(String fileOption, int[] defaultValue, char startingDelimiter, char inBetweenDelimiter) throws NumberFormatException {
		List<Integer> customValues = new ArrayList<Integer>();
		
		try {
			int startDelimiterIndex = 0, endDelimiterIndex;
			char[] fileOptionCharArray = fileOption.toCharArray();
			for (int i = 0; i < fileOptionCharArray.length; i++) {
				//if i == startingDelimiter, wait for inBetweenDelimiter.
				//once at inBetweenDelimiter, add string from startingDelimiter to inBetweenDelimiter to arrayList.
				//if i == inBetweenDelimiter, wait for inBetweenDelimiter.
				//once at inBetweenDelimiter, add string from inBetweenDelimiter to inBetweenDelimiter to arrayList.
				//if reach the end of string, add from beginningDelimiter to end of string to arrayList.
				
				if (fileOptionCharArray[i] == startingDelimiter) {
					startDelimiterIndex = i;
				}
				else if (fileOptionCharArray[i] == inBetweenDelimiter) {
					if (fileOptionCharArray[startDelimiterIndex] == startingDelimiter || fileOptionCharArray[startDelimiterIndex] == inBetweenDelimiter) {
						endDelimiterIndex = i;
						customValues.add(Integer.parseInt(fileOption.substring(startDelimiterIndex + 1, endDelimiterIndex)));
						startDelimiterIndex = i;
					}
				}
				else if (i == fileOptionCharArray.length - 1) {
					customValues.add(Integer.parseInt(fileOption.substring(startDelimiterIndex + 1)));
				}
			}
			
			return ArrayUtil.convertListToIntegerArray(customValues);
			
		} catch (NumberFormatException e) {
			LoggerUtil.logWarn(Thread.currentThread(), e, "Could not parse multiple integers from string. Returning default.", false);
			return defaultValue;
		}
	}
}

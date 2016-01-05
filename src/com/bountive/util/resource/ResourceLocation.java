package com.bountive.util.resource;

import com.bountive.util.FileUtil;

public class ResourceLocation extends ResourceBase {

	protected static final char DEFAULT_DELIMITER = '/';
	
	private String parentDirectory;
	private String resourceName;
	private boolean inJar;
	
	public ResourceLocation(String directory, String path, boolean isInJar) {
		this(directory, path, DEFAULT_DELIMITER, isInJar);
	}
	
	public ResourceLocation(String directory, String path, char fileSep, boolean isInJar) {
		parentDirectory = fixFileSeparator(directory, fileSep, isInJar);
		resourceName = fixFileSeparator(path, fileSep, isInJar);
		inJar = isInJar;
	}
	
	/**
	 * Takes in a resource file path and replaces forward slashes with the system dependent file separator.
	 * @param string : The resource path.
	 * @return : A resource path with system dependent file separators.
	 */
	private String fixFileSeparator(String string, char delimiter, boolean inJar) {
		StringBuilder b = new StringBuilder();
		for (int i = 0; i < string.length(); i++) {
			char currentChar = string.charAt(i);
			if (currentChar == delimiter) {
				if (inJar) b.append("/");
				else b.append(FileUtil.SEP);
			}
			else {
				b.append(currentChar);
			}
		}
		return b.toString();
	}
	
	public String getParentDir() {
		return parentDirectory;
	}
	
	public String getResourceName() {
		return resourceName;
	}
	
	public String getFullPath() {
		return parentDirectory + (inJar ? "/" : FileUtil.SEP)+ resourceName;
	}
	
	@Override
	public String toString() {
		return getFullPath();
	}
}

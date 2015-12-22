package com.bountive.resource;

public class ResourceLocation extends ResourceBase {

	protected static final char DEFAULT_DELIMITER = '/';
	
	private String parentDirectory;
	private String resourceName;
	
	public ResourceLocation(String resourcePath) {
		this(PARENT_DIRECTORY, resourcePath, DEFAULT_DELIMITER);
	}
	
	public ResourceLocation(String directory, String path) {
		parentDirectory = directory;
		parentDirectory = fixFileSeparator(directory, DEFAULT_DELIMITER);
		resourceName = fixFileSeparator(path, DEFAULT_DELIMITER);
	}
	
	public ResourceLocation(String resourcePath, char fileSep) {
		this(PARENT_DIRECTORY, resourcePath, fileSep);
	}
	
	public ResourceLocation(String directory, String path, char fileSep) {
		parentDirectory = directory;
		parentDirectory = fixFileSeparator(directory, fileSep);
		resourceName = fixFileSeparator(path, fileSep);
	}
	
	/**
	 * Takes in a resource file path and replaces forward slashes with the system dependent file separator.
	 * @param string : The resource path.
	 * @return : A resource path with system dependent file separators.
	 */
	private String fixFileSeparator(String string, char delimiter) {
		StringBuilder b = new StringBuilder();
		for (int i = 0; i < string.length(); i++) {
			char currentChar = string.charAt(i);
			if (currentChar == delimiter) {
				b.append(S);
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
		return parentDirectory + resourceName;
	}
}

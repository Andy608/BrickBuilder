package com.bountive.util.resource;

import com.bountive.start.Info;
import com.bountive.util.SystemUtil;
import com.bountive.util.SystemUtil.EnumOS;

public abstract class ResourceBase {

	public static final String APPDATA_DIRECTORY;
	public static final String JAR_DIRECTORY = "";
	protected static final EnumOS SYSTEM_OS = SystemUtil.getOSType();
	public static final String SEP = System.getProperty("file.separator");
	
	static {
		switch (SYSTEM_OS) {
		case WINDOWS: APPDATA_DIRECTORY = System.getenv("APPDATA") + SEP + Info.NAME; break;
		case OSX: APPDATA_DIRECTORY = System.getProperty("user.home") + SEP + "Library" + SEP + "Application Support" + SEP + Info.NAME; break;
		default: APPDATA_DIRECTORY = System.getProperty("user.home") + SEP + Info.NAME; break;
		}
	}
}

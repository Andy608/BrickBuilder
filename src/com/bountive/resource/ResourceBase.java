package com.bountive.resource;

import com.bountive.start.Info;
import com.bountive.util.Util;
import com.bountive.util.Util.EnumOS;

public abstract class ResourceBase {

	public static final String PARENT_DIRECTORY;
	protected static final EnumOS SYSTEM_OS = Util.getOSType();
	protected static final String S = System.getProperty("file.separator");
	
	static {
		switch (SYSTEM_OS) {
		case WINDOWS: PARENT_DIRECTORY = System.getenv("APPDATA") + S + Info.NAME; break;
		case OSX: PARENT_DIRECTORY = System.getProperty("user.home") + S + "Library" + S + "Application Support" + S + Info.NAME; break;
		default: PARENT_DIRECTORY = System.getProperty("user.home") + S + Info.NAME; break;
		}
	}
}

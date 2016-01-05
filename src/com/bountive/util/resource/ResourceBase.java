package com.bountive.util.resource;

import com.bountive.start.Info;
import com.bountive.util.FileUtil;
import com.bountive.util.SystemUtil;
import com.bountive.util.SystemUtil.EnumOS;

public abstract class ResourceBase {

	public static final String GAME_APPDATA_DIRECTORY;
	protected static final EnumOS SYSTEM_OS = SystemUtil.getOSType();
	
	static {
		switch (SYSTEM_OS) {
		case WINDOWS: GAME_APPDATA_DIRECTORY = System.getenv("APPDATA") + FileUtil.SEP + Info.NAME; break;
		case OSX: GAME_APPDATA_DIRECTORY = System.getProperty("user.home") + FileUtil.SEP + "Library" + FileUtil.SEP + "Application Support" + FileUtil.SEP + Info.NAME; break;
		default: GAME_APPDATA_DIRECTORY = System.getProperty("user.home") + FileUtil.SEP + Info.NAME; break;
		}
	}
}

package com.bountive.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Util {

	public static String getDate(String format) {
		return new SimpleDateFormat(format).format(new Date());
	}
	
	public static Boolean parseBoolean(String s) {
		if (s != null) {
			if (s.equalsIgnoreCase("true")) return true;
			else if (s.equalsIgnoreCase("false")) return false;
		}
		return null;
	}
	
	public static EnumOS getOSType() {
        String s = System.getProperty("os.name").toLowerCase();
        return s.contains("win") ? EnumOS.WINDOWS : (s.contains("mac") ? EnumOS.OSX : (s.contains("linux") ? EnumOS.LINUX : (s.contains("unix") ? EnumOS.LINUX : 
        	(s.contains("solaris") ? EnumOS.SOLARIS : (s.contains("sunos") ? EnumOS.SOLARIS : EnumOS.UNKNOWN)))));
    }
	
	public static enum EnumOS {
    	WINDOWS,
    	OSX,
    	LINUX,
        SOLARIS,
        UNKNOWN;
    }
}

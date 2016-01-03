package com.bountive.util;

import java.util.Iterator;
import java.util.List;

public class ArrayUtil {

	public static int[] convertListToIntegerArray(List<Integer> integers) {
	    int[] intArray = new int[integers.size()];
	    Iterator<Integer> iterator = integers.iterator();
	    
	    for (int i = 0; i < intArray.length; i++) {
	    	intArray[i] = iterator.next().intValue();
	    }
	    return intArray;
	}
}

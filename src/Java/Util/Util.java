package Java.Util;

import java.util.Arrays;

public class Util {
	
	public Util() {}
	
    public static int[] stringArrayToIntArray(String[] stringArray) {
    	int[] intArray = new int[stringArray.length];
    	
    	for (int i = 0; i < stringArray.length; i++) {
    		intArray[i] = Integer.parseInt(stringArray[i]);
    	}
    	
    	return intArray;
    }

	public static String getLine() {
		return "--------------------------------";
	}


}

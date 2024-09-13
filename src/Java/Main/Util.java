package Java.Main;// For random helper methods.

public class Util {
	
	public Util() {
		
	}
	
	// General
	
    public static int[] stringArrayToIntArray(String[] stringArray) {
    	int[] intArray = new int[stringArray.length];
    	
    	for (int i = 0; i < stringArray.length; i++) {
    		intArray[i] = Integer.parseInt(stringArray[i]);
    	}
    	
    	return intArray;
    }
    
    //TODO: why do we even need this?h
    public static float[] intArrayToFloatArray(int[] intArray) {
    	float[] floatArray = new float[intArray.length];
    	
    	for (int i = 0; i < intArray.length; i++) {
    		floatArray[i] = intArray[i];
    	}
    	
    	return floatArray;
    }

    
   
}

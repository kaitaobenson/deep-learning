// For random helper methods.

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
    
    
    // Math
    
    // Squashes numbers between 1 and 0
    public static double sigmoid(double value) {
    	double negExp = Math.exp(-value);
    	return 1 / (1 + negExp);
    }
    
    // Squashes numbers between 1 and -1
    public static double tanh(double value) {
    	double posExp = Math.exp(value);
    	double negExp = Math.exp(-value);
    	return (posExp - negExp) / (posExp + negExp);
    }
    
    // Returns the same value with a min of 0
    public static double ReLU(double value) {
    	if (value < 0) {
    		return 0;
    	}
    	return value;
    }
    
    
   
}

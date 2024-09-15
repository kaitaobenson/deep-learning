package Java.ProgramFlow;

public class Util {
	
	public Util() {}
	
    public static int[] stringArrayToIntArray(String[] stringArray) {
    	int[] intArray = new int[stringArray.length];
    	
    	for (int i = 0; i < stringArray.length; i++) {
    		intArray[i] = Integer.parseInt(stringArray[i]);
    	}
    	
    	return intArray;
    }

    public static float[] intArrayToFloatArray(int[] intArray) {
    	float[] floatArray = new float[intArray.length];
    	
    	for (int i = 0; i < intArray.length; i++) {
    		floatArray[i] = intArray[i];
    	}
    	
    	return floatArray;
    }

	public static String getLine() {
		return "--------------------------------";
	}
}

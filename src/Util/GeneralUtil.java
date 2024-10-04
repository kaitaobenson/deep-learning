package Util;

public class GeneralUtil {

    public static final String lineBreak =  "--------------------------------";

    // Randoms
    public static float randomFloat(float min, float max) {
        return (float) (min + (max - min) * Math.random());
    }

    public static int randomInt(int min, int max) {
        return (int) (min + (max - min) * Math.random());
    }

    // Arrays
    public static int[] stringArrayToIntArray(String[] stringArray) {
        int[] intArray = new int[stringArray.length];

        for (int i = 0; i < stringArray.length; i++) {
            intArray[i] = Integer.parseInt(stringArray[i]);
        }

        return intArray;
    }

}

package util;

public class GeneralUtil {

    public static int[] stringArrayToIntArray(String[] array) {
        int[] newArray = new int[array.length];

        for (int i = 0; i < array.length; i++) {
            newArray[i] = Integer.parseInt(array[i]);
        }

        return newArray;
    }
}

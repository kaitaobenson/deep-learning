package Java.Math;

public class RandomGenerator {

    public static double randomDouble(double min, double max) {
        return min + (max - min) * Math.random();
    }

    public static int randomInt(int min, int max) {
        return (int) (min + (max - min) * Math.random());
    }
}

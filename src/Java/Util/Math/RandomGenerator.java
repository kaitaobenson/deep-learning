package Java.Util.Math;

public class RandomGenerator {

    public static float randomFloat(float min, float max) {
        return (float) (min + (max - min) * Math.random());
    }

    public static int randomInt(int min, int max) {
        return (int) (min + (max - min) * Math.random());
    }
}

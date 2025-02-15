package util;

public class StatUtil {

    public float getSquaredError(float[] targets, float[] outputs) {
        assert(targets.length == outputs.length);

        float sum = 0.0f;
        for (int i = 0; i < targets.length; i++) {
            sum += 0.5 * Math.pow(targets[i] - outputs[i], 2);
        }

        return sum;
    }


}
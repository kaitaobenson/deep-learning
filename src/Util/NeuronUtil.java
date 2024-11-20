package Util;

import Digit.Digit;

public class NeuronUtil {

    // Returns an array of the target values based on target digit
    public static float[] getTargets(Digit digit) {
        // Label between 0 - 9
        float[] targets = new float[10];
        targets[digit.getLabel()] = 1.0f;

        return targets;
    }

    // Calculates the mean squared error (MSE) loss between outputs and targets
    public static float getMse(float[] outputs, float[] targets) {
        if (outputs.length != targets.length) {
            throw new IllegalArgumentException("Outputs and targets must be the same length.");
        }

        float sumSquaredDifference = 0.0f;
        for (int i = 0; i < outputs.length; i++) {
            sumSquaredDifference += (float) Math.pow(outputs[i] - targets[i], 2);
        }

        // Return the mean squared difference
        return sumSquaredDifference / targets.length;
    }

    // Calculates the derivative of the mean squared error (MSE)
    public static float[] getMseDerivative(float[] outputs, float[] targets) {
        float[] errorDerivatives = new float[targets.length];
        for (int i = 0; i < targets.length; i++){
            errorDerivatives[i] = 2.0f * (outputs[i] - targets[i]) / targets.length;
        }
        return errorDerivatives;
    }
}

package Neuron;

import Digit.Digit;
import Util.NeuronUtil;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class OutputData {

    // Output values
    private final float[] outputs;

    // Sorted outputs
    private final Map<Integer, Float> sortedOutputs;

    // Best guess [0-9]
    private final int bestGuess;

    // Digit tested on
    private final Digit digit;

    // Target values
    private float[] targets;

    // Mean squared error
    private float mse;


    public OutputData(float[] outputs, Digit digit) {
        this.outputs = outputs;
        this.sortedOutputs = sortOutputs(outputs);
        this.digit = digit;

        targets = NeuronUtil.getTargets(this.digit);
        mse = NeuronUtil.getMse(this.outputs, this.targets);
        bestGuess = sortedOutputs.keySet().iterator().next();
    }

    // Sorts the outputs into a map with their confidence values
    private Map<Integer, Float> sortOutputs(float[] outputs) {
        Map<Integer, Float> outputMap = new HashMap<>();
        for (int i = 0; i < outputs.length; i++) {
            outputMap.put(i, outputs[i]);
        }

        // Sort by confidence values
        return outputMap.entrySet()
                .stream()
                .sorted(Map.Entry.<Integer, Float>comparingByValue().reversed())
                .collect(LinkedHashMap::new,
                        (m, e) -> m.put(e.getKey(), e.getValue()),
                        LinkedHashMap::putAll);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("Output:");
        sb.append("\n");

        // Assuming sortedOutputs is a Map
        sortedOutputs.forEach((key, confidence) -> {
            sb.append(key);
            sb.append(" - Confidence: ");
            sb.append(confidence);
            sb.append("\n");
        });

        sb.append("Best Guess:");
        sb.append("\n");
        sb.append(bestGuess);

        return sb.toString();
    }

    // Getters
    public float[] getOutputs() {
        return outputs;
    }

    public Digit getDigit() {
        return digit;
    }

    public float[] getTargets() {
        return targets;
    }

    public float getMse() {
        return mse;
    }

    public int getBestGuess() {
        return bestGuess;
    }
}

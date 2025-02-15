package network.output;

import data.DataSample;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class OutputSingleData {

    public final float[] outputs;
    public final Map<Integer, Float> sortedOutputs;

    public final int bestGuess;
    public boolean isCorrect;

    public final DataSample dataSample;

    private float error;


    public OutputSingleData(float[] outputs, DataSample dataSample) {
        this.outputs = outputs;
        this.sortedOutputs = sortOutputs(outputs);

        this.bestGuess = sortedOutputs.keySet().iterator().next();

        int correctNum = 0;
        for (int i = 0; i < dataSample.getTargets().length; i++) {
            if (dataSample.getTargets()[i] == 1) {
                correctNum = i;
            }
        }

        isCorrect = bestGuess == correctNum;

        this.dataSample = dataSample;

        this.error = 0; //TODO: fix this
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
}

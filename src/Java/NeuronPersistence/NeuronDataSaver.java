package Java.NeuronPersistence;

import Java.Neuron.Neuron;
import Java.Neuron.NeuronLayer;
import Java.Neuron.NeuronModel;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NeuronDataSaver {

    private static final String modelDataSavePath = "../saved-model-data";
    private static final String modelDataFile = "model-data-x.csv";

    public void saveNeuronModel(NeuronModel neuronModel) throws IOException {
        File modelDataDirectory = new File(modelDataSavePath);

        // Ensure the directory exists or create it
        if (!modelDataDirectory.exists()) {
            if (!modelDataDirectory.mkdir()) {
                throw new IOException("Could not create model-data directory");
            }
        }

        System.out.println("model-data directory valid");

        File[] modelDataFiles = modelDataDirectory.listFiles();
        int largestId = 0;

        if (modelDataFiles != null) {

            for (File file : modelDataFiles) {
                String name = file.getName();
                if (isValidModelDataFileName(name)) {
                    String stringId = name.substring(11, name.length() - 4);
                    int intId = Integer.parseInt(stringId);

                    // Update largestId if bigger one found
                    if (intId > largestId) {
                        largestId = intId;
                    }
                }
            }
        }

        // Create the new file
        String saveFileName = modelDataFile.replaceFirst("x", Integer.toString(largestId + 1));
        File saveFile = new File(modelDataSavePath + "/" + saveFileName);
        System.out.println("Saving model to: " + saveFile.getPath());

        // Extract the data into correct format
        System.out.println("Building file...");
        StringBuilder sb = new StringBuilder();

        // Neuron layers
        NeuronLayer[] neuronLayers = neuronModel.getNeuronLayers();
        for (NeuronLayer neuronLayer : neuronLayers) {
            sb.append("\n");
            sb.append("Layer:");
            sb.append("\n");

            // Neurons
            Neuron[] neurons = neuronLayer.getNeurons();
            for (Neuron neuron : neurons) {
                sb.append("\n");
                sb.append("Neuron:");
                sb.append("\n");

                // Weights
                float[] weights = neuron.getWeights();
                for (float weight : weights) {
                    sb.append(weight);
                    sb.append(", ");
                }

                // Bias
                sb.append("\n");
                sb.append(neuron.getBias());
            }
        }

        // Write data to file
        try (FileWriter fileWriter = new FileWriter(saveFile)) {
            fileWriter.write(sb.toString());
        }
        System.out.println("Finished writing file");
    }

    public boolean isValidModelDataFileName(String name) {
        // Files in the format 'model-data-123.csv'
        Pattern pattern = Pattern.compile("^model-data-\\d+\\.csv$");
        Matcher matcher = pattern.matcher(name);
        return matcher.matches();
    }
}

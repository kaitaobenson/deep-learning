package Java.NeuronPersistence;

import Java.Neuron.NeuronModel;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.regex.Pattern;

public class NeuronDataSaver {

    private static final String modelDataSavePath = "../model-data";

    public void saveNeuronModel(NeuronModel neuronModel) throws IOException {
        File modelDataDirectory = new File(modelDataSavePath);
        if (!modelDataDirectory.exists()) {
            if (!modelDataDirectory.mkdir()) {
                throw new IOException("Could not make model-data directory");
            }
        }
        System.out.println("model-data directory valid");

        File[] modelDataFiles = modelDataDirectory.listFiles();

        for (File file : modelDataFiles) {
            String name = file.getName();
            //if (name.matches())
        }



    }
    public boolean isValidModelDataFileName(String name) {
        return false;
    }

}

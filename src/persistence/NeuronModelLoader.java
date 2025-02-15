package persistence;

import model.NeuronModel;
import java.io.*;
import java.nio.file.FileAlreadyExistsException;

public class NeuronLoader {

    private static final String modelDataSavePath = "../saved-model-data/";

    public void saveNeuronModel(NeuronModel model, String fileName) throws IOException {
        String filePath = modelDataSavePath + fileName;
        File file = new File(filePath);

        if (file.exists()) {
            throw new FileAlreadyExistsException("There is already a model file with that name.");
        }

        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filePath))) {
            System.out.println("Finished saving model.");
            oos.writeObject(model);
        }
    }

    public NeuronModel loadNeuronModel(String fileName) throws IOException, ClassNotFoundException{
        String filePath = modelDataSavePath + fileName;
        File file = new File(filePath);

        if (!file.exists()) {
            throw new FileNotFoundException("No model file with that name.");
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filePath))) {
            System.out.println("Finished loading model.");
            return (NeuronModel) ois.readObject();
        }
    }
}

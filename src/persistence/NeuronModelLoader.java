package persistence;

import network.NeuronModel;
import java.io.*;
import java.nio.file.FileAlreadyExistsException;


public class NeuronModelLoader {

    // TODO: you can replace this with standardized external configuration
    private static final String MODEL_DATA_SAVE_FOLDER = "../saved-model-data/";


    public void save(NeuronModel model, String fileName) throws IOException {
        String filePath = MODEL_DATA_SAVE_FOLDER + fileName;
        File file = new File(filePath);

        if (file.exists()) {
            throw new FileAlreadyExistsException("There is already a model file with that name.");
        }

        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filePath))) {
            System.out.println("Finished saving model.");
            oos.writeObject(model);
        }
    }

    public NeuronModel load(String fileName) throws IOException, ClassNotFoundException{
        String filePath = MODEL_DATA_SAVE_FOLDER + fileName;
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

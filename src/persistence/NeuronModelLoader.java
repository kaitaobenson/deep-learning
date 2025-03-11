package persistence;

import network.NeuronModel;
import java.io.*;
import java.nio.file.FileAlreadyExistsException;

public class NeuronModelLoader {

    private static final String MODEL_DATA_SAVE_FOLDER = "src/persistence/saved_models/";

    // Save NeuronModel to a file
    public void save(NeuronModel model, String fileName) throws IOException {
        String filePath = MODEL_DATA_SAVE_FOLDER + fileName + ".ser";
        File file = new File(filePath);

        System.out.println("Saving model to: " + file.getAbsolutePath());

        if (file.exists()) {
            throw new FileAlreadyExistsException("ERROR: There is already a file with that name.");
        }

        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filePath))) {
            System.out.println("Successfully saved model.");
            oos.writeObject(model);

        } catch (Exception e) {
            System.out.println("ERROR: Failed to write model.");
        }
    }

    // Load NeuronModel from a file
    public NeuronModel load(String fileName) throws IOException, ClassNotFoundException {
        String filePath = MODEL_DATA_SAVE_FOLDER + fileName + ".ser";
        File file = new File(filePath);

        System.out.println("Loading model from: " + file.getAbsolutePath());

        if (!file.exists()) {
            throw new FileNotFoundException("ERROR: No model file with that name.");
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filePath))) {
            System.out.println("Successfully loading model.");
            NeuronModel nm = (NeuronModel) ois.readObject();
            return nm;
        } catch (Exception e) {
            System.out.println("ERROR: Failed to load model. " + e.getMessage());
            return null;
        }
    }
}

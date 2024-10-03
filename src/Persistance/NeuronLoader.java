package Persistance;

import Neuron.NeuronModel;

import java.io.*;

public class NeuronLoader {

    private static final String modelDataSavePath = "../saved-model-data/";

    public void saveNeuronModel(NeuronModel model, String fileName) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(modelDataSavePath + fileName))) {
            oos.writeObject(model);
        }
    }

    public NeuronModel loadNeuronModel(String fileName) throws IOException, ClassNotFoundException{
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(modelDataSavePath + fileName))) {
            return (NeuronModel) ois.readObject();
        }
    }
}

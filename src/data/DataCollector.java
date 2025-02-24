package data;

import activation.IActivationFunction;
import activation.LeakyReLu;
import network.NeuronLayer;
import network.NeuronModel;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;

public class DataCollector {
    public String name;
    public int testSize;

    public DataCollector(String name, int testSize) {
        this.name = name;
        this.testSize = testSize;
    }

    public void DataCSVWriter(String fileName, float[][] data) throws IOException {
        try (PrintWriter writer = new PrintWriter(new FileWriter(fileName))){
            // Write CSV x,y header
            writer.println("x,y");

            // Write data pairs to CSV
            for (float[] row : data){
                writer.println(row[0] + "," + row[1]);
            }

            System.out.println("Created CSV file" + fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

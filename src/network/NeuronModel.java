package network;

import data.DataSample;
import data.DataSet;
import network.output.OutputAllData;
import network.output.OutputSingleData;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

public class NeuronModel implements Serializable {

    public static float learningRate = 0.01f;

    private final NeuronLayer[] layers;


    public NeuronModel(NeuronLayer[] neuronLayers) {
        this.layers = neuronLayers;
    }

    public void train(DataSet dataSet, int epochs) {
        for (int epoch = 0; epoch < epochs; epoch++) {
            for (DataSample dataSample : dataSet.data) {
                forward(dataSample.getInputs());
                backward(learningRate, dataSample);

                // Apply weight updates
                for (NeuronLayer layer : layers) {
                    for (Neuron neuron : layer.neurons) {
                        neuron.updateWeights(learningRate, 1);
                    }
                }
            }
            System.out.println("Epoch " + (epoch + 1) + " complete");
            System.out.println(Arrays.toString(layers[1].neurons[0].weights));
        }
    }

    public void trainMiniBatch(DataSet dataSet, int epochs, int batchSize){
        for (int epoch = 0; epoch < epochs; epoch++) {
            DataSet[] miniBatches = generateMiniBatches(dataSet, batchSize);
            for (DataSet batch : miniBatches) {
                for (DataSample dataSample : batch.data) {
                    forward(dataSample.getInputs());
                    backward(learningRate, dataSample);
                }

                // Apply weight updates
                for (NeuronLayer layer : layers) {
                    for (Neuron neuron : layer.neurons) {
                        neuron.updateWeights(learningRate, batch.getSampleAmount());
                    }
                }
            }

            System.out.println("Epoch " + (epoch + 1) + " complete");
            System.out.println(Arrays.toString(layers[1].neurons[0].weights));
        }
    }

    public OutputSingleData testSingle(DataSample dataSample) {
        forward(dataSample.getInputs());
        float[] outputs = extractOutputs();
        OutputSingleData output = new OutputSingleData(outputs, dataSample);
        return output;
    }

    public OutputAllData testAll(DataSet dataSet) {
        int totalCorrect = 0;
        for (DataSample dataSample : dataSet.data) {
            OutputSingleData outputData = testSingle(dataSample);
            if (outputData.isCorrect) {
                totalCorrect++;
            }
        }

        OutputAllData output = new OutputAllData(dataSet.getSampleAmount(), totalCorrect);
        return output;
    }

    public void randomize() {

    }


    private void forward(float[] inputs) {
        // Put inputs in first layer
        layers[0] = NeuronLayer.createInputLayer(inputs);

        // Loop layers
        for (int i = 1; i < layers.length; i++) {

            NeuronLayer prevLayer = layers[i - 1];
            NeuronLayer currentLayer = layers[i];

            // Loop neurons in layer
            for (Neuron neuron : currentLayer.neurons) {
                float sum = neuron.bias;

                // Loop neurons in previous layer
                for (int j = 0; j < prevLayer.neurons.length; j++) {
                    sum += prevLayer.neurons[j].value * neuron.weights[j];
                }

                neuron.value = currentLayer.activationFunction.output(sum);
            }
        }
    }


    private void backward(float learningRate, DataSample dataSample) {
        int lastLayer = layers.length - 1;

        // Update output layer gradients
        for (int i = 0; i < layers[lastLayer].neurons.length; i++) {
            Neuron neuron = layers[lastLayer].neurons[i];
            float output = neuron.value;
            float target = dataSample.getTargets()[i];

            float delta = (output - target) * layers[lastLayer].activationFunction.outputDerivative(output);

            neuron.gradient = delta;

            for (int j = 0; j < neuron.weights.length; j++) {
                neuron.weightDeltas[j] -= learningRate * delta * layers[lastLayer - 1].neurons[j].value;
            }
            neuron.biasDelta -= learningRate * delta;
        }


        // Update hidden layers
        for (int l = lastLayer - 1; l > 0; l--) { // Start from last hidden layer (not input layer)
            for (int j = 0; j < layers[l].neurons.length; j++) {
                Neuron neuron = layers[l].neurons[j];

                if (neuron.weights == null) continue; // Skip input neurons

                float output = neuron.value;
                float gradientSum = sumGradient(j, l + 1);

                float delta = gradientSum * layers[l].activationFunction.outputDerivative(output);
                float maxDelta = 10f;
                delta = Math.max(-maxDelta, Math.min(maxDelta, delta));

                //System.out.println("GRADIENT: " + delta);

                neuron.gradient = delta;

                for (int k = 0; k < neuron.weights.length; k++) {
                    neuron.weightDeltas[k] -= learningRate * delta * layers[l - 1].neurons[k].value;
                }
                neuron.biasDelta -= learningRate * delta;
            }
        }
    }

    private float[] extractOutputs() {
        NeuronLayer lastLayer = layers[layers.length - 1];

        float[] outputs = new float[lastLayer.neurons.length];

        for (int i = 0; i < lastLayer.neurons.length; i++) {
            outputs[i] = lastLayer.neurons[i].value;
        }
        return outputs;
    }

    private float sumGradient(int n_index, int l_index) {
        float gradient_sum = 0;
        NeuronLayer current_layer = layers[l_index];

        for (Neuron neuron : current_layer.neurons) {
            if (neuron.weights == null) {
                System.out.println("WEIGHTS WERE NULL");
                continue;
            }// Skip if weights are not initialized
            gradient_sum += neuron.weights[n_index] * neuron.gradient;
        }
        return gradient_sum;
    }

    private DataSet[] generateMiniBatches(DataSet dataset, int batchSize){
        if (dataset.getSampleAmount() % batchSize != 0){
            throw new IllegalArgumentException("Error: The batch size does not evenly divide the dataset size. Please choose a batch size that is a factor of the total data amount.");
        }

        DataSet[] miniBatches = new DataSet[Math.ceilDiv(dataset.getSampleAmount(),batchSize)];
        for (int i = 0; i < miniBatches.length; i++) {
            miniBatches[i] = new DataSet(batchSize);
        }

        List<DataSample> data = dataset.data;
        for (int i = 0; i < data.size(); i++){
            DataSet batch = miniBatches[Math.floorDiv(i, batchSize)];
            batch.data.add(i % batchSize, data.get(i));
        }

        return miniBatches;
    }
}
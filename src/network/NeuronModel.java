package network;

import data.DataSample;
import data.DataSet;
import network.output.OutputAllData;
import network.output.OutputSingleData;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class NeuronModel implements Serializable {

    public final float learningRate;
    public final boolean miniBatch;
    public final int batchSize;

    public final NeuronLayer[] layers;


    public NeuronModel(NeuronLayer[] neuronLayers, boolean miniBatch, int batchSize, float learningRate) {
        this.layers = neuronLayers;
        this.learningRate = learningRate;
        this.miniBatch = miniBatch;
        this.batchSize = batchSize;
    }

    public void trainModel(DataSet dataSet, int epochs){
        if (!miniBatch) {
            train(dataSet, epochs);
        }
        else{
            trainMiniBatch(dataSet, epochs, batchSize);
        }
    }

    public void train (DataSet dataSet, int epochs) {
        for (int epoch = 0; epoch < epochs; epoch++) {

            float loss = 0.0f;

            for (DataSample dataSample : dataSet.data) {
                forward(dataSample.getInputs());
                backward(learningRate, dataSample);

                loss += computeLoss(dataSample);

                // Apply weight updates
                for (NeuronLayer layer : layers) {
                    for (Neuron neuron : layer.neurons) {
                        neuron.updateWeights(1);
                    }
                }
            }

            System.out.println("EPOCH: " + (epoch + 1) + " complete");
            System.out.println("AVERAGE LOSS: " + loss / dataSet.getSize());
        }
    }

    public void trainMiniBatch(DataSet dataSet, int epochs, int batchSize){
        for (int epoch = 0; epoch < epochs; epoch++) {
            DataSet[] miniBatches = generateMiniBatches(dataSet, batchSize);

            float loss = 0.0f;

            for (DataSet batch : miniBatches) {
                for (DataSample dataSample : batch.data) {
                    forward(dataSample.getInputs());
                    backward(learningRate, dataSample);

                    loss += computeLoss(dataSample);
                }

                // Apply weight updates
                for (NeuronLayer layer : layers) {
                    for (Neuron neuron : layer.neurons) {
                        neuron.updateWeights(batch.getSize());
                    }
                }
            }

            System.out.println("EPOCH: " + (epoch + 1) + " complete");
            System.out.println("AVERAGE LOSS: " + loss / dataSet.getSize());
        }
    }

    public OutputSingleData testSingle(DataSample dataSample) {
        forward(dataSample.getInputs());
        float[] outputs = extractOutputs();
        return new OutputSingleData(outputs, dataSample);
    }

    public OutputAllData testAll(DataSet dataSet) {
        int totalCorrect = 0;
        ArrayList<Integer> incorrectIndexes = new ArrayList<>(1000);

        for (int i = 0; i < dataSet.getSize(); i++) {
            DataSample sample = dataSet.getSample(i);
            OutputSingleData outputData = testSingle(sample);

            if (outputData.isCorrect) {
                totalCorrect++;
            } else {
                incorrectIndexes.add(i);
            }
        }

        int[] incorrectIndexesArray = incorrectIndexes.stream().mapToInt(Integer::intValue).toArray();
        return new OutputAllData(dataSet.getSize(), totalCorrect, incorrectIndexesArray);
    }

    public void randomize() {
        for (NeuronLayer neuronLayer : layers) {
            neuronLayer.randomize();
        }
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
            gradient_sum += neuron.weights[n_index] * neuron.gradient;
        }
        return gradient_sum;
    }

    private float computeLoss(DataSample dataSample) {
        float[] outputs = extractOutputs();
        float[] targets = dataSample.getTargets();
        float loss = 0;

        // Mean Squared Error (MSE)
        for (int i = 0; i < outputs.length; i++) {
            float error = outputs[i] - targets[i];
            loss += error * error;
        }

        return loss / outputs.length; // Average loss per output neuron
    }


    private DataSet[] generateMiniBatches(DataSet dataset, int batchSize) {
        if (dataset.getSize() % batchSize != 0) {
            System.out.println("ERROR: The batch size does not evenly divide into the dataset size.");
            return null;
        }

        DataSet[] miniBatches = new DataSet[Math.ceilDiv(dataset.getSize(),batchSize)];
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
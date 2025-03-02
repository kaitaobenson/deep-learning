package activation;


import java.io.Serializable;

public class Sigmoid implements IActivationFunction, Serializable {

    @Override
    public float output(float x) {
        return (float) (1 / (1 + Math.exp(-x)));
    }

    @Override
    public float outputDerivative(float x) {
        float y = output(x);
        return y * (1 - y);
    }
}
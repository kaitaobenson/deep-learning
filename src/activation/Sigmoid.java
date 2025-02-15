package activation;


public class Sigmoid implements IActivationFunction {

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
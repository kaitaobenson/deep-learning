package activation;

public class Tanh implements IActivationFunction {

    @Override
    public float output(float x) {
        return (float) Math.tanh(x);
    }

    @Override
    public float outputDerivative(float x) {
        return (float) (1 - Math.pow(Math.tanh(x), 2));
    }
}

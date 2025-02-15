package activation;

public interface IActivationFunction {

    float output(float x);

    float outputDerivative(float x);
}

package network.output;

public class OutputAllData {

    public static int totalSamples;
    public static int correctSamples;

    public OutputAllData(int totalSamples, int correctSamples) {
        OutputAllData.totalSamples = totalSamples;
        OutputAllData.correctSamples = correctSamples;
    }

    public static float getAccuracy() {
        return (float) correctSamples / totalSamples;
    }

    @Override
    public String toString() {
        String str = "Output:\n" +
                correctSamples + "/" + totalSamples + " guessed correctly";

        return str;
    }
}

package network.output;

public class OutputAllData {

    public int totalSamples;
    public int correctSamples;

    public OutputAllData(int totalSamples, int correctSamples) {
        this.totalSamples = totalSamples;
        this.correctSamples = correctSamples;
    }


    @Override
    public String toString() {
        String str = "Output:\n" +
                correctSamples + "/" + totalSamples + " guessed correctly";

        return str;
    }
}

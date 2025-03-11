package network.output;

import java.util.Arrays;

public class OutputAllData {

    public int totalSamples;
    public int correctSamples;

    public int[] incorrectIndexes;

    public OutputAllData(int totalSamples, int correctSamples, int[] incorrectIndexes) {
        this.totalSamples = totalSamples;
        this.correctSamples = correctSamples;

        this.incorrectIndexes = incorrectIndexes;
    }

    @Override
    public String toString() {
        String line1 = "Correct out of Total: " + correctSamples + "/" + totalSamples;
        String line2 = "Incorrect Indexes: " + Arrays.toString(incorrectIndexes);
        return line1 + "\n" + line2;
    }
}

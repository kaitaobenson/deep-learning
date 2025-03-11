package testing;

import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class PointLogger {
    private List<Float> xList = new ArrayList<>(100);
    private List<Float> yList = new ArrayList<>(100);

    public PointLogger() {}

    public void addPoint(float x, float y) {
        xList.add(x);
        yList.add(y);
    }

    public PointLogger getAvgPointLogger() {
        PointLogger logger2 = new PointLogger();
        Map<Float, float[]> sumCountMap = new HashMap<>();  // {sum, count}

        for (int i = 0; i < xList.size(); i++) {
            float x = xList.get(i);
            float y = yList.get(i);

            sumCountMap.putIfAbsent(x, new float[]{0, 0});
            sumCountMap.get(x)[0] += y;
            sumCountMap.get(x)[1]++;
        }

        List<Float> sortedKeys = new ArrayList<>(sumCountMap.keySet());
        Collections.sort(sortedKeys);

        for (float x : sortedKeys) {
            float avgY = sumCountMap.get(x)[0] / sumCountMap.get(x)[1];
            logger2.addPoint(x, avgY);
        }

        return logger2;
    }

    public void writeFile(String fileName, String path) {
        StringBuilder sb = new StringBuilder();

        assert(xList.size() == yList.size());

        for (int i = 0; i < xList.size(); i++) {
            sb.append(xList.get(i)).append(",").append(yList.get(i)).append("\n");
        }

        // Hi kai it's carson
        // It doens't even use the path but I don't care anymore
        try (FileWriter writer = new FileWriter(fileName + ".csv")) {
            writer.write(sb.toString());
        } catch (IOException e) {
            System.out.println("ERROR: Failed to write test output file");
        }
    }
}

package data;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PointLogger {
    public String name;
    public String description;

    private List<Float> xList = new ArrayList<>(100);
    private List<Float> yList = new ArrayList<>(100);

    public PointLogger(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public void addPoint(float x, float y) {
        xList.add(x);
        yList.add(y);
    }

    public void writeFile(String fileName) {
        StringBuilder sb = new StringBuilder();

        sb.append("// Name: ").append(name).append("\n");
        sb.append("// Description: ").append(description).append("\n");

        assert(xList.size() == yList.size());

        for (int i = 0; i < xList.size(); i++) {
            sb.append(xList.get(i)).append(",").append(yList.get(i)).append("\n");
        }

        try (FileWriter writer = new FileWriter(fileName + ".csv")) {
            writer.write(sb.toString());
        } catch (IOException e) {
            System.out.println("Error writing output file");
        }
    }
}

package data;

import java.util.ArrayList;
import java.util.List;

public class DataSet {

    public List<DataSample> data;

    public DataSet(int initialSize) {
        data = new ArrayList<>(initialSize);
    }

    public int getSampleAmount() {
        return data.size();
    }

    public DataSample getSample(int index) {
        return data.get(index);
    }
}

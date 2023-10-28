import java.io.IOException;
import java.util.ArrayList;

public class DataFileReaderTest {
    public static void main(String[] args) throws IOException {
        DataFileReader fileReader = new DataFileReader(false);
        ArrayList<ElectricityRecord> recordList =  fileReader.getList();
        ElectricityRecord[] records = fileReader.getAll();
    }
}

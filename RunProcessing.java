import java.io.IOException;
import java.util.ArrayList;

public class RunProcessing {

    public static void main(String[] args) throws IOException {
        DataProcessor processor = new DataProcessor();
        String country = "France";
        String product = "natural gas";
        ArrayList<ElectricityRecord> record1 = processor.selectByCountry(country);
        ArrayList<ElectricityRecord> records2 = processor.selectByProduct(product);
        ArrayList<ElectricityRecord> records = processor.selectByCountryAndProduct(country, product);
    }
}

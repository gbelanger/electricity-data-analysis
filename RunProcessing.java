import java.io.IOException;

public class RunProcessing {

    public static void main(String[] args) throws IOException {
        DataProcessor processor = new DataProcessor();
        String country = "France";
        String product = "natural gas";
        processor.plotProductPerCountry(country, product);
    }
}

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public final class DataProcessor {

    private DataFileReader fileReader;
    private HashSet<String> countrySet;
    private HashSet<String> monthSet;
    private HashSet<String> balanceSet;
    private HashSet<String> productSet;
    private ArrayList<ElectricityRecord> records;


    public DataProcessor() throws IOException {
        fileReader = new DataFileReader();
        countrySet = fileReader.getCountrySet();
        monthSet = fileReader.getMonthSet();
        balanceSet = fileReader.getBalanceSet();
        productSet = fileReader.getProductSet();
        records = fileReader.getList();
    }

    public ArrayList<ElectricityRecord> selectByProduct(String product) {
        System.out.print("Selecting by product '" + product + "' ... ");
        ArrayList<ElectricityRecord> selected = new ArrayList<>();
        for (ElectricityRecord record : this.records) {
            if (record.getProduct().equals(product)) {
                selected.add(record);
            }
        }
        selected.trimToSize();
        System.out.println("done");
        return selected;
    }

    public ArrayList<ElectricityRecord> selectByCountry(String country) {
        System.out.print("Selecting by country '" + country + "' ... ");
        ArrayList<ElectricityRecord> selected = new ArrayList<>();
        for (ElectricityRecord record : this.records) {
            if (record.getCountry().equals(country)) {
                selected.add(record);
            }
        }
        selected.trimToSize();
        System.out.println("done");
        return selected;
    }

    public ArrayList<ElectricityRecord> selectByCountryAndProduct(String country, String product)  {
        System.out.println("Selecting by country and product '" + country + "' and '" + product + "' ... ");
        ArrayList<ElectricityRecord> selectedByCountry = this.selectByCountry(country);
        ArrayList<ElectricityRecord> selected = new ArrayList<>();
        for (ElectricityRecord record : selectedByCountry) {
            if (record.getProduct().equals(product)) {
                selected.add(record);
            }
        }
        System.out.println("done");
        return selected;
    }

}


//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;

import java.util.HashSet;
import java.util.logging.Logger;
import java.io.IOException;
import java.util.ArrayList;
import java.util.NoSuchElementException;

public final class DataFileReader extends AbstractDataFileReader {

    public static String DATA_FILENAME = "data/monthly-electricity-statistics.csv";

    private static final Logger logger = Logger.getLogger(DataFileReader.class.getName());

    private ArrayList<String> countryList;
    private ArrayList<String> monthList;
    private ArrayList<String> balanceList;
    private ArrayList<String> productList;
    private ArrayList<String> gigaWattHourList;
    private ArrayList<Double> gwhList;

    private HashSet<String> countrySet;
    private HashSet<String> monthSet;
    private HashSet<String> balanceSet;
    private HashSet<String> productSet;


    private ArrayList<ElectricityRecord> recordList;


    // Constructors

    public DataFileReader() throws IOException, IllegalArgumentException, NoSuchElementException {
        super(DATA_FILENAME);
        processFile();
    }

    public DataFileReader(boolean debug) throws IOException, IllegalArgumentException, NoSuchElementException {
        super(DATA_FILENAME);
        processFile(debug);
    }

    public DataFileReader(String csvFilename) throws IOException, IllegalArgumentException, NoSuchElementException {
        super(csvFilename);
        processFile();
    }

    public DataFileReader(String csvFilename, boolean debug) throws IOException, IllegalArgumentException, NoSuchElementException {
        super(csvFilename);
        processFile(debug);
    }

    @Override
    protected void processFile() {
        processFile(false);
    }

    @Override
    protected void processFile(boolean debug) throws IllegalArgumentException, NoSuchElementException {

        // Initialise class lists
        countryList = new ArrayList<>();
        monthList = new ArrayList<>();
        balanceList = new ArrayList<>();
        productList = new ArrayList<>();
        gigaWattHourList = new ArrayList<>();
        gwhList = new ArrayList<>();
        recordList = new ArrayList<>();

        // Skip the column titles
        int lineNumber = 2;

        System.out.println("Reading records. (This can take a while)");

        for (int lineIdx = 1; lineIdx < lines.size(); lineIdx++) {

            String[] dataline = lines.get(lineIdx);
            if (debug) logger.info("Reading line number " + lineNumber);

            int l = 0;

            String country = dataline[l++].trim();
            if (country.isBlank() || country.isEmpty()) {
                logger.severe("Missing country on line " + lineNumber + ".");
                throw new IllegalArgumentException("country is a required field.");
            }
            if (debug) {
                logger.info("country = " + country);
            }

            String month = dataline[l++].toLowerCase().trim();
            if (month.isBlank() || month.isEmpty()) {
                logger.severe("Missing month on line " + lineNumber + ".");
                throw new IllegalArgumentException("month is a required field.");
            }
            if (debug) {
                logger.info("month = " + month);
            }

            String balance = dataline[l++].toLowerCase().trim();
            if (balance.isBlank() || balance.isEmpty()) {
                logger.warning("Missing balance on line " + lineNumber + ".");
                throw new IllegalArgumentException("balance is a required field.");
            }
            if (debug) {
                logger.info("balance = " + balance);
            }

            String product = dataline[l++].toLowerCase().trim();
            if (product.isBlank() || product.isEmpty()) {
                logger.warning("Missing product on line " + lineNumber + ".");
                throw new IllegalArgumentException("product is a required field.");
            }
            if (debug) {
                logger.info("product = " + product);
            }

            String gwhString = dataline[l++].toLowerCase().trim();
            double gwh = 0;
            if (gwhString.isBlank() || gwhString.isEmpty()) {
//                logger.warning("Missing gwh on line " + lineNumber + ". Product: "+product);
            }
            else {
                try {
                    gwh = Double.parseDouble(gwhString);
                } catch (NumberFormatException e) {
                    logger.warning("GWh must be a numerical value.");
                }
                if (debug) {
                    logger.info("gwh = " + gwh);
                }
            }


            // Add entries to lists if all elements are non-null
            if (!country.isBlank() && !month.isBlank() && !balance.isBlank() && !product.isBlank() && !gwhString.isBlank()) {
                countryList.add(country);
                monthList.add(month);
                balanceList.add(balance);
                productList.add(product);
                gigaWattHourList.add(gwhString);
                gwhList.add(gwh);
                ElectricityRecord record = new ElectricityRecord(country, month, balance, product, gwh);
                recordList.add(record);
//                record.print();
            }

            // Go to next line
            lineNumber++;
            if (debug) System.out.println();

            if (lineNumber % 10000 == 0) {
                System.out.println("... " + lineNumber);
            }
        }
        System.out.println("... " + lineNumber);
        System.out.println("Done!");

        countryList.trimToSize();
        monthList.trimToSize();
        balanceList.trimToSize();
        productList.trimToSize();
        gigaWattHourList.trimToSize();
        gwhList.trimToSize();
        recordList.trimToSize();
        
        // Create unique sets from lists
        countrySet = new HashSet<>(countryList);
        monthSet = new HashSet<>(monthList);
        balanceSet = new HashSet<>(balanceList);
        productSet = new HashSet<>(productList);

    }

    @Override
    public ArrayList<ElectricityRecord> getList() {
        return recordList;
    }

    @Override
    public ElectricityRecord[] getAll() {
        ElectricityRecord[] records = new ElectricityRecord[recordList.size()];
        return recordList.toArray(records);
    }

    public HashSet<String> getCountrySet() {
        return countrySet;
    }

    public HashSet<String> getMonthSet() {
        return monthSet;
    }

    public HashSet<String> getBalanceSet() {
        return balanceSet;
    }

    public HashSet<String> getProductSet() {
        return productSet;
    }

    //    @Override
//    public IMarker get(String key) throws NoSuchElementException {
//        int idx = keyList.indexOf(key);
//        if (idx == -1) {
//            throw new NoSuchElementException("Marker key '" + key + "' not found in " + DATA_FILENAME);
//        }
//        return markersList.get(idx);
//    }
//
//    public IMarker getByName(String name) throws NoSuchElementException {
//        int idx = namesList.indexOf(name);
//        if (idx == -1) {
//            idx = shortNamesList.indexOf(name);
//            if (idx == -1) {
//                throw new NoSuchElementException("Marker name '" + name + "' not found in " + DATA_FILENAME);
//            }
//        }
//        return markersList.get(idx);
//    }

}

//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;

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
        this.countryList = new ArrayList<>();
        this.monthList = new ArrayList<>();
        this.balanceList = new ArrayList<>();
        this.productList = new ArrayList<>();
        this.gigaWattHourList = new ArrayList<>();
        this.gwhList = new ArrayList<>();
        this.recordList = new ArrayList<>();

        // Skip the column titles
        int lineNumber = 2;

        for (int lineIdx = 1; lineIdx < this.lines.size(); lineIdx++) {

            String[] dataline = this.lines.get(lineIdx);
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
                logger.warning("Missing gwh on line " + lineNumber + ". Product: "+product);
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
                this.countryList.add(country);
                this.monthList.add(month);
                this.balanceList.add(balance);
                this.productList.add(product);
                this.gigaWattHourList.add(gwhString);
                this.gwhList.add(gwh);
                ElectricityRecord record = new ElectricityRecord(country, month, balance, product, gwh);
                this.recordList.add(record);
                record.print();
            }

            // Go to next line
            lineNumber++;
            if (debug) System.out.println();
        }

        this.countryList.trimToSize();
        this.monthList.trimToSize();
        this.balanceList.trimToSize();
        this.productList.trimToSize();
        this.gigaWattHourList.trimToSize();
        this.gwhList.trimToSize();
        this.recordList.trimToSize();
    }

    @Override
    public ArrayList<ElectricityRecord> getList() {
        return this.recordList;
    }

    @Override
    public ElectricityRecord[] getAll() {
        ElectricityRecord[] records = new ElectricityRecord[recordList.size()];
        return recordList.toArray(records);
    }



//    @Override
//    public IMarker get(String key) throws NoSuchElementException {
//        int idx = this.keyList.indexOf(key);
//        if (idx == -1) {
//            throw new NoSuchElementException("Marker key '" + key + "' not found in " + DATA_FILENAME);
//        }
//        return this.markersList.get(idx);
//    }
//
//    public IMarker getByName(String name) throws NoSuchElementException {
//        int idx = this.namesList.indexOf(name);
//        if (idx == -1) {
//            idx = this.shortNamesList.indexOf(name);
//            if (idx == -1) {
//                throw new NoSuchElementException("Marker name '" + name + "' not found in " + DATA_FILENAME);
//            }
//        }
//        return this.markersList.get(idx);
//    }

}
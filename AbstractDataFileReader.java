
import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvException;
//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.logging.Logger;


abstract class AbstractDataFileReader {


    protected Logger logger = Logger.getLogger(AbstractDataFileReader.class.getName());
    protected String csvFilename;
    protected File csvFile;

    protected boolean debug = false;
    protected List<String[]> lines;


    public AbstractDataFileReader(String csvFilename) throws IOException {
        this.csvFile = new File(csvFilename);
        this.csvFilename = this.csvFile.getCanonicalPath();
        readFile();
    }

    public AbstractDataFileReader(File csvFile) throws IOException {
        this.csvFile = csvFile;
        this.csvFilename = this.csvFile.getCanonicalPath();
        readFile();
    }

    // Abstract private

    protected void processFile() throws IllegalArgumentException, NoSuchElementException {
        processFile(false);
    }

    protected abstract void processFile(boolean debug) throws IllegalArgumentException, NoSuchElementException;

    public abstract Object[] getAll();

    public abstract List getList();

    // Implemented private

    protected void readFile() throws IOException {
        logger.info("Reading CSV parameter file " + this.csvFilename);

        // Prepare file and csv readers
        Reader fileReader = Files.newBufferedReader(Paths.get(this.csvFilename));
        CSVParser csvParser = new CSVParserBuilder().withSeparator(',').build();
        CSVReader csvReader = new CSVReaderBuilder(fileReader)
                .withSkipLines(0)
                .withCSVParser(csvParser)
                .build();

        this.lines = new ArrayList<>();
        try {
            lines = csvReader.readAll();
        } catch (CsvException e) {
            logger.warning("Failed trying to csvReader.readAll() and cannot proceed:\n" + e.getMessage());
            System.exit(-1);
        }

        // Close file and csv readers
        fileReader.close();
        csvReader.close();
    }

}
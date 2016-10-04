package ao.csvutil;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class ColumnsSplitter {

    public static void split(String inputFileName, String outputFileName, List<Integer> columnsIndexes) throws IOException {
        CSVFormat format = CSVFormat.DEFAULT;
        try (BufferedReader reader = Util.getBufferedReader(inputFileName);
             PrintWriter writer = Util.getPrintWriter(outputFileName)) {
            Iterable<CSVRecord> records = format.parse(reader);
            List<List<String>> filterRecords = filterRecords(records, columnsIndexes);
            CSVPrinter csvPrinter = new CSVPrinter(writer, format);
            for (List<String> record : filterRecords) {
                csvPrinter.printRecord(record);
            }
        }
    }

    public static List<List<String>> filterRecords(Iterable<CSVRecord> records, List<Integer> columnsIndexes) {
        List<List<String>> filteredRecords = new ArrayList<>();
        for (CSVRecord record : records) {
            List<String> filteredRecord = new ArrayList<>();
            for (int index : columnsIndexes) {
                filteredRecord.add(record.get(index));
            }
            filteredRecords.add(filteredRecord);
        }
        return filteredRecords;
    }

}

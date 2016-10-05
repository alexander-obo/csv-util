package ao.csvutil;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

public class ColumnsSplitter {

    public static void split(String inputFileName, String outputFileName, List<Integer> columnsIndexes) throws IOException {
        CSVFormat format = CSVFormat.DEFAULT;
        try (BufferedReader reader = Util.getBufferedReader(inputFileName);
             PrintWriter writer = Util.getPrintWriter(outputFileName)) {
            List<CSVRecord> records = format.parse(reader).getRecords();
            List<List<String>> filterRecords = filterRecords(records, columnsIndexes);
            CSVPrinter csvPrinter = new CSVPrinter(writer, format);
            for (List<String> record : filterRecords) {
                csvPrinter.printRecord(record);
            }
        }
    }

    public static List<List<String>> filterRecords(List<CSVRecord> records, List<Integer> columnsIndexes) {
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

    public static List<List<String>> filterRecordsByColumnHeaders(List<CSVRecord> records, List<String> headers) {
        CSVRecord headerRecord = records.get(0);
        Map<String, Integer> mappedHeader = new HashMap<>();
        for (int i = 0; i < headerRecord.size(); i++) {
            mappedHeader.put(headerRecord.get(i), i);
        }
        List<Integer> columnsIndexes = new ArrayList<>();
        for (String header : headers) {
            columnsIndexes.add(mappedHeader.get(header));
        }
        return filterRecords(records, columnsIndexes);
    }

}

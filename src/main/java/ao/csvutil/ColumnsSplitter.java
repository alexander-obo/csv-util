package ao.csvutil;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

public class ColumnsSplitter {

    private static final CSVFormat FORMAT = CSVFormat.DEFAULT;

    public static void splitByColumnsIndexes(String inputFileName, String outputFileName, List<Integer> columnsIndexes) throws IOException {
        try (BufferedReader reader = IOFactory.getBufferedReader(inputFileName)) {
            List<CSVRecord> records = FORMAT.parse(reader).getRecords();
            List<List<String>> filterRecords = filterRecordsByColumnsIndexes(records, columnsIndexes);
            write(outputFileName, FORMAT, filterRecords);
        }
    }

    public static void splitByColumnsHeaders(String inputFileName, String outputFileName, List<String> headers) throws IOException {
        try (BufferedReader reader = IOFactory.getBufferedReader(inputFileName)) {
            List<CSVRecord> records = FORMAT.parse(reader).getRecords();
            List<List<String>> filterRecords = filterRecordsByColumnsHeaders(records, headers);
            write(outputFileName, FORMAT, filterRecords);
        }
    }

    public static List<List<String>> filterRecordsByColumnsIndexes(List<CSVRecord> records, List<Integer> columnsIndexes) {
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

    public static List<List<String>> filterRecordsByColumnsHeaders(List<CSVRecord> records, List<String> headers) {
        CSVRecord headerRecord = records.get(0);
        Map<String, Integer> mappedHeader = new HashMap<>();
        for (int i = 0; i < headerRecord.size(); i++) {
            mappedHeader.put(headerRecord.get(i), i);
        }
        List<Integer> columnsIndexes = new ArrayList<>();
        for (String header : headers) {
            Integer index = mappedHeader.get(header);
            if (index == null) {
//                throw new IllegalArgumentException("No '" + header + "' header in CSV file");
                System.err.println("No '" + header + "' header in CSV file");
            } else {
                columnsIndexes.add(index);
            }
        }
        return filterRecordsByColumnsIndexes(records, columnsIndexes);
    }

    private static void write(String outputFileName, CSVFormat format, List<List<String>> filterRecords) throws IOException {
        try (PrintWriter writer = IOFactory.getPrintWriter(outputFileName);
             CSVPrinter csvPrinter = new CSVPrinter(writer, format)) {
            for (List<String> record : filterRecords) {
                csvPrinter.printRecord(record);
            }
        }
    }
}

package ao.csvutil;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.junit.Assert;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class ColumnsSplitterTest {

    @Test
    public void testRecordsFiltration() {
        String input = getClass().getResource("/test_data.csv").getFile();
        List<Integer> indexes = Arrays.asList(1, 2);
        System.out.println(input);
        try {
            BufferedReader reader = Util.getBufferedReader(input);
            Iterable<CSVRecord> records = CSVFormat.DEFAULT.parse(reader);
            List<List<String>> filteredRecords = ColumnsSplitter.filterRecords(records, indexes);
            Assert.assertEquals(4, filteredRecords.size());

            List<String> record0 = filteredRecords.get(0);
            Assert.assertEquals(Arrays.asList("Last name", "Age"), record0);

            List<String> record1 = filteredRecords.get(1);
            Assert.assertEquals(Arrays.asList("Doug", "32"), record1);

            List<String> record2 = filteredRecords.get(2);
            Assert.assertEquals(Arrays.asList("Louren", "20"), record2);

            List<String> record3 = filteredRecords.get(3);
            Assert.assertEquals(Arrays.asList("Golden", "24"), record3);
        } catch (IOException ex) {
            Assert.fail(ex.getMessage());
        }
    }
}
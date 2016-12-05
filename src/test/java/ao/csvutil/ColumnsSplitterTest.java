package ao.csvutil;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.List;

public class ColumnsSplitterTest {

    private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();

    @Before
    public void setUp() {
        System.setErr(new PrintStream(errContent));
    }

    @After
    public void tearDown() {
        System.setErr(null);
    }

    @Test
    public void testRecordsFiltrationByColumnsIndexes() {
        String input = getClass().getResource("/test_data.csv").getFile();
        List<Integer> indexes = Arrays.asList(1, 2);
        try (BufferedReader reader = IOFactory.getBufferedReader(input)) {
            List<CSVRecord> records = CSVFormat.DEFAULT.parse(reader).getRecords();
            List<List<String>> filteredRecords = ColumnsSplitter.filterRecordsByColumnsIndexes(records, indexes);
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

    @Test
    public void testRecordsFiltrationByColumnsHeaders() {
        String input = getClass().getResource("/test_data.csv").getFile();
        List<String> headers = Arrays.asList("Phone", "Gender");
        try (BufferedReader reader = IOFactory.getBufferedReader(input)) {
            List<CSVRecord> records = CSVFormat.DEFAULT.parse(reader).getRecords();
            List<List<String>> filteredRecords = ColumnsSplitter.filterRecordsByColumnsHeaders(records, headers);
            Assert.assertEquals(4, filteredRecords.size());

            List<String> record0 = filteredRecords.get(0);
            Assert.assertEquals(Arrays.asList("Phone", "Gender"), record0);

            List<String> record1 = filteredRecords.get(1);
            Assert.assertEquals(Arrays.asList("123-45", "m"), record1);

            List<String> record2 = filteredRecords.get(2);
            Assert.assertEquals(Arrays.asList("789-00", "m"), record2);

            List<String> record3 = filteredRecords.get(3);
            Assert.assertEquals(Arrays.asList("562-34", "f"), record3);
        } catch (IOException ex) {
            Assert.fail(ex.getMessage());
        }
    }

    @Ignore("Print to stderr instead of throw exception")
    @Test(expected = IllegalArgumentException.class)
    public void testNoSuchHeaderInFile_throwException() {
        String input = getClass().getResource("/test_data.csv").getFile();
        List<String> nonexistentHeader = Arrays.asList("Weight");
        IllegalArgumentException expectedException = null;
        try (BufferedReader reader = IOFactory.getBufferedReader(input)) {
            List<CSVRecord> records = CSVFormat.DEFAULT.parse(reader).getRecords();
            ColumnsSplitter.filterRecordsByColumnsHeaders(records, nonexistentHeader);
        } catch (IOException ex) {
            Assert.fail(ex.getMessage());
        } catch (IllegalArgumentException ex) {
            expectedException = ex;
        }
        if (expectedException != null) {
            Assert.assertEquals("No 'Weight' header in CSV file", expectedException.getMessage());
            throw expectedException;
        }
    }

    @Test
    public void testNoSuchHeaderInFile_printToStdErr() {
        String input = getClass().getResource("/test_data.csv").getFile();
        List<String> nonexistentHeader = Arrays.asList("Weight");
        try (BufferedReader reader = IOFactory.getBufferedReader(input)) {
            List<CSVRecord> records = CSVFormat.DEFAULT.parse(reader).getRecords();
            ColumnsSplitter.filterRecordsByColumnsHeaders(records, nonexistentHeader);
        } catch (IOException ex) {
            Assert.fail(ex.getMessage());
        }
        Assert.assertEquals("No 'Weight' header in CSV file\n", errContent.toString());
    }

}

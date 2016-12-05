# CsvUtil

**CsvUtil** provides ability to split CSV file by certain headers or column indexes.

### Example

1. Call *ColumnsSplitter.splitByColumnsIndexes(...)* to extract columns by index that specified in the list.

*splitByColumnsIndexes* method accepts next parameters:
* String input - absolute path to input file
* String output - absolute path to output file
* List\<Integer\> indexes - column indexes

2. Call *ColumnsSplitter.splitByColumnsHeaders(...)* to extract columns by headers that specified in the list.

*splitByColumnsHeaders* method accepts next parameters:
* String input - absolute path to input file
* String output - absolute path to output file
* List\<String\> indexes - column headers

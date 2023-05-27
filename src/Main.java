import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) throws IOException {
        String csvFile = "Police_Department_Incident_Reports__2018_to_Present.csv";

        try (FileReader fileReader = new FileReader(csvFile);
             CSVParser csvParser = new CSVParser(fileReader, CSVFormat.DEFAULT.withFirstRecordAsHeader())) {

            // Get the headers from the CSV file
            String[] headers = csvParser.getHeaderMap().keySet().toArray(new String[0]);

            // Find the indices of the required fields
            Map<String, Integer> fieldIndices = getFieldIndices(headers);

            // Calculate count of incidents per Incident Category for each year using TreeMap
            TreeMap<Integer, TreeMap<String, Long>> incidentCountMap = createIncidentCountMap(csvParser, fieldIndices);

            // Print the count of incidents per Incident Category for each year
            for (Map.Entry<Integer, TreeMap<String, Long>> entry : incidentCountMap.entrySet()) {
                int year = entry.getKey();
                TreeMap<String, Long> categoryCountMap = entry.getValue();

                System.out.println("Year: " + year);
                for (Map.Entry<String, Long> categoryEntry : categoryCountMap.entrySet()) {
                    String category = categoryEntry.getKey();
                    long count = categoryEntry.getValue();
                    System.out.printf("    Incident Category: %-20s  Incident Count: %d%n", category, count);
                }
                System.out.println();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static Map<String, Integer> getFieldIndices(String[] headers) {
        Map<String, Integer> fieldIndices = new HashMap<>();
        for (int i = 0; i < headers.length; i++) {
            fieldIndices.put(headers[i], i);
        }
        return fieldIndices;
    }

    private static TreeMap<Integer, TreeMap<String, Long>> createIncidentCountMap(CSVParser csvParser, Map<String, Integer> fieldIndices) {
        // Calculate count of incidents per Incident Category for each year using stream and TreeMap
        return csvParser.getRecords().stream()
                .collect(Collectors.groupingBy(record -> extractYear(record.get(fieldIndices.get("Incident Date"))),
                        TreeMap::new,
                        Collectors.groupingBy(record -> record.get(fieldIndices.get("Incident Category")),
                                TreeMap::new,
                                Collectors.counting())));
    }

    private static int extractYear(String incidentDate) {
        // Assuming the incident date format is "yyyy-mm-dd"
        return Integer.parseInt(incidentDate.substring(0, 4));
    }
}

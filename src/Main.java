import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) throws IOException {
        String csvFile = "Police_Department_Incident_Reports__2018_to_Present.csv";
        List<Incident> incidents = new ArrayList<Incident>();

        try (FileReader fileReader = new FileReader(csvFile);
             CSVParser csvParser = new CSVParser(fileReader, CSVFormat.DEFAULT.withFirstRecordAsHeader())) {

            // Get the headers from the CSV file
            String[] headers = csvParser.getHeaderMap().keySet().toArray(new String[0]);

            // Find the indices of the required fields
            Map<String, Integer> fieldIndices = getFieldIndices(headers);

            // Process each record of the CSV file
            for (CSVRecord record : csvParser) {
                // Create an Incident object from the CSV record
                Incident incident = createIncidentFromRecord(record);

                // Add the Incident object to the list
                incidents.add(incident);
            }

            System.out.println(incidents.size());

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

    private static Incident createIncidentFromRecord(CSVRecord record) {
        // Create an Incident object from the CSV record
        DateTimeFormatter ldt = DateTimeFormatter.ofPattern("yyyy/MM/dd hh:mm:ss a",Locale.ENGLISH);

        Incident incident = new Incident();
        LocalDateTime incDate = LocalDateTime.parse(record.get("Incident Datetime"), ldt);
        incident.setIncidentDatetime(incDate);
        incident.setIncidentDate(record.get("Incident Date"));
        incident.setIncidentTime(record.get("Incident Time"));
        incident.setIncidentYear(record.get("Incident Year"));
        incident.setIncidentDayOfWeek(record.get("Incident Day of Week"));
        incident.setReportDatetime(record.get("Report Datetime"));
        incident.setRowId(record.get("Row ID"));
        incident.setIncidentId(record.get("Incident ID"));
        incident.setIncidentNumber(record.get("Incident Number"));
        incident.setCadNumber(record.get("CAD Number"));
        incident.setReportTypeCode(record.get("Report Type Code"));
        incident.setReportTypeDescription(record.get("Report Type Description"));
        incident.setFiledOnline(record.get("Filed Online"));
        incident.setIncidentCode(record.get("Incident Code"));
        incident.setIncidentCategory(record.get("Incident Category"));
        incident.setIncidentSubcategory(record.get("Incident Subcategory"));
        incident.setIncidentDescription(record.get("Incident Description"));
        incident.setResolution(record.get("Resolution"));
        incident.setIntersection(record.get("Intersection"));
        incident.setCnn(record.get("CNN"));
        incident.setPoliceDistrict(record.get("Police District"));
        incident.setAnalysisNeighborhood(record.get("Analysis Neighborhood"));
        //incident.setSupervisor(record.get("Supervisor"));

        return incident;
    }
}

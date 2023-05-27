import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) throws IOException {
        String csvFile = "Police_Department_Incident_Reports__2018_to_Present.csv";

        try (FileReader fileReader = new FileReader(csvFile);
             CSVParser csvParser = new CSVParser(fileReader, CSVFormat.DEFAULT.withFirstRecordAsHeader())) {

            // Create a list to store Incident objects
            List<Incident> incidents = csvParser.getRecords().stream()
                    .map(Main::createIncidentFromRecord)
                    .toList();

            for (Incident incident : incidents) {
                if (incident.getIncidentSubcategory().equals("Other")) {
                    incident.setIncidentSubcategory("Other: " + incident.getIncidentDescription());
                } else if (incident.getIncidentCategory().equals("Burglary")) {
                    incident.setIncidentSubcategory("Burglary: " + incident.getIncidentDescription());
                }
            }

            // Count Incidents by Incident Category and Incident Year using a stream
            Map<String, TreeMap<String, Long>> countBySubCategoryAndYear = incidents.stream()
                    .collect(Collectors.groupingBy(Incident::getIncidentSubcategory,
                            TreeMap::new,
                            Collectors.groupingBy(Incident::getIncidentYear, TreeMap::new, Collectors.counting())));

            // Print the count of Incidents by Incident Category and Incident Year (sorted)
            for (Map.Entry<String, TreeMap<String, Long>> subCategoryEntry : countBySubCategoryAndYear.entrySet()) {
                String category = subCategoryEntry.getKey();
                TreeMap<String, Long> yearCountMap = subCategoryEntry.getValue();

                System.out.println("Incident Category: " + category);
                for (Map.Entry<String, Long> yearEntry : yearCountMap.entrySet()) {
                    String year = yearEntry.getKey();
                    long count = yearEntry.getValue();
                    System.out.printf("    Incident Year: %4s  Incident Count: %d%n", year, count);
                }
                System.out.println();

                CategoryDataset dataset = createDataset(incidents);

                // Create the bar chart
                JFreeChart chart = createBarChart(dataset);

                // Save the chart to a PNG image file
                saveChartAsPNG(chart, "incidents_per_year_chart.png");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static CategoryDataset createDataset(List<Incident> incidents) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        // Filter incidents where Incident Category is "Burglary"
        List<Incident> filteredIncidents = incidents.stream()
                .filter(incident -> "Burglary".equals(incident.getIncidentCategory()))
                .toList();

        // Count incidents per year
        TreeMap<String, Long> incidentsPerYear = filteredIncidents.stream()
                .collect(Collectors.groupingBy(Incident::getIncidentYear, TreeMap::new, Collectors.counting()));

        // Add incident counts to the dataset
        for (Map.Entry<String, Long> entry : incidentsPerYear.entrySet()) {
            String year = entry.getKey();
            Long count = entry.getValue();
            dataset.addValue(count, "Burglary", year);
        }

        return dataset;
    }

    private static JFreeChart createBarChart(CategoryDataset dataset) {
        JFreeChart chart = ChartFactory.createBarChart(
                "Incidents per Year (Category: Burglary)",
                "Year",
                "Incident Count",
                dataset,
                PlotOrientation.VERTICAL,
                true,
                true,
                false
        );

        return chart;
    }

    private static void saveChartAsPNG(JFreeChart chart, String filename) {
        try {
            ChartUtilities.saveChartAsPNG(new File(filename), chart, 800, 600);
            System.out.println("Chart saved as " + filename);
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

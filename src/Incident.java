import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Objects;

public class Incident {
    private LocalDateTime incidentDatetime;
    private String incidentDate;
    private String incidentTime;
    private String incidentYear;
    private String incidentDayOfWeek;
    private String reportDatetime;
    private String rowId;
    private String incidentId;
    private String incidentNumber;
    private String cadNumber;
    private String reportTypeCode;
    private String reportTypeDescription;
    private String filedOnline;
    private String incidentCode;
    private String incidentCategory;
    private String incidentSubcategory;
    private String incidentDescription;
    private String resolution;
    private String intersection;
    private String cnn;
    private String policeDistrict;
    private String analysisNeighborhood;
    private String supervisor;

    // Constructors, getters, and setters

    public Incident() {
    }

    public Incident(LocalDateTime incidentDatetime, String incidentDate, String incidentTime, String incidentYear,
                    String incidentDayOfWeek, String reportDatetime, String rowId, String incidentId,
                    String incidentNumber, String cadNumber, String reportTypeCode, String reportTypeDescription,
                    String filedOnline, String incidentCode, String incidentCategory, String incidentSubcategory,
                    String incidentDescription, String resolution, String intersection, String cnn,
                    String policeDistrict, String analysisNeighborhood, String supervisor) {
        this.incidentDatetime = incidentDatetime;
        this.incidentDate = incidentDate;
        this.incidentTime = incidentTime;
        this.incidentYear = incidentYear;
        this.incidentDayOfWeek = incidentDayOfWeek;
        this.reportDatetime = reportDatetime;
        this.rowId = rowId;
        this.incidentId = incidentId;
        this.incidentNumber = incidentNumber;
        this.cadNumber = cadNumber;
        this.reportTypeCode = reportTypeCode;
        this.reportTypeDescription = reportTypeDescription;
        this.filedOnline = filedOnline;
        this.incidentCode = incidentCode;
        this.incidentCategory = incidentCategory;
        this.incidentSubcategory = incidentSubcategory;
        this.incidentDescription = incidentDescription;
        this.resolution = resolution;
        this.intersection = intersection;
        this.cnn = cnn;
        this.policeDistrict = policeDistrict;
        this.analysisNeighborhood = analysisNeighborhood;
        this.supervisor = supervisor;
    }

    // Getters and setters

    public LocalDateTime getIncidentDatetime() {
        return incidentDatetime;
    }

    public void setIncidentDatetime(LocalDateTime incidentDatetime) {
        this.incidentDatetime = incidentDatetime;
    }

    public String getIncidentDate() {
        return incidentDate;
    }

    public void setIncidentDate(String incidentDate) {
        this.incidentDate = incidentDate;
    }

    public String getIncidentTime() {
        return incidentTime;
    }

    public void setIncidentTime(String incidentTime) {
        this.incidentTime = incidentTime;
    }

    public String getIncidentYear() {
        return incidentYear;
    }

    public void setIncidentYear(String incidentYear) {
        this.incidentYear = incidentYear;
    }

    public String getIncidentDayOfWeek() {
        return incidentDayOfWeek;
    }

    public void setIncidentDayOfWeek(String incidentDayOfWeek) {
        this.incidentDayOfWeek = incidentDayOfWeek;
    }

    public String getReportDatetime() {
        return reportDatetime;
    }

    public void setReportDatetime(String reportDatetime) {
        this.reportDatetime = reportDatetime;
    }

    public String getRowId() {
        return rowId;
    }

    public void setRowId(String rowId) {
        this.rowId = rowId;
    }

    public String getIncidentId() {
        return incidentId;
    }

    public void setIncidentId(String incidentId) {
        this.incidentId = incidentId;
    }

    public String getIncidentNumber() {
        return incidentNumber;
    }

    public void setIncidentNumber(String incidentNumber) {
        this.incidentNumber = incidentNumber;
    }

    public String getCadNumber() {
        return cadNumber;
    }

    public void setCadNumber(String cadNumber) {
        this.cadNumber = cadNumber;
    }

    public String getReportTypeCode() {
        return reportTypeCode;
    }

    public void setReportTypeCode(String reportTypeCode) {
        this.reportTypeCode = reportTypeCode;
    }

    public String getReportTypeDescription() {
        return reportTypeDescription;
    }

    public void setReportTypeDescription(String reportTypeDescription) {
        this.reportTypeDescription = reportTypeDescription;
    }

    public String getFiledOnline() {
        return filedOnline;
    }

    public void setFiledOnline(String filedOnline) {
        this.filedOnline = filedOnline;
    }

    public String getIncidentCode() {
        return incidentCode;
    }

    public void setIncidentCode(String incidentCode) {
        this.incidentCode = incidentCode;
    }

    public String getIncidentCategory() {
        return incidentCategory;
    }

    public void setIncidentCategory(String incidentCategory) {
        this.incidentCategory = incidentCategory;
    }

    public String getIncidentSubcategory() {
        return incidentSubcategory;
    }

    public void setIncidentSubcategory(String incidentSubcategory) {
        this.incidentSubcategory = incidentSubcategory;
    }

    public String getIncidentDescription() {
        return incidentDescription;
    }

    public void setIncidentDescription(String incidentDescription) {
        this.incidentDescription = incidentDescription;
    }

    public String getResolution() {
        return resolution;
    }

    public void setResolution(String resolution) {
        this.resolution = resolution;
    }

    public String getIntersection() {
        return intersection;
    }

    public void setIntersection(String intersection) {
        this.intersection = intersection;
    }

    public String getCnn() {
        return cnn;
    }

    public void setCnn(String cnn) {
        this.cnn = cnn;
    }

    public String getPoliceDistrict() {
        return policeDistrict;
    }

    public void setPoliceDistrict(String policeDistrict) {
        this.policeDistrict = policeDistrict;
    }

    public String getAnalysisNeighborhood() {
        return analysisNeighborhood;
    }

    public void setAnalysisNeighborhood(String analysisNeighborhood) {
        this.analysisNeighborhood = analysisNeighborhood;
    }

    public String getSupervisor() {
        return supervisor;
    }

    public void setSupervisor(String supervisor) {
        this.supervisor = supervisor;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Incident incident = (Incident) o;
        return Objects.equals(rowId, incident.rowId) && Objects.equals(incidentId, incident.incidentId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(rowId, incidentId);
    }

    @Override
    public String toString() {
        return "Incident{" +
                "incidentDatetime=" + incidentDatetime +
                ", reportDatetime=" + reportDatetime +
                ", incidentCategory='" + incidentCategory + '\'' +
                ", incidentSubcategory='" + incidentSubcategory + '\'' +
                ", intersection='" + intersection + '\'' +
                '}';
    }
}
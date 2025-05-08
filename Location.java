
public class Location {
    private String name;
    private int reportNumber;
    private static String[] locations = {"Library", "Dormitory", "Cafeteria", "Sports Hall"};

    public Location(String name) {
        this.name = name;
        this.reportNumber = 0;
    }

    public void incrementReportNumber() {
        reportNumber++;
    }

    public String getName() { return name; }
    public int getReportNumber() { return reportNumber; }

    public static String[] getAvailableLocations() {
        return locations;
    }
}

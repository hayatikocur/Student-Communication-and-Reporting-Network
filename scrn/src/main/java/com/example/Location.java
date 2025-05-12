package com.example;

public class Location {
    /*
    locationId(int): AI, PK
    location(varchar)
     */
    private String name;
    private int reportNumber;
    private String[] locations;

    public void incrementReportNumber() {
        reportNumber++;
    }
}
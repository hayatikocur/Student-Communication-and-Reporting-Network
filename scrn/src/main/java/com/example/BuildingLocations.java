package com.example;

import java.util.HashMap;
import java.util.Map;

public class BuildingLocations {
    private static final Map<String, double[]> locations = new HashMap<>();
    static {
        locations.put("B Binası", new double[]{39.868880, 32.747980});
        locations.put("SB Binası", new double[]{39.868378, 32.748163});
        locations.put("V Binası", new double[]{39.867069, 32.750051});
        locations.put("FC Binası", new double[]{39.867028, 32.749364});
        locations.put("FB Binası", new double[]{39.866707, 32.749654});
        locations.put("FA Binası", new double[]{39.866188, 32.750008});
        locations.put("FD Binası", new double[]{39.866377, 32.749203});
        locations.put("FF Binası", new double[]{39.865867, 32.748774});
        locations.put("Kütüphane", new double[]{39.870181, 32.750013});
        // gerekirse diğer binalar
    }

    public static double getLat(String name) {
        return locations.containsKey(name) ? locations.get(name)[0] : 0;
    }

    public static double getLng(String name) {
        return locations.containsKey(name) ? locations.get(name)[1] : 0;
    }
}

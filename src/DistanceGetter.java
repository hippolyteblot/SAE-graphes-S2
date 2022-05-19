
// Java program to calculate Distance Between
// Two Points on Earth
import java.util.*;
import java.lang.*;

public class DistanceGetter {
    public static double distance(Sommet sm1, Sommet sm2) {
        // The math module contains a function
        // named toRadians which converts from
        // degrees to radians.
        double lon1 = Math.toRadians(sm1.getLocX());
        double lon2 = Math.toRadians(sm2.getLocX());
        double lat1 = Math.toRadians(sm1.getLocY());
        double lat2 = Math.toRadians(sm2.getLocY());
        // Haversine formula
        double dlon = lon2 - lon1;
        double dlat = lat2 - lat1;
        double a = Math.pow(Math.sin(dlat / 2), 2)
                + Math.cos(lat1) * Math.cos(lat2)
                * Math.pow(Math.sin(dlon / 2),2);

        double c = 2 * Math.asin(Math.sqrt(a));

        // Radius of earth in kilometers. Use 3956
        // for miles
        double r = 6371;

        // calculate the result
        return(c * r);
    }
}


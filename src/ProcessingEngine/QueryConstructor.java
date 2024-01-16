package ProcessingEngine;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Scanner;

public class QueryConstructor {

    StringBuffer placesQuery = new StringBuffer();
    StringBuffer distanceQuery = new StringBuffer();
    String where;
    String radius;
    HashMap<String, String> locations = new HashMap<>();

    public QueryConstructor() throws FileNotFoundException {
        //readFile("C:\\Users\\blapa\\Desktop\\Cours IUT\\Java\\test\\src\\locationsFR.csv");
    }

    public QueryConstructor(String where, String radius) {
        this.where = where;
        this.radius = radius;
    }

    public void addLocation(String location, String value) {
        locations.put(location, value);
    }
    public void readFile(String fileName) throws FileNotFoundException {
        File file = new File(fileName);
        Scanner fr = new Scanner(file);
        String line;
        while(fr.hasNextLine()) {
            line = fr.nextLine();
            line.replace(" ", "");
            String[] parts = line.split(",");
            locations.put(parts[0], parts[1]+","+parts[2]);
        }
    }

    public void constructLocationQuery(String where, String radius, String type) {
        /* Old version with hashmap
        query = new StringBuffer();
        query.append("https://maps.googleapis.com/maps/api/place/search/json?location=");
        query.append(locations.get(where));
        query.append("&radius=");
        query.append(radius);
        query.append("&types=");
        query.append(type);
        query.append("&key=AIzaSyDsSWpa4y-dsYA7BBA-I4xJq60be0qAHUI");
         */
        placesQuery = new StringBuffer();
        placesQuery.append("https://maps.googleapis.com/maps/api/place/search/json?location=");
        placesQuery.append(where);
        placesQuery.append("&radius=");
        placesQuery.append(radius);
        placesQuery.append("&types=");
        placesQuery.append(type);
        placesQuery.append("&key=AIzaSyDsSWpa4y-dsYA7BBA-I4xJq60be0qAHUI");
    }

    public void constructSearchQuery(String what) {
        placesQuery = new StringBuffer();
        placesQuery.append("https://maps.googleapis.com/maps/api/place/findplacefromtext/json?input=");
        placesQuery.append(what);
        placesQuery.append("&inputtype=textquery&fields=formatted_address%2Cgeometry&key=$KEY");
    }

    public String getQuery() {
        return placesQuery.toString();
    }
}

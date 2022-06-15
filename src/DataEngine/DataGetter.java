package DataEngine;

import IHM.SearchBar;
import Launch.*;
import ProcessingEngine.QueryConstructor;
import org.json.simple.*;
import org.json.simple.parser.*;

import java.io.*;
import java.net.URL;
import java.nio.charset.StandardCharsets;


public class DataGetter {
    private String locationSelected;
    private String typeSelected;
    private String radiusSelected;
    private int numberOfResultsSelected;
    private SearchBar sb;

    public DataGetter(Main main) throws FileNotFoundException {
        this.sb = new SearchBar(this, main);
    }

    public void locationSelected() throws Exception {
        QueryConstructor qc = new QueryConstructor();
        qc.constructSearchQuery(locationSelected);
        String locationQuery = qc.getQuery();
        System.out.println("locationquery = " + locationQuery);
        JSONObject json = readJsonFromUrl(locationQuery);
        String location = getLocationFromJSON(json);
        qc.constructLocationQuery(location, radiusSelected, typeSelected);
        System.out.println("dataquery = " + qc.getQuery());
        processData(qc.getQuery(), typeSelected, numberOfResultsSelected);

    }

    public JSONObject readJsonFromUrl(String url) throws IOException {
        try (InputStream is = new URL(url).openStream()) {
            JSONParser jsonP = new JSONParser();
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
            return (JSONObject) jsonP.parse(rd);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    public void writeJSONs(JSONArray jsonA, String type, int numberOfResults) throws IOException {
        String typeFound = sb.getTypeFound(type);
        FileWriter file = new FileWriter("src\\data.csv", true);
        if(jsonA.size() < numberOfResults) {numberOfResults = jsonA.size();}
        for(int i = 0; i < numberOfResults; i++) {
            JSONObject newObj = (JSONObject)jsonA.get(i);
            JSONObject geometry = (JSONObject)newObj.get("geometry");
            JSONObject location = (JSONObject)geometry.get("location");

            file.write(typeFound + "," +
                    newObj.get("name").toString().replace(",", " -").replace(":", "-") +
                    "," + location.get("lat") + "," + location.get("lng") + "," + ":;;;\n");
        }
        file.flush();
        file.close();
    }
    public void eraseData() throws IOException {
        FileWriter file = new FileWriter("src\\data.csv", false);
        file.write("");
        file.flush();
        file.close();
    }

    public String getLocationFromJSON(JSONObject json) {
        JSONArray jsonA = (JSONArray)json.get("candidates");
        if(jsonA.size() == 0) {
            System.out.println("No location found, default location = Lyon");
        }
        else{
            JSONObject first = (JSONObject)jsonA.get(0);
            JSONObject geometry = (JSONObject)first.get("geometry");
            JSONObject location = (JSONObject)geometry.get("location");
            return location.get("lat") + "," + location.get("lng");
        }
        return "45.764043,4.835659";
    }

    public void processData(String url, String type, int numberOfResults) throws Exception {
        JSONObject json = readJsonFromUrl(url);
        JSONArray jsonA = (JSONArray)json.get("results");
        writeJSONs(jsonA, type, numberOfResults+1);

    }

    public void setLocationSelected(String location) {
        locationSelected = location;
    }

    public SearchBar getSearchBar() {
        return sb;
    }
    public void setTypeSelected(String type) {
        typeSelected = type;
    }
    public void setRadiusSelected(String radius) {
        radiusSelected = radius;
    }

    public String getLocationSelected() {
        return locationSelected;
    }

    public String getRadiusSelected() {
        return radiusSelected;
    }

    public void setNumberOfResultsSelected(int numbOfResults) {
        this.numberOfResultsSelected = numbOfResults;
    }
}

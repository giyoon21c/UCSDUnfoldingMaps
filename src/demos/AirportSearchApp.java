package demos;

import de.fhpotsdam.unfolding.data.PointFeature;
import de.fhpotsdam.unfolding.geo.Location;
import de.fhpotsdam.unfolding.marker.Marker;
import module6.AirportMarker;
import parsing.ParseFeed;
import processing.core.PApplet;

import java.io.*;
import java.util.*;

/**
 * Created on 8/10/17.
 */
public class AirportSearchApp {

    /*
    public void getAirportData(List<PointFeature> features, List<Marker> airportList) {
        features = ParseFeed.parseAirports(this, "airports.dat");
        // list for markers, hashmap for quicker access when matching with routes
        airportList = new ArrayList<Marker>();
        HashMap<Integer, Location> airports = new HashMap<Integer, Location>();

        // create markers from features
        for(PointFeature feature : features) {
            AirportMarker m = new AirportMarker(feature);

            m.setRadius(5);
            airportList.add(m);

            // put airport in hashmap with OpenFlights unique id for key
            airports.put(Integer.parseInt(feature.getId()), feature.getLocation());
        }
    }
    */

    private static void readAirportDat(List<Airport> airports) throws IOException {
        FileInputStream fileInputStream =
                new FileInputStream("/home/dumble/IdeaProjects/UCSDUnfoldingMaps/data/airports_v0.dat");
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(fileInputStream));

        String line = null;
        while ((line = bufferedReader.readLine()) != null) {
            //System.out.println(line);
            String[] columns = line.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)");
            System.out.println(columns.toString());
            //System.out.println(Arrays.toString(columns));
            Airport airport = new Airport(Integer.parseInt(columns[0]),
                    columns[1],
                    columns[2],
                    columns[3],
                    columns[4],
                    columns[5]);
            //System.out.println(airport);
            airports.add(airport);

        }
        bufferedReader.close();
    }

    private static void printAirportDat(List<Airport> airports) throws IOException {
        for (Airport airport : airports) {
            System.out.format("%d %s %s %s %s %s\n",
                    airport.getAirportID(),
                    airport.getName(),
                    airport.getCity(),
                    airport.getCountry(),
                    airport.getCode3(),
                    airport.getCode4());
        }
    }

    public static boolean linearSearch(List<Airport> airports, String airportCityName) {
        for (Airport airport : airports) {
            System.out.println(airport.getCity() + " vs " + airportCityName );
            if (airport.getCity().equals(airportCityName)) {
                //System.out.println(airport.getCity());
                return true;
            }
        }
        return false;
    }

    public static void main(String[] args) throws IOException {
        /*
        List<Marker> airportList = null;
        List<PointFeature> features = null;
        AirportSearchApp searchApp = new AirportSearchApp();
        searchApp.getAirportData(features, airportList);
        */

        List<Airport> airports = new ArrayList<>();;
        readAirportDat(airports);
        printAirportDat(airports);

        String airportCityName = "\"Godthaab\"";
        if (linearSearch(airports, airportCityName)) {
            System.out.println(airportCityName + " found!");
        } else {
            System.out.println(airportCityName + " not found!");
        }

        System.out.println("\n\n");
        System.out.println("before sorting...");
        printAirportDat(airports);

        Collections.sort(airports);
        System.out.println("after sortring...");
        printAirportDat(airports);

    }
}

package demos;

import de.fhpotsdam.unfolding.data.PointFeature;
import de.fhpotsdam.unfolding.geo.Location;
import de.fhpotsdam.unfolding.marker.Marker;
import module6.AirportMarker;
import parsing.ParseFeed;
import processing.core.PApplet;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created on 8/10/17.
 */
public class AirportSearchApp extends PApplet {

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

    public static void main(String[] args) {
        List<Marker> airportList = null;
        List<PointFeature> features = null;
        AirportSearchApp searchApp = new AirportSearchApp();
        searchApp.getAirportData(features, airportList);
        System.out.println("binary search");
    }
}

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

    public static boolean binarySearch(List<Airport> airports, String airportCityName) {
        int low = 0;
        int high = airports.size()-1;
        while (low <= high) {
            //int mid = (low + high) / 2;
            int mid = low + ((high-low)/2);
            System.out.println(low + ", " + high + ", " + mid + ", " + (airports.get(mid).getCity()));
            int compare = airportCityName.compareTo(airports.get(mid).getCity());
            if (compare < 0) {
                high = mid - 1;
            } else if (compare > 0){
                low = mid + 1;
            } else {
                System.out.println("found!");
                return true;
            }
        }
        return false;
    }

    public static void selectionSort() {
        List<Integer> nums = new ArrayList<>();
        nums.add(5);
        nums.add(3);
        nums.add(10);
        nums.add(2);
        nums.add(7);
        System.out.println(nums);

        /*
        for (int i = 0; i < nums.size(); i++) {
            int smallestIndex = i;
            int smallSoFarSeen = nums.get(i);
            System.out.println(smallestIndex + ": " + smallSoFarSeen);
            for (int j = i+1; j < nums.size(); j++) {
                System.out.println("   " + j + ": " + nums.get(j));
                if (smallSoFarSeen > nums.get(j)) {
                    smallestIndex = j;
                    smallSoFarSeen = nums.get(j);
                    System.out.println("   smallest value so far: " + smallSoFarSeen + " at index " + smallestIndex);
                }
            }
            System.out.println("       smallest index = " + smallestIndex + " value: " + smallSoFarSeen);

            int OldValueToSave = nums.get(i);
            nums.set(i, nums.get(smallestIndex));
            System.out.println("progress ->" + nums);

            nums.set(smallestIndex, OldValueToSave);
            System.out.println("progress ->" + nums);
            System.out.println(i + ", " + nums.get(smallestIndex));
        }
        System.out.println(nums);
        */

        for (int i = 0; i < nums.size(); i++) {
            int minIndex = i;
            for (int j = i+1; j < nums.size(); j++) {
                if (nums.get(j) < nums.get(minIndex)) {
                    minIndex = j;
                }
            }
            //System.out.println("minIndex = " + minIndex);
            int temp = nums.get(i);
            nums.set(i, nums.get(minIndex));
            nums.set(minIndex, temp);
            System.out.println(nums);
        }
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

        System.out.println("\n\n");
        System.out.println("before sorting...");
        printAirportDat(airports);

        String airportCityName = "\"Godthaab\"";
        if (linearSearch(airports, airportCityName)) {
            System.out.println(airportCityName + " found!");
        } else {
            System.out.println(airportCityName + " not found!");
        }

        Collections.sort(airports);
        System.out.println("after sortring...");
        printAirportDat(airports);

        if (binarySearch(airports, airportCityName)) {
            System.out.println(airportCityName + " found!");
        } else {
            System.out.println(airportCityName + " not found!");
        }

        selectionSort();
    }
}

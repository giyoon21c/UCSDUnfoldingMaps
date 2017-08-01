package module3;

//Java utilities libraries
import java.util.ArrayList;
//import java.util.Collections;
//import java.util.Comparator;
import java.util.List;

//Processing library
import de.fhpotsdam.unfolding.geo.Location;
import de.fhpotsdam.unfolding.providers.Microsoft;
import processing.core.PApplet;

//Unfolding libraries
import de.fhpotsdam.unfolding.UnfoldingMap;
import de.fhpotsdam.unfolding.marker.Marker;
import de.fhpotsdam.unfolding.data.PointFeature;
import de.fhpotsdam.unfolding.marker.SimplePointMarker;
import de.fhpotsdam.unfolding.providers.Google;
import de.fhpotsdam.unfolding.providers.MBTilesMapProvider;
import de.fhpotsdam.unfolding.utils.MapUtils;

//Parsing library
import parsing.ParseFeed;

import static java.awt.Color.white;

/** EarthquakeCityMap
 * An application with an interactive map displaying earthquake data.
 * Author: UC San Diego Intermediate Software Development MOOC team
 * @author Your name here
 * Date: July 17, 2015
 * */
public class EarthquakeCityMap extends PApplet {

	// You can ignore this.  It's to keep eclipse from generating a warning.
	private static final long serialVersionUID = 1L;

	// IF YOU ARE WORKING OFFLINE, change the value of this variable to true
	private static final boolean offline = false;
	//private static final boolean offline = true;

	// Less than this threshold is a light earthquake
	public static final float THRESHOLD_MODERATE = 5;

	// Less than this threshold is a minor earthquake
	public static final float THRESHOLD_LIGHT = 4;

	/** This is where to find the local tiles, for working without an Internet connection */
	public static String mbTilesString = "blankLight-1-3.mbtiles";
	
	// The map
	private UnfoldingMap map;
	private UnfoldingMap key;
	
	//feed with magnitude 2.5+ Earthquakes
	private String earthquakesURL = "http://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/2.5_week.atom";

    // Here is an example of how to use Processing's color method to generate
    // an int that represents the color yellow.
    int yellow = color(255, 255, 0);
    int blue = color(0, 0, 255);
    int red = color(255, 0, 0);

	public void setup() {
		size(950, 600, OPENGL);

		if (offline) {
		    //map = new UnfoldingMap(this, 200, 50, 700, 500, new MBTilesMapProvider(mbTilesString));
		    map = new UnfoldingMap(this, 200, 50, 700, 500, new Microsoft.RoadProvider());
			//earthquakesURL = "2.5_week.atom"; 	// Same feed, saved Aug 7, 2015, for working offline
			earthquakesURL = "/home/dumble/IdeaProjects/UCSDUnfoldingMaps/data/2.5_week.atom"; 	// Same feed, saved Aug 7, 2015, for working offline
		}
		else {
			//map = new UnfoldingMap(this, 200, 50, 700, 500, new Google.GoogleMapProvider());
			map = new UnfoldingMap(this, 200, 50, 700, 500, new Microsoft.RoadProvider());
			// IF YOU WANT TO TEST WITH A LOCAL FILE, uncomment the next line
			//earthquakesURL = "2.5_week.atom";
			earthquakesURL = "/home/dumble/IdeaProjects/UCSDUnfoldingMaps/data/2.5_week.atom";
			// Same feed, saved Aug 7, 2015, for working offline
		}
		
	    map.zoomToLevel(2);
	    MapUtils.createDefaultEventDispatcher(this, map);	

	    // a single location added as a marker int the map...
        /*
	    Location valLoc = new Location(-38.1f, -73.03f);
	    Marker val = new SimplePointMarker(valLoc);
	    map.addMarker(val);
        */



	    // The List you will populate with new SimplePointMarkers
	    List<Marker> markers = new ArrayList<Marker>();

	    //Use provided parser to collect properties for each earthquake
	    //PointFeatures have a getLocation method
	    List<PointFeature> earthquakes = ParseFeed.parseEarthquake(this, earthquakesURL);
	    
	    // These print statements show you (1) all of the relevant properties 
	    // in the features, and (2) how to get one property and use it

	    for (int count = 0; count < earthquakes.size(); count++) {
            System.out.println("inside loop, counts: " + earthquakes.size());
            PointFeature f = earthquakes.get(count);
	    	System.out.println(f.getProperties());
	    	Object magObj = f.getProperty("magnitude");
	    	float mag = Float.parseFloat(magObj.toString());
	    	// PointFeatures also have a getLocation method
            /*
            SimplePointMarker simpleVal = new SimplePointMarker(f.getLocation(), f.getProperties());
            if (mag >= 5.0) {
                simpleVal.setColor(red);
                simpleVal.setRadius(30.0f);
            } else if ((mag >= 4.0) && (mag <= 4.9)) {
                simpleVal.setColor(yellow);
                simpleVal.setRadius(20.0f);
            } else {
                simpleVal.setColor(blue);
                simpleVal.setRadius(10.0f);
            }
            markers.add(simpleVal);
            */
            Marker val = new SimplePointMarker(f.getLocation(), f.getProperties());
            if (mag >= 5.0) {
                val.setColor(red);
                ((SimplePointMarker) val).setRadius(15.0f);
            } else if ((mag >= 4.0) && (mag <= 4.9)) {
                val.setColor(yellow);
                ((SimplePointMarker) val).setRadius(10.0f);
            } else {
                val.setColor(blue);
                ((SimplePointMarker) val).setRadius(5.0f);
            }
            markers.add(val);

	    }

	    //TODO: Add code here as appropriate
        map.addMarkers(markers);
	}
		
	// A suggested helper method that takes in an earthquake feature and 
	// returns a SimplePointMarker for that earthquake
	// TODO: Implement this method and call it from setUp, if it helps
	private SimplePointMarker createMarker(PointFeature feature)
	{
		// finish implementing and use this method, if it helps.
		return new SimplePointMarker(feature.getLocation());
	}
	
	public void draw() {
	    background(10);
	    map.draw();
	    addKey();
	}


	// helper method to draw key in GUI
	// TODO: Implement this method to draw the key
	private void addKey() 
	{	
		// Remember you can use Processing's graphics methods here
        //key = new UnfoldingMap(this, 10, 50, 700, 500, new Microsoft.RoadProvider());
        fill(175);
        rect(10, 50, 180, 500);

        // header
        textSize(10);
        String heading = "Earthquake key";
        fill(0, 0, 0);
        text(heading, 50, 100);

        // big earthquake
        fill(red);
        ellipse(50, 150, 15, 15);
        String big = "5.0 + Magnitude";
        text(big, 100, 150);

        // med earthquake
        fill(yellow);
        ellipse(50, 200, 10, 10);
        String medium = "4.0 + Magnitude";
        text(big, 100, 200);

        // small earthquake
        fill(blue);
        ellipse(50, 250, 5, 5);
        String small = "below 4.0";
        text(small, 100, 250);
    }
}

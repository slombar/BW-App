package edu.wpi.cs3733.teamO.Maps;

import com.google.maps.*;
import com.google.maps.errors.ApiException;
import com.google.maps.model.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

// Reference
// PlaceDetailsRequest query = PlacesApi.placeDetails(new
// GeoApiContext.Builder().apiKey(API_KEY).build(), prediction.getPrediction().placeId);
// API KEY "AIzaSyBXXka_F4Sk_WZ7pdCcS-zNatD8oLoLwJ0"

public class Directions {

  private static final String API_KEY = "AIzaSyBXXka_F4Sk_WZ7pdCcS-zNatD8oLoLwJ0";

  /**
   * gets the directions using google maps api
   *
   * @param fromLocation starting location
   * @param toLocation ending location
   * @return an arrayList of directionSteps... can be accessed in a loop using
   *     (directionStep).htmlInstructions
   * @throws ApiException not sure yet
   * @throws InterruptedException not sure yet
   * @throws IOException not sure yet
   */
  public static ArrayList<DirectionsStep> getDirections(String fromLocation, String toLocation)
      throws ApiException, InterruptedException, IOException {
    GeoApiContext geo = new GeoApiContext.Builder().apiKey(API_KEY).build();
    DirectionsApiRequest dirReq = DirectionsApi.getDirections(geo, fromLocation, toLocation);

    dirReq.departureTimeNow();
    dirReq.units(Unit.IMPERIAL);
    dirReq.mode(TravelMode.DRIVING);

    DirectionsResult result = null;
    ArrayList<DirectionsLeg> legs = new ArrayList<>();

    try {
      result = dirReq.await();
    } catch (ApiException e) { // TODO find what these exceptions mean to display to user;
      e.printStackTrace();
      System.out.println("HELP");
      throw e;
    } catch (InterruptedException e) {
      e.printStackTrace();
      System.out.println("HELP1");
      throw e;

    } catch (IOException e) {
      e.printStackTrace();
      System.out.println("HELP2");
      throw e;
    }

    assert result != null;

    DirectionsRoute[] route = result.routes;

    for (DirectionsRoute directionsRoute : route) {

      DirectionsLeg[] currentLegs = directionsRoute.legs;

      legs.addAll(Arrays.asList(currentLegs));
    }

    ArrayList<DirectionsStep> directionsSteps =
        new ArrayList<>(Arrays.asList(legs.get(0).steps)); // only takes one leg for now

    //    polyline shit that we dont use
    //    for (DirectionsRoute r : route) {
    //      System.out.println(r.overviewPolyline.getEncodedPath());
    //      System.out.println(decodePoly(r.overviewPolyline.getEncodedPath()));
    //    }

    geo.shutdown();
    return directionsSteps;
  }

  /**
   * removes some of the tags and bs from html to make it plain text
   *
   * @param html text with html bullshit
   * @return clean text hopefully
   */
  public static String html2text(String html) {

    boolean tag = true; // the text is part of a html tag
    boolean reserved = true; // the text is part of a reserved keyword
    char[] charA = html.toCharArray();
    StringBuilder noHtml = new StringBuilder();

    int count = 0;
    for (char c : charA) {

      if (c == '<') {

        tag = false;

        if (charA[count + 1] == 'd') {
          noHtml.append("\n");
        }

      } else if (c == '>') {
        tag = true;

      } else if (c == '&') {

        reserved = false;

      } else if (c == ';') {

        reserved = true;

      } else if (tag && reserved) {
        noHtml.append(c);
      }

      count++;
    }
    return noHtml.toString();
  }

  /**
   * this decodes a PolyLine datatype into gps coordinates
   *
   * @param encoded an encoded version of a polyline in string format
   * @return a list of the decoded coordinates
   */
  public static List<LatLng> decodePoly(String encoded) {

    List<LatLng> poly = new ArrayList<LatLng>();
    int index = 0, len = encoded.length();
    int lat = 0, lng = 0;

    while (index < len) {
      int b, shift = 0, result = 0;
      do {
        b = encoded.charAt(index++) - 63;
        result |= (b & 0x1f) << shift;
        shift += 5;
      } while (b >= 0x20);
      int dlat = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
      lat += dlat;

      shift = 0;
      result = 0;
      do {
        b = encoded.charAt(index++) - 63;
        result |= (b & 0x1f) << shift;
        shift += 5;
      } while (b >= 0x20);
      int dlng = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
      lng += dlng;

      LatLng p = new LatLng((((double) lat / 1E5)), (((double) lng / 1E5)));
      poly.add(p);
    }

    return poly;
  }

  public static String getApiKey() {
    return API_KEY;
  }

  public static String urlForm(String words) {
    return words.replace(' ', '+');
  }
}

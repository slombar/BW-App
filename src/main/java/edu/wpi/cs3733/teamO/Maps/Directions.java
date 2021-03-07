package edu.wpi.cs3733.teamO.Maps;

import com.google.maps.*;
import com.google.maps.errors.ApiException;
import com.google.maps.model.DirectionsResult;
import com.google.maps.model.DirectionsRoute;
import java.io.IOException;

// Reference
// PlaceDetailsRequest query = PlacesApi.placeDetails(new
// GeoApiContext.Builder().apiKey(API_KEY).build(), prediction.getPrediction().placeId);
// API KEY "AIzaSyBXXka_F4Sk_WZ7pdCcS-zNatD8oLoLwJ0"

public class Directions {

  public static void main(String[] args) {
    getDirections();
  }

  private static final String API_KEY = "AIzaSyBXXka_F4Sk_WZ7pdCcS-zNatD8oLoLwJ0";

  public static void getDirections() {

    DirectionsApiRequest dirReq =
        DirectionsApi.getDirections(
            new GeoApiContext.Builder().apiKey(API_KEY).build(),
            "100 Institute Rd. Worcester MA, 01609",
            "182 Russel Street Worcester MA, 01609");

    DirectionsResult result = null;

    try {
      result = dirReq.await();
    } catch (ApiException e) {
      e.printStackTrace();
      System.out.println("HELP");
    } catch (InterruptedException e) {
      e.printStackTrace();
      System.out.println("HELP1");
    } catch (IOException e) {
      e.printStackTrace();
      System.out.println("HELP2");
    }

    assert result != null;
    DirectionsRoute[] route = result.routes;


    for (DirectionsRoute r : route) {
      System.out.println(r.toString());
    }
  }
}

package edu.wpi.cs3733.teamO.Maps;

import com.google.gson.FieldNamingPolicy;
import com.google.maps.DirectionsApi;
import com.google.maps.DirectionsApiRequest;
import com.google.maps.GeoApiContext;
import com.google.maps.errors.ApiException;
import com.google.maps.errors.OverQueryLimitException;
import com.google.maps.internal.ApiConfig;
import com.google.maps.internal.ApiResponse;
import com.google.maps.internal.ExceptionsAllowedToRetry;
import com.google.maps.internal.UrlSigner;

//Reference
// PlaceDetailsRequest query = PlacesApi.placeDetails(new GeoApiContext.Builder().apiKey(API_KEY).build(), prediction.getPrediction().placeId);
//API KEY "AIzaSyBXXka_F4Sk_WZ7pdCcS-zNatD8oLoLwJ0"



public class Directions {

    private final String API_KEY = "AIzaSyBXXka_F4Sk_WZ7pdCcS-zNatD8oLoLwJ0";

    DirectionsApiRequest dirReq = DirectionsApi.newRequest(new GeoApiContext.Builder().apiKey(API_KEY).build());


}

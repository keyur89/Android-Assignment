package com.test.assignment.Model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Keyur Tailor on 01-Nov-17.
 */

public class TravelDataModel {

    @SerializedName("id")
    int travelId;
    @SerializedName("name")
    String placeName;
    @SerializedName("fromcentral")
    FromCentral fromCentral;
    @SerializedName("location")
    PlaceLocation placeLocation;

    public int getTravelId() {
        return travelId;
    }

    public void setTravelId(int travelId) {
        this.travelId = travelId;
    }

    public String getPlaceName() {
        return placeName;
    }

    public void setPlaceName(String placeName) {
        this.placeName = placeName;
    }

    public FromCentral getFromCentral() {
        return fromCentral;
    }

    public void setFromCentral(FromCentral fromCentral) {
        this.fromCentral = fromCentral;
    }

    public PlaceLocation getPlaceLocation() {
        return placeLocation;
    }

    public void setPlaceLocation(PlaceLocation placeLocation) {
        this.placeLocation = placeLocation;
    }
}

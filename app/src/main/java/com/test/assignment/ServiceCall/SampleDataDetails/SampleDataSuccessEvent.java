package com.test.assignment.ServiceCall.SampleDataDetails;

import com.test.assignment.Model.TravelDataModel;

import java.util.ArrayList;

/**
 * Created by Keyur Tailor on 01-Nov-17.
 */

public class SampleDataSuccessEvent {
    boolean successful;
    ArrayList<TravelDataModel> travelDataModelArrayList;

    public SampleDataSuccessEvent(boolean successful, ArrayList<TravelDataModel> upcomingMoviesModel) {
        this.successful = successful;
        this.travelDataModelArrayList = upcomingMoviesModel;
    }

    public boolean isSuccessful() {
        return successful;
    }

    public ArrayList<TravelDataModel> getTravelDataModelArrayList() {
        return travelDataModelArrayList;
    }
}

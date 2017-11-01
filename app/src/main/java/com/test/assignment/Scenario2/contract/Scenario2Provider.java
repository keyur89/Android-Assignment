package com.test.assignment.Scenario2.contract;

import com.test.assignment.Model.TravelDataModel;

import java.util.ArrayList;

/**
 * Created by Keyur Tailor on 01-Nov-17.
 */

public interface Scenario2Provider {
    void showError(String message);

    void fillSpinnerData(ArrayList<TravelDataModel> travelDataModelArrayList, ArrayList<String> placeNames);

    void setDistanceData(String car, String train);

    void setNavigationData(double latitude, double longitude);
}

package com.test.assignment.Scenario2.presenter;

import com.test.assignment.ApiManager.ApiManager;
import com.test.assignment.Model.TravelDataModel;
import com.test.assignment.Scenario2.contract.Scenario2Provider;
import com.test.assignment.ServiceCall.SampleDataDetails.SampleDataDetails;

import java.util.ArrayList;

import javax.inject.Inject;

/**
 * Created by Keyur Tailor on 01-Nov-17.
 */

public class Scenario2Presenter {

    @Inject
    public Scenario2Presenter() {

    }

    public void callTravelDataDetails(ApiManager apiManager) {
        SampleDataDetails sampleDataDetails = new SampleDataDetails();
        sampleDataDetails.callSampleDataDetails(apiManager);
    }

    public void fillSpinnerData(Scenario2Provider provider, ArrayList<TravelDataModel> travelDataModelArrayList) {
        ArrayList<String> placesNames = new ArrayList<>();
        if (travelDataModelArrayList.size() > 0) {
            for (int i = 0; i < travelDataModelArrayList.size(); i++) {
                placesNames.add(travelDataModelArrayList.get(i).getPlaceName());
            }
            provider.fillSpinnerData(travelDataModelArrayList, placesNames);
        }
    }

    public void fillSelectedItemData(Scenario2Provider provider, TravelDataModel travelDataModel) {
        if (travelDataModel != null) {
            provider.setDistanceData(travelDataModel.getFromCentral().getCar(), travelDataModel.getFromCentral().getTrain());
            provider.setNavigationData(travelDataModel.getPlaceLocation().getLatitude(), travelDataModel.getPlaceLocation().getLongitude());
        }
    }
}

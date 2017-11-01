package com.test.assignment.ServiceCall.SampleDataDetails;


import com.test.assignment.ApiManager.ApiManager;
import com.test.assignment.Model.TravelDataModel;
import com.test.assignment.Utils.Injector;

import org.greenrobot.eventbus.EventBus;

import java.lang.annotation.Annotation;
import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Converter;
import retrofit2.Response;

/**
 * Created by Keyur Tailor on 01-Nov-17.
 */

public class SampleDataDetails {
    EventBus bus;

    public void callSampleDataDetails(final ApiManager apiManager) {
        bus = Injector.provideEventBus();

        Call<ArrayList<TravelDataModel>> travelDataList = apiManager.getService().getSampleData();
        travelDataList.enqueue(new Callback<ArrayList<TravelDataModel>>() {
            @Override
            public void onResponse(Call<ArrayList<TravelDataModel>> call, Response<ArrayList<TravelDataModel>> response) {
                if (response.isSuccessful()) {
                    ArrayList<TravelDataModel> travelDataModelArrayList = response.body();
                    bus.post(new SampleDataSuccessEvent(response.isSuccessful(), travelDataModelArrayList));
                } else {
                    Converter<ResponseBody, Error> errorConverter =
                            apiManager.retrofit.responseBodyConverter(Error.class, new Annotation[0]);
                    // Convert the error body into our Error type.
                    try {
                        Error error = errorConverter.convert(response.errorBody());
                        String errorMessageDisplay = error.message;
                        bus.post(new SampleDataFailureEvent(false, errorMessageDisplay));

                    } catch (Exception e) {
                        e.printStackTrace();
                        bus.post(new SampleDataFailureEvent(false, null));
                    }
                }
            }

            @Override
            public void onFailure(Call<ArrayList<TravelDataModel>> call, Throwable t) {
                bus.post(new SampleDataFailureEvent(false, null));
            }
        });

    }

    static class Error {
        String message;
    }
}

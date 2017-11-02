package com.test.assignment.Scenario2.view;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.test.assignment.ApiManager.ApiManager;
import com.test.assignment.Model.TravelDataModel;
import com.test.assignment.R;
import com.test.assignment.Scenario2.contract.Scenario2Provider;
import com.test.assignment.Scenario2.presenter.Scenario2Presenter;
import com.test.assignment.ServiceCall.SampleDataDetails.SampleDataFailureEvent;
import com.test.assignment.ServiceCall.SampleDataDetails.SampleDataSuccessEvent;
import com.test.assignment.Utils.Injector;
import com.test.assignment.Utils.Network;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;


public class Scenario2Fragment extends Fragment implements Scenario2Provider {

    Scenario2Presenter scenario2Presenter = new Scenario2Presenter();

    @Bind(R.id.rlScenario2)
    RelativeLayout rlScenario2;
    @Bind(R.id.spinnerPlaces)
    Spinner spinnerPlaces;
    @Bind(R.id.txtCar)
    TextView txtCar;
    @Bind(R.id.txtTrain)
    TextView txtTrain;
    @Bind(R.id.btnNavigate)
    Button btnNavigate;

    EventBus bus;
    ApiManager apiManager;
    ProgressDialog progressDialog;

    double latitude = 0;
    double longitude = 0;

    public Scenario2Fragment() {
    }

    public static Scenario2Fragment newInstance() {
        Scenario2Fragment fragment = new Scenario2Fragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_scenario2, container, false);
        ButterKnife.bind(this, rootView);
        setRetainInstance(true);
        apiManager = ApiManager.getInstance();
        bus = Injector.provideEventBus();
        progressDialog = new ProgressDialog(getActivity());

        if (checkIfNetworkConnected()) {
            showLoading();
            scenario2Presenter.callTravelDataDetails(apiManager);
        } else {
            hideLoading();
            showError(getResources().getString(R.string.network_error));
        }

        btnNavigate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navigateToMaps();
            }
        });
        return rootView;
    }

    @Subscribe()
    public void SampleDataSuccessEvent(SampleDataSuccessEvent event) {
        hideLoading();
        scenario2Presenter.fillSpinnerData(Scenario2Fragment.this, event.getTravelDataModelArrayList());
    }

    @Subscribe()
    public void SampleDataFailureEvent(SampleDataFailureEvent event) {
        hideLoading();
        showError(getResources().getString(R.string.unexpected_error));
    }

    @Override
    public void fillSpinnerData(final ArrayList<TravelDataModel> travelDataModelArrayList, ArrayList<String> placeNames) {
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, placeNames);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerPlaces.setAdapter(dataAdapter);
        spinnerPlaces.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                scenario2Presenter.fillSelectedItemData(Scenario2Fragment.this, travelDataModelArrayList.get(i));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    @Override
    public void setDistanceData(String car, String train) {
        if (car != null) {
            txtCar.setVisibility(View.VISIBLE);
            txtCar.setText(car);
        } else {
            txtCar.setVisibility(View.GONE);
        }
        if (train != null) {
            txtTrain.setVisibility(View.VISIBLE);
            txtTrain.setText(train);
        } else {
            txtTrain.setVisibility(View.GONE);
        }
    }

    @Override
    public void setNavigationData(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public void navigateToMaps() {
        try {
            // Creates an Intent that will load a map of Given coordinates
            Uri gmmIntentUri = Uri.parse("geo:" + latitude + "," + longitude + "?q=" + latitude + "," + longitude);
            Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
            mapIntent.setPackage("com.google.android.apps.maps");

            // Verify it resolves
            PackageManager packageManager = getActivity().getPackageManager();
            List<ResolveInfo> activities = packageManager.queryIntentActivities(mapIntent, 0);
            boolean isIntentSafe = activities.size() > 0;

            // Start an activity if it's safe
            if (isIntentSafe) {
                startActivity(mapIntent);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void showError(String message) {
        try {
            Snackbar snackbar = Snackbar.make(rlScenario2, message, Snackbar.LENGTH_SHORT);
            snackbar.show();
        } catch (Exception e) {

        }
    }

    public boolean checkIfNetworkConnected() {
        return new Network(getActivity()).getConnectivityStatus();
    }

    public void showLoading() {
        progressDialog.setMessage(getResources().getString(R.string.loading));
        progressDialog.show();
    }

    public void hideLoading() {
        if (progressDialog != null) {
            if (progressDialog.isShowing()) {
                progressDialog.dismiss();
            }
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public void onStart() {
        super.onStart();
        try {
            if (!EventBus.getDefault().isRegistered(this)) {
                bus.register(this);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        bus.unregister(this);
    }
}

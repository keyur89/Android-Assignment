package com.test.assignment.Scenario2.view;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
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
import com.test.assignment.dagger.DaggerInjector;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;


public class Scenario2Activity extends AppCompatActivity implements Scenario2Provider {

    @Inject
    Scenario2Presenter scenario2Presenter;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scenario2);
        ButterKnife.bind(this);
        DaggerInjector.get().inject(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        apiManager = ApiManager.getInstance();
        bus = Injector.provideEventBus();
        progressDialog = new ProgressDialog(this);

        if (savedInstanceState == null) {
            if (checkIfNetworkConnected()) {
                showLoading();
                scenario2Presenter.callTravelDataDetails(apiManager);
            } else {
                hideLoading();
                showError(getResources().getString(R.string.network_error));
            }
        }

        btnNavigate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navigateToMaps();
            }
        });
    }

    @Subscribe()
    public void SampleDataSuccessEvent(SampleDataSuccessEvent event) {
        hideLoading();
        scenario2Presenter.fillSpinnerData(Scenario2Activity.this, event.getTravelDataModelArrayList());
    }

    @Subscribe()
    public void SampleDataFailureEvent(SampleDataFailureEvent event) {
        hideLoading();
        showError(getResources().getString(R.string.unexpected_error));
    }

    @Override
    public void fillSpinnerData(final ArrayList<TravelDataModel> travelDataModelArrayList, ArrayList<String> placeNames) {
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, placeNames);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerPlaces.setAdapter(dataAdapter);
        spinnerPlaces.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                scenario2Presenter.fillSelectedItemData(Scenario2Activity.this, travelDataModelArrayList.get(i));
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
            PackageManager packageManager = getPackageManager();
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
        return new Network(this).getConnectivityStatus();
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
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
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
    protected void onStop() {
        bus.unregister(this);
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}

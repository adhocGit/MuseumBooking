package com.museumbooking.activity;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.museumbooking.R;
import com.museumbooking.adapter.MuseumDetailsAdapter;
import com.museumbooking.conf.WebserviceConstants;
import com.museumbooking.model.DistrictModel;
import com.museumbooking.model.MuseumDetailsModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TrackLocationActivity extends BaseActivity implements BaseActivity.ServiceCompleted{
    private ProgressDialog pDialog;
    private Spinner districtSpinner;
    private ArrayAdapter<String> districtAdapter;
    private ArrayList<String> districtArray = new ArrayList<>();
    private String districtSelected;
    private ListView detailsList;
    List<MuseumDetailsModel> museumDetailsModelList=new ArrayList<>();
    private MuseumDetailsAdapter museumDetailsAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_track_location);
        setView();
        invokeDistrictDetailsService();
    }
    private void setView()
    {
        districtSpinner = (Spinner) findViewById(R.id.districtSpinner);
        // Creating adapter for spinner
        districtAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, districtArray);
        // Drop down layout style - list view with radio button
        districtAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // attaching data adapter to spinner
        districtSpinner.setAdapter(districtAdapter);
        districtSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                districtSelected=districtArray.get(position);
                invokegetMuseumDetailsService();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        detailsList= (ListView) findViewById(R.id.detailsList);
        museumDetailsAdapter=new MuseumDetailsAdapter(this,museumDetailsModelList);
        detailsList.setAdapter(museumDetailsAdapter);
    }

    private void invokeDistrictDetailsService() {
        pDialog = new ProgressDialog(TrackLocationActivity.this);
        pDialog.setMessage("Loading...");
        pDialog.show();
        jsonArrayRequest(getUrl(WebserviceConstants.DISTRICT_DETAILS_SERVICE,populateParameter()),this,WebserviceConstants.DISTRICT_DETAILS_SERVICE);


    }

    /**
     * Populating parameters to pass in service
     *
     * @return
     */
    private Map<String, String> populateParameter() {
        Map<String, String> urlParams = new HashMap<String, String>();
        urlParams.put("input", "0");
        return urlParams;
    }

    @Override
    public void ServiceCompleted(String jsonresponse,String serviceURL) {
        if (jsonresponse != null) {
            if (serviceURL.equalsIgnoreCase(WebserviceConstants.DISTRICT_DETAILS_SERVICE)) {
                districtArray.clear();
                GsonBuilder gsonBuilder = new GsonBuilder();
                Gson gson = gsonBuilder.create();
                List<DistrictModel> postsList = Arrays.asList(gson.fromJson(jsonresponse,
                        DistrictModel[].class));
                for (int i = 0; i < postsList.size(); i++) {
                    districtArray.add(postsList.get(i).getDistrict());
                }
                districtAdapter.notifyDataSetChanged();
            }
            else
            {
                museumDetailsModelList.clear();
                GsonBuilder gsonBuilder = new GsonBuilder();
                Gson gson = gsonBuilder.create();
                museumDetailsModelList.addAll(Arrays.asList(gson.fromJson(jsonresponse,
                        MuseumDetailsModel[].class)));
                museumDetailsAdapter.notifyDataSetChanged();
            }

            pDialog.dismiss();
        } else {
            pDialog.dismiss();
        }
    }


    private void invokegetMuseumDetailsService() {
        pDialog = new ProgressDialog(TrackLocationActivity.this);
        pDialog.setMessage("Loading...");
        pDialog.show();
        jsonArrayRequest(getUrl(WebserviceConstants.GET_MUSEUM_DETAILS_SERVICE,populateMuseumDetailsParameter()),this,WebserviceConstants.GET_MUSEUM_DETAILS_SERVICE);
    }
    /**
     * Populating parameters to pass in service
     *
     * @return
     */
    private Map<String, String> populateMuseumDetailsParameter() {
        Map<String, String> urlParams = new HashMap<String, String>();
        urlParams.put("district", districtSelected);
        return urlParams;
    }
}

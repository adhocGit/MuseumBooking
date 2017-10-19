package com.museumbooking.activity;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.museumbooking.R;
import com.museumbooking.adapter.MuseumDetailsAdapter;
import com.museumbooking.adapter.ReservationListAdapter;
import com.museumbooking.conf.Constants;
import com.museumbooking.conf.WebserviceConstants;
import com.museumbooking.model.DistrictModel;
import com.museumbooking.model.MuseumDetailsModel;
import com.museumbooking.model.ReservationModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReservationDetailsActivity extends BaseActivity implements BaseActivity.ServiceCompleted{
    private ListView detailsList;
    private ReservationListAdapter museumDetailsAdapter;
    private ProgressDialog pDialog;
    List<ReservationModel> resrevationDetailsModelList=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation_details);
        setView();
        invokeReservationDetailsService();
    }
    private void setView()
    {
        detailsList= (ListView) findViewById(R.id.detailsList);
        museumDetailsAdapter=new ReservationListAdapter(this,resrevationDetailsModelList);
        detailsList.setAdapter(museumDetailsAdapter);
    }
    private void invokeReservationDetailsService() {
        pDialog = new ProgressDialog(ReservationDetailsActivity.this);
        pDialog.setMessage("Loading...");
        pDialog.show();
        jsonArrayRequest(getUrl(WebserviceConstants.GET_TICKET_RESERVATION_DETAILS_SERVICE,populateParameter()),this,WebserviceConstants.GET_TICKET_RESERVATION_DETAILS_SERVICE);


    }

    /**
     * Populating parameters to pass in service
     *
     * @return
     */
    private Map<String, String> populateParameter() {
        Map<String, String> urlParams = new HashMap<String, String>();
        urlParams.put("userid", getPreferences().getString(Constants.LOGGED_IN_USERID,""));
        return urlParams;
    }

    @Override
    public void ServiceCompleted(String jsonresponse, String serviceURL) {
        if (jsonresponse != null) {
            if (serviceURL.equalsIgnoreCase(WebserviceConstants.GET_TICKET_RESERVATION_DETAILS_SERVICE)) {
                resrevationDetailsModelList.clear();
                GsonBuilder gsonBuilder = new GsonBuilder();
                Gson gson = gsonBuilder.create();
                resrevationDetailsModelList.addAll(Arrays.asList(gson.fromJson(jsonresponse,
                        ReservationModel[].class)));
                museumDetailsAdapter.notifyDataSetChanged();
            }
        }
        pDialog.dismiss();
    }
}

package com.museumbooking.activity;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.museumbooking.R;
import com.museumbooking.conf.Constants;
import com.museumbooking.conf.WebserviceConstants;
import com.museumbooking.model.DistrictModel;
import com.museumbooking.model.MuseumNameModel;
import com.museumbooking.model.MuseumTypeModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TicketReservation extends BaseActivity implements BaseActivity.ServiceCompleted, View.OnClickListener {
    private EditText dateOfReservation,totalPerson;
    private int mYear, mMonth, mDay;
    private ProgressDialog pDialog;
    private Spinner museumTypeSpinner, museumNameSpinner;
    private String museumType,museumName,dateVal,numberOfPerson;
    private ArrayList<String> museumTypeArray = new ArrayList<>();
    private ArrayList<String> museumNameArray = new ArrayList<>();
    private ArrayAdapter<String> museumTypeAdapter,museumNameAdapter;
    private Button reserveButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket_reservation);
        setView();
        invokeMuseumTypeService();
    }

    private void setView() {
        reserveButton= (Button) findViewById(R.id.reserveButton);
        reserveButton.setOnClickListener(this);
        dateOfReservation = (EditText) findViewById(R.id.dateOfReservation);
        dateOfReservation.setOnClickListener(this);
        totalPerson= (EditText) findViewById(R.id.totalPerson);
        museumTypeSpinner = (Spinner) findViewById(R.id.museumTypeSpinner);
        museumNameSpinner = (Spinner) findViewById(R.id.museumNameSpinner);
        // Creating adapter for spinner
        museumTypeAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, museumTypeArray);
        // Drop down layout style - list view with radio button
        museumTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // attaching data adapter to spinner
        museumTypeSpinner.setAdapter(museumTypeAdapter);
        museumTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                museumType=museumTypeArray.get(position);
                invokeMuseumNameService();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        // Creating adapter for spinner
        museumNameAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, museumNameArray);
        // Drop down layout style - list view with radio button
        museumNameAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // attaching data adapter to spinner
        museumNameSpinner.setAdapter(museumNameAdapter);
        museumNameSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                museumName=museumNameArray.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.dateOfReservation:
                showDatePicker();
                break;
            case R.id.reserveButton:
                onReservceClick();
                break;



        }
    }

    private void showDatePicker() {
        // Get Current Date
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);


        DatePickerDialog datePickerDialog = new DatePickerDialog(TicketReservation.this,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {

                        dateOfReservation.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.show();
    }

    private void invokeMuseumNameService() {
        pDialog = new ProgressDialog(TicketReservation.this);
        pDialog.setMessage("Loading...");
        pDialog.show();
        jsonArrayRequest(getUrl(WebserviceConstants.MUSEUM_NAME_SERVICE, populateMuseumNameParameter()), this, WebserviceConstants.MUSEUM_NAME_SERVICE);
    }

    private void invokeMuseumTypeService() {
        pDialog = new ProgressDialog(TicketReservation.this);
        pDialog.setMessage("Loading...");
        pDialog.show();
        jsonArrayRequest(getUrl(WebserviceConstants.MUSEUM_NAME_TYPE, populateParameter()), this, WebserviceConstants.MUSEUM_NAME_TYPE);
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
    /**
     * Populating parameters to pass in service
     *
     * @return
     */
    private Map<String, String> populateMuseumNameParameter() {
        Map<String, String> urlParams = new HashMap<String, String>();
        urlParams.put("museumtype", museumType);
        return urlParams;
    }

    @Override
    public void ServiceCompleted(String jsonresponse, String serviceURL) {
        if (jsonresponse != null) {
            if (serviceURL.equalsIgnoreCase(WebserviceConstants.MUSEUM_NAME_TYPE)) {
                museumTypeArray.clear();
                GsonBuilder gsonBuilder = new GsonBuilder();
                Gson gson = gsonBuilder.create();
                List<MuseumTypeModel> postsList = Arrays.asList(gson.fromJson(jsonresponse,
                        MuseumTypeModel[].class));
                for (int i = 0; i < postsList.size(); i++) {
                    museumTypeArray.add(postsList.get(i).getMuseumType());
                }
                museumTypeAdapter.notifyDataSetChanged();

                pDialog.dismiss();
            } else if (serviceURL.equalsIgnoreCase(WebserviceConstants.MUSEUM_NAME_SERVICE)){
                museumNameArray.clear();
                GsonBuilder gsonBuilder = new GsonBuilder();
                Gson gson = gsonBuilder.create();
                List<MuseumNameModel> postsList = Arrays.asList(gson.fromJson(jsonresponse,
                        MuseumNameModel[].class));
                for (int i = 0; i < postsList.size(); i++) {
                    museumNameArray.add(postsList.get(i).getMuseum_name());
                }
                museumNameAdapter.notifyDataSetChanged();
                pDialog.dismiss();
            }
            else
            {
                try {
                    JSONArray jsonArray = new JSONArray(jsonresponse);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObbj = (JSONObject) jsonArray.get(i);
                        String msg = jsonObbj.optString("msg");
                        if (msg.equalsIgnoreCase("Ticket reserved successfully")) {
                            showToast(msg);
                            finish();
                        } else {
                            showToast(msg);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }


        } else {
            pDialog.dismiss();
        }
    }

    private void onReservceClick()
    {
        dateVal=dateOfReservation.getText().toString().trim();
        numberOfPerson=totalPerson.getText().toString().trim();
        invokeTicketReserveService();
    }


    private void invokeTicketReserveService() {
        pDialog = new ProgressDialog(TicketReservation.this);
        pDialog.setMessage("Loading...");
        pDialog.show();
        jsonArrayRequest(getUrl(WebserviceConstants.TICKET_RESREVATION_SERVICE, populateTicketReserveParameter()), this, WebserviceConstants.TICKET_RESREVATION_SERVICE);
    }
    /**
     * Populating parameters to pass in service
     *
     * @return
     */
    private Map<String, String> populateTicketReserveParameter() {
        Map<String, String> urlParams = new HashMap<String, String>();
        urlParams.put("museumtype", museumType);
        urlParams.put("museumname", museumName);
        urlParams.put("date", dateVal);
        urlParams.put("totalperson", numberOfPerson);
        urlParams.put("userid", getPreferences().getString(Constants.LOGGED_IN_USERID,""));
        return urlParams;
    }
}

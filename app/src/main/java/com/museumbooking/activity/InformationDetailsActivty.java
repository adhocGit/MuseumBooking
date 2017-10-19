package com.museumbooking.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.zxing.integration.android.IntentIntegrator;
import com.museumbooking.R;
import com.museumbooking.conf.Constants;
import com.museumbooking.conf.WebserviceConstants;
import com.museumbooking.model.InformationScanModel;
import com.museumbooking.model.PaymentDetailsModel;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InformationDetailsActivty extends BaseActivity implements View.OnClickListener, BaseActivity.ServiceCompleted {
    Button scanButton;
    private static final int request = 123;
    private String scanVal;
    private ProgressDialog pDialog;
    List<InformationScanModel> scanInfoDetailsModelList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information_details_activty);
        setView();
    }

    private void setView() {
        scanButton = (Button) findViewById(R.id.scanButton);
        scanButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.scanButton:
                onScanClick();
                break;

        }
    }

    private void onScanClick() {
        IntentIntegrator integrator = new IntentIntegrator(InformationDetailsActivty.this);
        //integrator.setDesiredBarcodeFormats(IntentIntegrator.PRODUCT_CODE_TYPES);
        integrator.setPrompt("Scan");
        integrator.setCameraId(0);
        integrator.setBeepEnabled(true);
        Intent intent = integrator.createScanIntent();
        startActivityForResult(intent, request);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != RESULT_OK)
            return;


        switch (requestCode) {

            case request: {
                if (resultCode == RESULT_OK) {
                    String contents = data.getStringExtra("SCAN_RESULT");
                    scanVal = contents;
                    String format = data.getStringExtra("SCAN_RESULT_FORMAT");
                    Toast toast = Toast.makeText(getApplicationContext(), "Content:" + contents + " Format:" + format, Toast.LENGTH_LONG);
                    invokeQRInfoService();
                    toast.show();
                } else if (resultCode == RESULT_CANCELED) {

                    // user cancelled Image capture
                    Toast.makeText(getApplicationContext(),
                            "You cancelled the scanning", Toast.LENGTH_SHORT).show();

                } else {
                    // failed to capture image
                    Toast.makeText(getApplicationContext(),
                            "Sorry! Scanning Failed", Toast.LENGTH_SHORT)
                            .show();
                }

            }

        }
    }

    private void invokeQRInfoService() {
        pDialog = new ProgressDialog(InformationDetailsActivty.this);
        pDialog.setMessage("Loading...");
        pDialog.show();
        jsonArrayRequest(getUrl(WebserviceConstants.GET_QR_INFO_SERVICE, populateParameter()), this, WebserviceConstants.GET_QR_INFO_SERVICE);


    }

    /**
     * Populating parameters to pass in service
     *
     * @return
     */
    private Map<String, String> populateParameter() {
        Map<String, String> urlParams = new HashMap<String, String>();
        urlParams.put("scanid", scanVal);
        urlParams.put("userid", getPreferences().getString(Constants.LOGGED_IN_USERID, ""));
        return urlParams;
    }

    @Override
    public void ServiceCompleted(String jsonresponse, String serviceURL) {
        if (jsonresponse != null) {
            showToast(jsonresponse);
            if (serviceURL.equalsIgnoreCase(WebserviceConstants.GET_QR_INFO_SERVICE)) {
                scanInfoDetailsModelList.clear();
                GsonBuilder gsonBuilder = new GsonBuilder();
                Gson gson = gsonBuilder.create();
                scanInfoDetailsModelList.addAll(Arrays.asList(gson.fromJson(jsonresponse,
                        InformationScanModel[].class)));
                if (scanInfoDetailsModelList.size() > 0) {
                    showToast(scanInfoDetailsModelList.get(0).getPaymentstatus());
                    if (scanInfoDetailsModelList.get(0).getPaymentstatus().equalsIgnoreCase("paid")) {
                        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(getFileUrl(scanInfoDetailsModelList.get(0).getFile())));
                        startActivity(browserIntent);

                    } else {
                        showToast("Payment Not Done");
                        startActivity(new Intent(InformationDetailsActivty.this, ReservationDetailsActivity.class));
                    }
                }
            }
        }
        pDialog.dismiss();
    }

    private String getFileUrl(String fileName) {
        String url = "http";
        url = (new StringBuilder(url).append("://")
                .append(getServerAddress()).append("/").append(WebserviceConstants.IMAGE_PATH).append("/").append(fileName)).toString();
        return url;
    }
}

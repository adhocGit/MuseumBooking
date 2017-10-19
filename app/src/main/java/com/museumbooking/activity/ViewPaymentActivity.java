package com.museumbooking.activity;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.museumbooking.R;
import com.museumbooking.adapter.PaymentListAdapter;
import com.museumbooking.adapter.ReservationListAdapter;
import com.museumbooking.conf.Constants;
import com.museumbooking.conf.WebserviceConstants;
import com.museumbooking.model.PaymentDetailsModel;
import com.museumbooking.model.ReservationModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ViewPaymentActivity extends BaseActivity implements BaseActivity.ServiceCompleted {
    private ListView detailsList;
    private PaymentListAdapter paymentDetailsAdapter;
    private ProgressDialog pDialog;
    List<PaymentDetailsModel> paymentDetailsModelList=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_payment);
        setView();
        invokePaymentDetailsService();
    }
    private void setView()
    {
        detailsList= (ListView) findViewById(R.id.detailsList);
        paymentDetailsAdapter=new PaymentListAdapter(this,paymentDetailsModelList);
        detailsList.setAdapter(paymentDetailsAdapter);
    }
    private void invokePaymentDetailsService() {
        pDialog = new ProgressDialog(ViewPaymentActivity.this);
        pDialog.setMessage("Loading...");
        pDialog.show();
        jsonArrayRequest(getUrl(WebserviceConstants.VIEW_PAYMENT_SERVICE,populateParameter()),this,WebserviceConstants.VIEW_PAYMENT_SERVICE);


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
            if (serviceURL.equalsIgnoreCase(WebserviceConstants.VIEW_PAYMENT_SERVICE)) {
                paymentDetailsModelList.clear();
                GsonBuilder gsonBuilder = new GsonBuilder();
                Gson gson = gsonBuilder.create();
                paymentDetailsModelList.addAll(Arrays.asList(gson.fromJson(jsonresponse,
                        PaymentDetailsModel[].class)));
                paymentDetailsAdapter.notifyDataSetChanged();
            }
        }
        pDialog.dismiss();
    }
}

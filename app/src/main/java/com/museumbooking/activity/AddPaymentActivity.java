package com.museumbooking.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.museumbooking.R;
import com.museumbooking.conf.Constants;
import com.museumbooking.conf.WebserviceConstants;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class AddPaymentActivity extends BaseActivity implements View.OnClickListener,BaseActivity.ServiceCompleted {
    private EditText name,museumName,museumType,visitDate,accountHolderName,bankName,pinNo,accountNo,amount;
    private String nameVal,museumNameVal,museumTypeVal,visitDateVal,accountHolderNameVal,bankNameVal,bankTypeVal,pinNoVal,accountNoVal,amountVal,reservation_id;
    private Button paymentButton;
    private ProgressDialog pDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_payment);
        setView();
        setData();
    }
    private void setView()
    {
        paymentButton= (Button) findViewById(R.id.paymentButton);
        paymentButton.setOnClickListener(this);
        name= (EditText) findViewById(R.id.name);
        museumName= (EditText) findViewById(R.id.museumName);
        museumType= (EditText) findViewById(R.id.museumType);
        visitDate= (EditText) findViewById(R.id.visitDate);
        accountHolderName= (EditText) findViewById(R.id.accountHolderName);
        bankName= (EditText) findViewById(R.id.bankName);
        pinNo= (EditText) findViewById(R.id.pinNo);
        accountNo= (EditText) findViewById(R.id.accountNo);
        amount= (EditText) findViewById(R.id.amount);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.paymentButton:
                onPaymentButtonClick();
                break;
        }
    }
    private void onPaymentButtonClick()
    {
        nameVal=name.getText().toString().trim();
        museumNameVal=museumName.getText().toString().trim();
        museumTypeVal=museumType.getText().toString().trim();
        visitDateVal=visitDate.getText().toString().trim();
        accountHolderNameVal=accountHolderName.getText().toString().trim();
        bankNameVal=bankName.getText().toString().trim();
        nameVal=name.getText().toString().trim();
        pinNoVal=pinNo.getText().toString().trim();
        bankTypeVal="SAVINGS";
        accountNoVal=accountNo.getText().toString().trim();
        amountVal=amount.getText().toString().trim();
        invokeAddPaymentService();
    }
    private void invokeAddPaymentService() {
        pDialog = new ProgressDialog(AddPaymentActivity.this);
        pDialog.setMessage("Loading...");
        pDialog.show();
        jsonArrayRequest(getUrl(WebserviceConstants.ADD_PAYMENT_SERVICE, populatefeedbackServiceParameter()), this, WebserviceConstants.ADD_PAYMENT_SERVICE);
    }
    /**
     * Populating parameters to pass in service
     *
     * @return
     */
    private Map<String, String> populatefeedbackServiceParameter() {
        Map<String, String> urlParams = new HashMap<String, String>();
        urlParams.put("name", getPreferences().getString(Constants.LOGGED_IN_USERNAME,""));
        urlParams.put("museumtype", museumTypeVal);
        urlParams.put("museumname", museumNameVal);
        urlParams.put("acountholdername", accountHolderNameVal);
        urlParams.put("bankname", bankNameVal);
        urlParams.put("type", bankTypeVal);
        urlParams.put("pinno", pinNoVal);
        urlParams.put("accountno", accountNoVal);
        urlParams.put("amount", amountVal);
        urlParams.put("visitdate", visitDateVal);
        urlParams.put("reservation_id", reservation_id);
        urlParams.put("userid", getPreferences().getString(Constants.LOGGED_IN_USERID,""));
        return urlParams;
    }
    private void setData()
    {
        if(null!=getIntent())
        {
            name.setText("Name: "+getPreferences().getString(Constants.LOGGED_IN_USERNAME,""));
            name.setEnabled(false);
            museumName.setText("Museum Name: "+getIntent().getStringExtra("museum_name"));
            museumName.setEnabled(false);
            museumType.setText("Museum Type: "+getIntent().getStringExtra("museum_type"));
            museumType.setEnabled(false);
            visitDate.setText("Visit Date: "+getIntent().getStringExtra("date"));
            visitDate.setEnabled(false);
            amount.setText("Amount: 50");
            amount.setEnabled(false);
            reservation_id=getIntent().getStringExtra("resrvation_id");
        }
    }

    @Override
    public void ServiceCompleted(String jsonresponse, String serviceURL) {
        if (jsonresponse != null) {
            if (serviceURL.equalsIgnoreCase(WebserviceConstants.ADD_PAYMENT_SERVICE)) {
                try {
                    pDialog.dismiss();
                    JSONArray jsonArray = new JSONArray(jsonresponse);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObbj = (JSONObject) jsonArray.get(i);
                        String msg = jsonObbj.optString("msg");
                        if (msg.equalsIgnoreCase("payment successfully")) {
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
        }
    }
}

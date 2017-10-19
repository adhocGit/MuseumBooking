package com.museumbooking.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.museumbooking.R;
import com.museumbooking.conf.Constants;
import com.museumbooking.conf.WebserviceConstants;
import com.museumbooking.model.DistrictModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LoginActivity extends BaseActivity implements View.OnClickListener, BaseActivity.ServiceCompleted {
    private TextView registerTV;
    EditText username, password,enterIP;
    Button loginButton,submitButton;
    private ProgressDialog pDialog;
    String user, pass,ipaddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
    }

    private void initView() {
        enterIP= (EditText) findViewById(R.id.enterIP);
        registerTV = (TextView) findViewById(R.id.register);
        registerTV.setOnClickListener(this);
        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        loginButton = (Button) findViewById(R.id.loginButton);
        loginButton.setOnClickListener(this);
        submitButton= (Button) findViewById(R.id.submitButton);
        submitButton.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.register:
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class).putExtra("page","register"));
                break;
            case R.id.loginButton:
                onLoginClick();
                break;
            case R.id.submitButton:
                onSubmitClick();
                break;
        }
    }
    private void onSubmitClick() {
        ipaddress=enterIP.getText().toString().trim();
        addString(Constants.LOGGED_IN_IPADDRESS,ipaddress);

    }
    private void onLoginClick() {
        user = username.getText().toString();
        pass = password.getText().toString();

        if (user.equals("")) {
            username.setError("can't be blank");
        } else if (!Patterns.EMAIL_ADDRESS.matcher(user).matches()) {
            username.setError("please enter valid email");
        } else if (pass.equals("")) {
            password.setError("can't be blank");
        } else {

            invokeLoginService();


        }

    }

    private void invokeLoginService() {
        pDialog = new ProgressDialog(LoginActivity.this);
        pDialog.setMessage("Loading...");
        pDialog.show();
        jsonArrayRequest(getUrl(WebserviceConstants.LOGIN_SERVICE, populateParameter()), this,WebserviceConstants.LOGIN_SERVICE);


    }

    /**
     * Populating parameters to pass in service
     *
     * @return
     */
    private Map<String, String> populateParameter() {
        Map<String, String> urlParams = new HashMap<String, String>();
        urlParams.put("username", user);
        urlParams.put("password", pass);
        return urlParams;
    }

    @Override
    public void ServiceCompleted(String jsonresponse,String serviceURL) {
        if (jsonresponse != null) {
            String msg = null;
            String userid = null;
            try {
                JSONArray jsonArray = new JSONArray(jsonresponse);
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObbj = (JSONObject) jsonArray.get(i);
                    msg = jsonObbj.optString("msg");
                    userid = jsonObbj.optString("userid");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (msg != null && msg.equalsIgnoreCase("success")) {
                addString(Constants.LOGGED_IN_USERID, userid);
                addString(Constants.LOGGED_IN_USERNAME, user);
                startActivity(new Intent(LoginActivity.this, HomeActivity.class));
            } else {
                showToast("Failure: " + msg);
            }
            pDialog.dismiss();
        } else {
            pDialog.dismiss();
        }
    }
}

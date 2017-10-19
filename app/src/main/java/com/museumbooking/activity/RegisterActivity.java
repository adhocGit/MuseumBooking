package com.museumbooking.activity;

import android.app.ProgressDialog;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.museumbooking.R;
import com.museumbooking.conf.Constants;
import com.museumbooking.conf.WebserviceConstants;
import com.museumbooking.model.MuseumNameModel;
import com.museumbooking.model.UserProfileModel;


import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RegisterActivity extends BaseActivity implements BaseActivity.ServiceCompleted {
    EditText name, age, address, pincode, district, state, nationality, phone, email, username, password;
    Button registerButton;
    String user, pass, nameVal, ageVal, genderVal, addressVal, pincodeVal, districtVal, stateVal, nationalityVal, emailVal, phoneVal;
    private ProgressDialog pDialog;
    private RadioGroup radioSexGroup;
    private RadioButton radioSexButton,radioMale,radioFemale;
    private String pageName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        setView();
    }

    private void setView() {
        if(null!=getIntent())
        {
            pageName= getIntent().getStringExtra("page");
        }
        radioSexGroup = (RadioGroup) findViewById(R.id.radioSex);
        radioMale= (RadioButton) findViewById(R.id.radioMale);
        radioFemale= (RadioButton) findViewById(R.id.radioFemale);
        name = (EditText) findViewById(R.id.name);
        age = (EditText) findViewById(R.id.age);
        address = (EditText) findViewById(R.id.address);
        pincode = (EditText) findViewById(R.id.pincode);
        district = (EditText) findViewById(R.id.district);
        state = (EditText) findViewById(R.id.state);
        nationality = (EditText) findViewById(R.id.nationality);
        email = (EditText) findViewById(R.id.email);
        phone = (EditText) findViewById(R.id.phone);
        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        registerButton = (Button) findViewById(R.id.registerButton);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int selectedId = radioSexGroup.getCheckedRadioButtonId();
                radioSexButton = (RadioButton) findViewById(selectedId);
                genderVal = radioSexButton.getText().toString().trim();
                user = username.getText().toString().trim();
                pass = password.getText().toString().trim();
                ageVal = age.getText().toString().trim();
                addressVal = address.getText().toString().trim();
                pincodeVal = pincode.getText().toString().trim();
                districtVal = district.getText().toString().trim();
                stateVal = state.getText().toString().trim();
                nationalityVal = nationality.getText().toString().trim();
                emailVal = email.getText().toString().trim();
                nameVal = name.getText().toString().trim();
                phoneVal = phone.getText().toString().trim();
                if (user.equals("")) {
                    username.setError("can't be blank");
                } else if (pass.equals("")) {
                    password.setError("can't be blank");
                } else if (!Patterns.EMAIL_ADDRESS.matcher(user).matches()) {
                    username.setError("please enter valid email");
                } else if (user.length() < 5) {
                    username.setError("at least 5 characters long");
                } else if (pass.length() < 5) {
                    password.setError("at least 5 characters long");
                } else {
                    if (haveInternet()) {
                        invokeRegisterService();
                    } else {
                        displayUserErrorMessage(null, Constants.NETWORK_ERROR_IO_EXCEPTION, RegisterActivity.this, null);
                    }

                }
            }
        });
        if(pageName.equalsIgnoreCase("profile"))
        {
            invokeProfileService();
        }
    }
    private void invokeProfileService() {
        pDialog = new ProgressDialog(RegisterActivity.this);
        pDialog.setMessage("Loading...");
        pDialog.show();
        jsonArrayRequest(getUrl(WebserviceConstants.VIEW_PROFILE_SERVICE, populateProfileParameter()), this,WebserviceConstants.VIEW_PROFILE_SERVICE);

    }

    /**
     * Populating parameters to pass in service
     *
     * @return
     */
    private Map<String, String> populateProfileParameter() {
        Map<String, String> urlParams = new HashMap<String, String>();
        urlParams.put("userid", getPreferences().getString(Constants.LOGGED_IN_USERID,""));
        return urlParams;
    }
    private void invokeRegisterService() {
        pDialog = new ProgressDialog(RegisterActivity.this);
        pDialog.setMessage("Loading...");
        pDialog.show();
        jsonArrayRequest(getUrl(WebserviceConstants.REGISTER_SERVICE, populateParameter()), this,WebserviceConstants.REGISTER_SERVICE);

    }

    /**
     * Populating parameters to pass in service
     *
     * @return
     */
    private Map<String, String> populateParameter() {
        Map<String, String> urlParams = new HashMap<String, String>();
        urlParams.put("name", nameVal);
        urlParams.put("age", ageVal);
        urlParams.put("gender", genderVal);
        urlParams.put("address", addressVal);
        urlParams.put("pincode", pincodeVal);
        urlParams.put("district", districtVal);
        urlParams.put("nationality", nationalityVal);
        urlParams.put("phoneno", phoneVal);
        urlParams.put("email", emailVal);
        urlParams.put("username", user);
        urlParams.put("password", pass);
        return urlParams;
    }

    @Override
    public void ServiceCompleted(String jsonresponse,String serviceURL) {
        if (jsonresponse != null) {
            if (serviceURL.equalsIgnoreCase(WebserviceConstants.REGISTER_SERVICE)) {
                if (jsonresponse.equalsIgnoreCase("registerd successfully")) {
                    showToast(jsonresponse);
                    finish();
                } else {
                    showToast(jsonresponse);
                }
            }
            else
            {
                GsonBuilder gsonBuilder = new GsonBuilder();
                Gson gson = gsonBuilder.create();
                List<UserProfileModel> postsList = Arrays.asList(gson.fromJson(jsonresponse,
                        UserProfileModel[].class));
                name.setText(postsList.get(0).getName());
                age.setText(postsList.get(0).getAge());
                address.setText(postsList.get(0).getAddress());
                pincode.setText(postsList.get(0).getPincode());
                district.setText(postsList.get(0).getDistrict());
                nationality.setText(postsList.get(0).getNationality());
                phone.setText(postsList.get(0).getPhoneno());
                phone.setEnabled(false);
                email.setText(postsList.get(0).getEmail());
                email.setEnabled(false);
                username.setText(postsList.get(0).getUsername());
                username.setEnabled(false);
                password.setText(postsList.get(0).getPassword());
                if(postsList.get(0).getGender().equalsIgnoreCase("male"))
                {
                    radioMale.setChecked(true);
                }
                else
                {
                    radioFemale.setChecked(true);
                }

                registerButton.setText("UPDATE");

            }
            pDialog.dismiss();
        } else {
            pDialog.dismiss();
        }

    }
}

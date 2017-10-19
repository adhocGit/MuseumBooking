package com.museumbooking.activity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.google.android.gms.location.LocationListener;
import com.museumbooking.R;
import com.museumbooking.conf.Constants;
import com.museumbooking.conf.WebserviceConstants;
import com.museumbooking.volleyservice.AppController;
import com.museumbooking.volleyservice.WebserviceExecutor;

import org.json.JSONArray;

import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


public class BaseActivity extends AppCompatActivity implements
        View.OnClickListener {

    private BaseActivity activity;
    public String jsonresponse = null;
    private ServiceCompleted mCallback;
    Double currentLongitude,currentLatitude;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
//    LocationManager lm = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

//        lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 2000, 10, locationListener);
    }

    @Override
    protected void onResume() {

        super.onResume();
    }


    @Override
    public void onBackPressed() {

        super.onBackPressed();
    }

    private final LocationListener locationListener = new LocationListener() {
        public void onLocationChanged(Location location) {
            currentLongitude = location.getLongitude();
            currentLatitude = location.getLatitude();
        }
    };
    @Override
    protected void onPause() {


        super.onPause();
    }


    public void setActivity(BaseActivity activity) {
        this.activity = activity;
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
        }

    }

    public void showErrorMessage(VolleyError error) {
        if (error instanceof TimeoutError || error instanceof NoConnectionError) {
            displayUserErrorMessage(null, Constants.CONNECTION_ERROR_IO_EXCEPTION, BaseActivity.this, null);
        } else if (error instanceof AuthFailureError) {
            displayUserErrorMessage(null, Constants.AUTH_FAILURE_ERROR_IO_EXCEPTION, BaseActivity.this, null);
        } else if (error instanceof ServerError) {
            displayUserErrorMessage(null, Constants.SERVER_ERROR_IO_EXCEPTION, BaseActivity.this, null);
        } else if (error instanceof NetworkError) {
            displayUserErrorMessage(null, Constants.NETWORK_ERROR_IO_EXCEPTION, BaseActivity.this, null);
        } else if (error instanceof ParseError) {
        }
    }

    public static void displayUserErrorMessage(String title, String message,
                                               final Context context, final Intent intent) {
        String COMMON_ERROR_TITLE = "Sorry";
        final String lblPositiveButton = "OK";
        final String errorTitle = COMMON_ERROR_TITLE;
        String dialogHeading = "";
        if (title != null && title.trim().length() > 0) {
            dialogHeading = title;
        } else {
            dialogHeading = errorTitle;
        }
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        int width = metrics.widthPixels;

        final Dialog errorDialog = new Dialog(context,
                R.style.AppCompatAlertDialogStyle);
        errorDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        errorDialog.setContentView(R.layout.alert_dialog);
        errorDialog.setCancelable(false);

        TextView headingTV = (TextView) errorDialog.findViewById(R.id.heading);
        TextView messageTV = (TextView) errorDialog.findViewById(R.id.message);
        Button mAgreeButton = (Button) errorDialog.findViewById(R.id.btnAgree);
        Button mDisagreeButton = (Button) errorDialog
                .findViewById(R.id.btnDisagree);

        headingTV.setText(dialogHeading);
        messageTV.setText(message);
        mAgreeButton.setText(lblPositiveButton);
        mDisagreeButton.setVisibility(View.GONE);
        errorDialog.getWindow().setLayout((6 * width) / 7,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        try {
            errorDialog.show();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        mAgreeButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                errorDialog.dismiss();
                if (intent != null) {
                    context.startActivity(intent);
                }
            }
        });


    }

    protected boolean haveInternet() {
        NetworkInfo info = ((ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE))
                .getActiveNetworkInfo();
        if (info == null || !info.isConnected()) {
            return false;
        }
        if (info.isRoaming()) {
            // here is the roaming option you can change it if you want to
            // disable internet while roaming, just return false
            return true;
        }
        return true;
    }
    public String getUrl(String requestUrl,Map<String, String> urlParamsMap)
    {
        String url = "http";
        url = (new StringBuilder(url).append("://")
                .append(getServerAddress()).append("/").append(WebserviceConstants.URL_PATH).append("/").append(requestUrl)).toString();
        StringBuffer urlBuffer = null;
        String formedUrl = null;
        if (null != urlParamsMap && !urlParamsMap.isEmpty()) {
            urlBuffer = new StringBuffer(url);
            final Iterator<Map.Entry<String, String>> urlParamsIterator = urlParamsMap.entrySet().iterator();
            while (urlParamsIterator.hasNext()) {
                final Map.Entry<String, String> mapEnt = (Map.Entry<String, String>) urlParamsIterator.next();
                urlBuffer.append(mapEnt.getKey());
                urlBuffer.append(WebserviceConstants.EQUALS_SYMBOL);
                urlBuffer.append( URLEncoder.encode(mapEnt.getValue()));
                urlBuffer.append(WebserviceConstants.AMPERSAND_SYMBOL);
            }
            formedUrl = urlBuffer.toString();
        }
        if (null != formedUrl) {
            url = formedUrl;
        }
        return  url;
    }

    public static String getServerAddress()
    {
        return getPreferences().getString(Constants.LOGGED_IN_IPADDRESS,WebserviceConstants.SERVERADDRESS);
    }
    public void jsonArrayRequest(String url,final ServiceCompleted mCallback,final String serviceURL) {
        // Tag used to cancel the request
        String tag_json_arry = "json_array_req";

        JsonArrayRequest jsonarrayReq = new JsonArrayRequest(Request.Method.GET,
                url, null,
                new Response.Listener<JSONArray>() {

                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d("response", response.toString());
                        jsonresponse = response.toString();
                        mCallback.ServiceCompleted(jsonresponse,serviceURL);
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                mCallback.ServiceCompleted(error.getMessage(),serviceURL);
                VolleyLog.d("error", "Error: " + error.getMessage());
            }


        });
        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(jsonarrayReq, tag_json_arry);
    }
    public interface ServiceCompleted {
        void ServiceCompleted(String jsonresponse,String serviceURL);
    }
    public void showToast(String message)
    {
        Toast.makeText(this,message,Toast.LENGTH_LONG).show();
    }

    public static SharedPreferences getPreferences() {
        return AppController.getInstance().getSharedPreferences(Constants.SHARED_PREFERENCE_NAME, 0);
    }
    private SharedPreferences.Editor getEditor() {
        return getPreferences().edit();
    }

    public void addString(String key, String value) {
        SharedPreferences.Editor editor = getEditor();
        editor.putString(key, value);
        editor.commit();
    }

    public void addBoolean(String key, boolean value) {
        SharedPreferences.Editor editor = getEditor();
        editor.putBoolean(key, value);
        editor.commit();
    }

    public void addInt(String key, int value) {
        SharedPreferences.Editor editor = getEditor();
        editor.putInt(key, value);
        editor.commit();
    }

    public void addLong(String key, long value) {
        SharedPreferences.Editor editor = getEditor();
        editor.putLong(key, value);
        editor.commit();
    }
}

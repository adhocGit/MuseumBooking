package com.museumbooking.volleyservice;

import android.app.ProgressDialog;
import android.content.Context;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request.Method;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.toolbox.JsonObjectRequest;
import com.museumbooking.conf.WebserviceConstants;
import com.museumbooking.model.MuseumModel;

import org.json.JSONObject;

import java.net.URLEncoder;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;


public class WebserviceExecutor {
    private InvokerParams invokerParams;
    /**
     * The Bean that is populated with response that is being used.
     */
    private Class<MuseumModel> responseBean;

    public static MuseumModel responseParsedBean;
    /**
     * Get, Post etc.
     */
    private HttpMethod httpMethod;

    /**
     * http, https.
     */
    private HttpProtocol httpProtocol;

    /**
     * Service to invoker.
     */
    private String serviceToInvoke;
    /**
     * additionalRequestInformation.
     */
    private String additionalRequestInformation;

    Context context;
    ProgressDialog pDialog;
    String tag_json_obj = "json_obj_req";
    String tag_multi_part_obj = "json_obj_req";
    Response.Listener<JSONObject> listener;
    Response.ErrorListener errlsn;
    private static int httpServiceMethod;

    public WebserviceExecutor(InvokerParams invokerParams, Context context, Response.Listener<JSONObject> listener,
                              Response.ErrorListener errlsn) throws BaseException {
        this.invokerParams = invokerParams;
        this.responseBean = invokerParams.getBeanClazz();
        this.httpMethod = invokerParams.getHttpMethod();
        this.httpProtocol = invokerParams.getHttpProtocol();
        this.serviceToInvoke = invokerParams.getServiceToInvoke();
        this.additionalRequestInformation = invokerParams.getAdditionalRequestInformation();
        this.context = context;
        this.errlsn = errlsn;
        this.listener = listener;
        executeWebservice(invokerParams);
    }


    private void executeWebservice(final InvokerParams<?> invokerParams) throws BaseException {
        String urlToFire = createGetServiceUrl(this.serviceToInvoke, WebserviceConstants.SERVERADDRESS, this.invokerParams.getUrlParameters(), this.httpProtocol, this.httpMethod, this.additionalRequestInformation);
        JsonObjectRequest jsonObjReq = null;
        VolleyMultipartRequest multipartRequest=null;
        boolean multiPart=false;
        switch (httpMethod) {
            case GET:
                jsonObjReq = new JsonObjectRequest(httpServiceMethod, urlToFire,
                        null, listener, errlsn);
                break;
            case POST:
                jsonObjReq = new JsonObjectRequest(httpServiceMethod, urlToFire,
                        null, listener, errlsn) {
                    @Override
                    protected Map<String, String> getParams() {
                        return invokerParams.getUrlParameters();
                    }
                };
                break;
//            case MULTIPOST:
//                multiPart=true;
//                multipartRequest = new VolleyMultipartRequest(httpServiceMethod, urlToFire,
//                        null, listener, errlsn) {
//                    @Override
//                    protected Map<String, String> getParams() {
//                        return invokerParams.getUrlParameters();
//                    }
//                    @Override
//                    protected  Map<String, DataPart> getByteData() {
//
//                        return invokerParams.getMultiPartParameters();
//                    }
//                };
//                break;

        }
        if (null != jsonObjReq) {
            int socketTimeout = 30000;//30 seconds - change to what you want
            RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
            jsonObjReq.setRetryPolicy(policy);
            // Adding request to request queue
//            if(multiPart)
//            {
                AppController.getInstance().addToRequestQueue(jsonObjReq, tag_json_obj);
//            }
//            else {
//                AppController.getInstance().addToRequestQueue(multipartRequest);
//            }
        }

    }

    private static String createGetServiceUrl(String urlToFire,
                                              String environment, Map<String, String> urlParamsMap,
                                              HttpProtocol httpProtocol, HttpMethod httpMethod, String additionalRequestInformation) {
        String url = null;

        //Set protocol
        switch (httpProtocol) {
            case http:
                url = "http";
                break;
            case https:
                url = "https";
                break;
        }

        url = (new StringBuilder(url).append("://")
                .append(environment).append("/").append(WebserviceConstants.URL_PATH).append("/").append(urlToFire)).toString();

        //set parameters if get method
        switch (httpMethod) {
            case GET:
                httpServiceMethod = Method.GET;
                StringBuffer urlBuffer = null;
                String formedUrl = null;
                if (null != urlParamsMap && !urlParamsMap.isEmpty()) {
                    urlBuffer = new StringBuffer(url);
                    final Iterator<Entry<String, String>> urlParamsIterator = urlParamsMap.entrySet().iterator();
                    while (urlParamsIterator.hasNext()) {
                        final Entry<String, String> mapEnt = (Entry<String, String>) urlParamsIterator.next();
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
                break;
            case POST:
                httpServiceMethod = Method.POST;
                break;
            default:
                break;
        }

        return url;
    }
}

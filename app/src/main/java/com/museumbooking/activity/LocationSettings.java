//package com.museumbooking.activity;
//
//import java.io.BufferedReader;
//import java.io.InputStream;
//import java.io.InputStreamReader;
//import java.util.ArrayList;
//import java.util.List;
//
//import org.apache.http.HttpEntity;
//import org.apache.http.HttpResponse;
//import org.apache.http.client.HttpClient;
//import org.apache.http.client.methods.HttpPost;
//import org.apache.http.impl.client.DefaultHttpClient;
//import org.json.JSONArray;
//import org.json.JSONException;
//import org.json.JSONObject;
//
//import com.example.smartjournalist.R;
//
//import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
//import com.google.android.gms.maps.CameraUpdateFactory;
//import com.google.android.gms.maps.GoogleMap;
//import com.google.android.gms.maps.MapView;
//import com.google.android.gms.maps.MapsInitializer;
//import com.google.android.gms.maps.model.LatLng;
//import com.google.android.gms.maps.model.MarkerOptions;
//import com.google.android.gms.maps.model.Polyline;
//import com.google.android.gms.maps.model.PolylineOptions;
//
//import android.annotation.SuppressLint;
//import android.app.Dialog;
//import android.app.ProgressDialog;
//import android.graphics.Color;
//import android.os.AsyncTask;
//import android.os.Bundle;
//import android.os.StrictMode;
//import android.support.annotation.Nullable;
//import android.support.v4.app.Fragment;
//import android.util.Log;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.View.OnClickListener;
//import android.view.ViewGroup;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.RadioButton;
//import android.widget.RadioGroup;
//import android.widget.Toast;
//
//public class LocationSettings extends Fragment {
//
//	private MapView mMapView;
//	private GoogleMap mMap;
//	private Bundle mBundle;
//	Dialog mydialog;
//
//	int startla, startlo;
//	static int flag = 0;
//	EditText startEdit;
//	String page;
//	JSONObject cat;
//
//	Button route, locate;
//
//	@Override
//	public View onCreateView(LayoutInflater inflater, ViewGroup container,
//			Bundle savedInstanceState) {
//		View inflatedView = inflater.inflate(R.layout.locationsettings,
//				container, false);
//
//		try {
//			MapsInitializer.initialize(getActivity());
//		} catch (GooglePlayServicesNotAvailableException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//
//		mMapView = (MapView) inflatedView.findViewById(R.id.map);
//		mMapView.onCreate(mBundle);
//		setUpMapIfNeeded(inflatedView);
//
//		return inflatedView;
//	}
//
//	@Override
//	public void onCreate(Bundle savedInstanceState) {
//		super.onCreate(savedInstanceState);
//		mBundle = savedInstanceState;
//
//	}
//
//	private void setUpMapIfNeeded(View inflatedView) {
//		if (mMap == null) {
//			mMap = ((MapView) inflatedView.findViewById(R.id.map)).getMap();
//			if (mMap != null) {
//				setUpMap();
//			}
//		}
//	}
//
//	private void setUpMap() {
//
//		Log.i("latitude=" + MyLocationListener.latitude, "longitude="
//				+ MyLocationListener.longitude);
//		mMap.addMarker(new MarkerOptions().position(
//				new LatLng(MyLocationListener.latitude,
//						MyLocationListener.longitude)).title("Marker"));
//
//		mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(
//				MyLocationListener.latitude, MyLocationListener.longitude),
//				12.0f));
//	}
//
//	@Override
//	public void onActivityCreated(Bundle savedInstanceState) {
//		// TODO Auto-generated method stub
//		super.onActivityCreated(savedInstanceState);
//
//		mydialog = new Dialog(getActivity());
//		route = (Button) getActivity().findViewById(R.id.location_route);
//		locate = (Button) getActivity().findViewById(R.id.location_locate);
//		route.setOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(View v) {
//				// TODO Auto-generated method stub
//
//				final EditText source, destination;
//				Button submit, cancel;
//
//				mydialog.setContentView(R.layout.findroute_dialog);
//				mydialog.setTitle("Route");
//
//				source = (EditText) mydialog.findViewById(R.id.sourceid);
//				destination = (EditText) mydialog.findViewById(R.id.destid);
//				submit = (Button) mydialog.findViewById(R.id.route_submit);
//				cancel = (Button) mydialog.findViewById(R.id.route_cancel);
//
//				submit.setOnClickListener(new OnClickListener() {
//
//					@Override
//					public void onClick(View v) {
//						// TODO Auto-generated method stub
//
//						String sourcestr = source.getText().toString();
//						String deststr = destination.getText().toString();
//
//						if (sourcestr.equals("") && deststr.equals("")) {
//							Toast.makeText(getActivity(), "Fill all Fields",
//									5000).show();
//						} else {
//							// show route n map and dismiss dialog
//							Findlatitude obj = new Findlatitude(getActivity());
//							obj.getLatitudeAndLongitudeFromGoogleMapForAddress(sourcestr);
//
//							double sourcelat = Findlatitude.StartLat;
//							double sourcelog = Findlatitude.endlat;
//							obj.getLatitudeAndLongitudeFromGoogleMapForAddress(deststr);
//
//							double destlat = Findlatitude.StartLat;
//							double destlog = Findlatitude.endlat;
//							Log.i("sda", "" + sourcelat + sourcelog + destlat
//									+ destlog);
//							LatLng latLng = new LatLng(sourcelat, sourcelog);
//							mMap.animateCamera(CameraUpdateFactory
//									.newLatLngZoom(latLng, 12.0f));
//							String url = makeURL(sourcelat, sourcelog, destlat,
//									destlog);
//							// String
//							// url=makeURL(8.472372,76.948071,8.47025,76.959314);
//							connectAsyncTask asyn = new connectAsyncTask(url);
//							asyn.execute();
//							mydialog.dismiss();
//						}
//
//					}
//				});
//
//				cancel.setOnClickListener(new OnClickListener() {
//
//					@Override
//					public void onClick(View v) {
//						// TODO Auto-generated method stub
//						mydialog.dismiss();
//
//					}
//				});
//
//				mydialog.show();
//
//			}
//		});
//
//		locate.setOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(View v) {
//				// TODO Auto-generated method stub
//
//				mydialog.setContentView(R.layout.locateplaces_dialog);
//				mydialog.setTitle("Locate");
//
//				final RadioGroup rg = (RadioGroup) mydialog
//						.findViewById(R.id.locateplacesRG);
//
//				mydialog.show();
//				Button locate_submit = (Button) mydialog
//						.findViewById(R.id.locate_submit);
//
//				locate_submit.setOnClickListener(new OnClickListener() {
//
//					@Override
//					public void onClick(View v) {
//						// TODO Auto-generated method stub
//						mydialog.dismiss();
//
//						int id = rg.getCheckedRadioButtonId();
//						RadioButton rb = (RadioButton) mydialog
//								.findViewById(id);
//
//						page = rb.getText().toString();
//
//						findplace(MyLocationListener.latitude,
//								MyLocationListener.longitude);
//
//					}
//				});
//
//			}
//		});
//	}
//
//	public String makeURL(double sourcelat, double sourcelog, double destlat,
//			double destlog) {
//		StringBuilder urlString = new StringBuilder();
//		urlString.append("http://maps.googleapis.com/maps/api/directions/json");
//		urlString.append("?origin=");// from
//		urlString.append(Double.toString(sourcelat));
//		urlString.append(",");
//		urlString.append(Double.toString(sourcelog));
//		urlString.append("&destination=");// to
//		urlString.append(Double.toString(destlat));
//		urlString.append(",");
//		urlString.append(Double.toString(destlog));
//		urlString.append("&sensor=false&mode=driving&alternatives=true");
//		return urlString.toString();
//	}
//
//	private class connectAsyncTask extends AsyncTask<Void, Void, String> {
//		private ProgressDialog progressDialog;
//		String url;
//
//		connectAsyncTask(String urlPass) {
//			url = urlPass;
//		}
//
//		@Override
//		protected void onPreExecute() {
//			// TODO Auto-generated method stub
//			super.onPreExecute();
//			progressDialog = new ProgressDialog(getActivity());
//			progressDialog.setMessage("Fetching route, Please wait...");
//			progressDialog.setIndeterminate(true);
//			progressDialog.show();
//		}
//
//		@Override
//		protected String doInBackground(Void... params) {
//			JSONParser jParser = new JSONParser();
//			String json = jParser.getJSONFromUrl(url);
//			return json;
//		}
//
//		@Override
//		protected void onPostExecute(String result) {
//			super.onPostExecute(result);
//			progressDialog.hide();
//			if (result != null) {
//				drawPath(result);
//			}
//		}
//	}
//
//	public void drawPath(String result) {
//
//		try {
//			// Tranform the string into a json object
//			final JSONObject json = new JSONObject(result);
//			JSONArray routeArray = json.getJSONArray("routes");
//			JSONObject routes = routeArray.getJSONObject(0);
//			JSONObject overviewPolylines = routes
//					.getJSONObject("overview_polyline");
//			String encodedString = overviewPolylines.getString("points");
//			List<LatLng> list = decodePoly(encodedString);
//
//			for (int z = 0; z < list.size() - 1; z++) {
//				LatLng src = list.get(z);
//				LatLng dest = list.get(z + 1);
//				Polyline line = mMap.addPolyline(new PolylineOptions()
//						.add(new LatLng(src.latitude, src.longitude),
//								new LatLng(dest.latitude, dest.longitude))
//						.width(2).color(Color.BLUE).geodesic(true));
//			}
//
//		} catch (JSONException e) {
//
//		}
//	}
//
//	private List<LatLng> decodePoly(String encoded) {
//
//		List<LatLng> poly = new ArrayList<LatLng>();
//		int index = 0, len = encoded.length();
//		int lat = 0, lng = 0;
//
//		while (index < len) {
//			int b, shift = 0, result = 0;
//			do {
//				b = encoded.charAt(index++) - 63;
//				result |= (b & 0x1f) << shift;
//				shift += 5;
//			} while (b >= 0x20);
//			int dlat = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
//			lat += dlat;
//
//			shift = 0;
//			result = 0;
//			do {
//				b = encoded.charAt(index++) - 63;
//				result |= (b & 0x1f) << shift;
//				shift += 5;
//			} while (b >= 0x20);
//			int dlng = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
//			lng += dlng;
//
//			LatLng p = new LatLng((((double) lat / 1E5)),
//					(((double) lng / 1E5)));
//			poly.add(p);
//		}
//
//		return poly;
//	}
//
//	@Override
//	public void onResume() {
//		super.onResume();
//		mMapView.onResume();
//	}
//
//	@Override
//	public void onPause() {
//		super.onPause();
//		mMapView.onPause();
//	}
//
//	@Override
//	public void onDestroy() {
//		mMapView.onDestroy();
//		super.onDestroy();
//	}
//
//	@SuppressLint("NewApi")
//	public void findplace(double lat, double log) {
//		mMap.clear();
//		Log.i("page", "" + page);
//		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
//				.permitAll().build();
//
//		StrictMode.setThreadPolicy(policy);
//		try {
//			Log.i("latitude and longitude", "" + MyLocationListener.latitude
//					+ MyLocationListener.longitude);
//			if (page.equals("ATM")) {
//				cat = getJSONfromURL("https://maps.googleapis.com/maps/api/place/textsearch/json?query=atm&location="
//						+ lat
//						+ ","
//						+ log
//						+ "&radius=2000&sensor=false&key=AIzaSyAREhlNc0UyqHAgOFB-FgfLwXEiRlJzuZE");
//			} else if (page.equals("Hospitals")) {
//				cat = getJSONfromURL("https://maps.googleapis.com/maps/api/place/textsearch/json?query=hospitals&location="
//						+ lat
//						+ ","
//						+ log
//						+ "&radius=2000&sensor=false&key=AIzaSyAREhlNc0UyqHAgOFB-FgfLwXEiRlJzuZE");
//			} else if (page.equals("restaurant")) {
//				cat = getJSONfromURL("https://maps.googleapis.com/maps/api/place/textsearch/json?query=restaurants&location="
//						+ lat
//						+ ","
//						+ log
//						+ "&radius=2000&sensor=false&key=AIzaSyAREhlNc0UyqHAgOFB-FgfLwXEiRlJzuZE");
//			} else if (page.equals("PoliceStation")) {
//				cat = getJSONfromURL("https://maps.googleapis.com/maps/api/place/textsearch/json?query=policestation&location="
//						+ lat
//						+ ","
//						+ log
//						+ "&radius=2000&sensor=false&key=AIzaSyAREhlNc0UyqHAgOFB-FgfLwXEiRlJzuZE");
//			}
//
//			JSONArray httpattributr = cat.getJSONArray("html_attributions");
//			JSONArray JArray = cat.getJSONArray("results");
//			if (JArray != null) {
//				Log.i("cat", "" + cat);
//				Log.i("JArray", "" + JArray);
//				for (int i = 0; i < JArray.length(); i++) {
//					Log.i("flag", "" + flag);
//					JSONObject jo = (JSONObject) JArray.get(i);
//					JSONObject jgeometry = (JSONObject) jo.get("geometry");
//					String hospitalname = jo.get("name").toString();
//					Log.i("hospitalname", hospitalname);
//					JSONObject jlocation = (JSONObject) jgeometry
//							.get("location");
//					Log.i("jo", "" + jo);
//					Log.i("geometry", "" + jo.get("geometry"));
//					String addressvalue = jo.get("formatted_address")
//							.toString();
//					Log.i("location", "" + jlocation);
//					Log.i("lng", "" + jlocation.get("lng"));
//					Log.i("lat", "" + jlocation.get("lat"));
//					String longit = jlocation.get("lng").toString();
//					String latvalue = jlocation.get("lat").toString();
//					LatLng latLng = new LatLng(Double.parseDouble(latvalue),
//							Double.parseDouble(longit));
//					mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
//					mMap.addMarker(new MarkerOptions().position(latLng)
//							.title("" + hospitalname)
//							.snippet("" + addressvalue));
//					mMap.getUiSettings().setCompassEnabled(true);
//					mMap.getUiSettings().setZoomControlsEnabled(true);
//					flag = 1;
//					Log.i("flag", "" + flag);
//				}
//				flag = 0;
//			} else {
//				Toast.makeText(getActivity(), "Connection error", 1000).show();
//			}
//		} catch (JSONException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		LatLng latLng = new LatLng(MyLocationListener.latitude,
//				MyLocationListener.longitude);
//		mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));
//	}
//
//	public static JSONObject getJSONfromURL(String url) {
//
//		// initialize
//		InputStream is = null;
//		String result = "";
//		JSONObject jArray = null;
//
//		// http post
//		try {
//
//			HttpClient httpclient = new DefaultHttpClient();
//			HttpPost httppost = new HttpPost(url);
//			HttpResponse response = httpclient.execute(httppost);
//			HttpEntity entity = response.getEntity();
//
//			is = entity.getContent();
//
//		} catch (Exception e) {
//			Log.e("log_tag", "Error in http connection " + e.toString());
//		}
//		// convert response to string
//
//		try {
//
//			BufferedReader reader = new BufferedReader(new InputStreamReader(
//					is, "iso-8859-1"), 8);
//			StringBuilder sb = new StringBuilder();
//			String line = null;
//
//			while ((line = reader.readLine()) != null) {
//				sb.append(line + "\n");
//			}
//
//			is.close();
//			result = sb.toString();
//
//		} catch (Exception e) {
//			Log.e("log_tag", "Error converting result " + e.toString());
//		}
//		// try parse the string to a JSON object
//
//		try {
//			jArray = new JSONObject(result);
//
//		} catch (JSONException e) {
//			Log.e("log_tag", "Error parsing data " + e.toString());
//
//		}
//		return jArray;
//	}
//
//}

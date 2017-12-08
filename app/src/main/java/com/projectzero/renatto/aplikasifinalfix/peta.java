package com.projectzero.renatto.aplikasifinalfix;

import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.projectzero.renatto.aplikasifinalfix.model.model_peta;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class peta extends AppCompatActivity implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener{
    GoogleApiClient mGoogleApiClient;
    LatLng latLng;
    GoogleMap mGoogleMap;
    Marker currLocationMarker;
    LocationRequest mLocationRequest;
    MapFragment mFragmentMap;
    RequestQueue requestQueue;
    ProgressDialog loading;
    String telp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_peta);
        mFragmentMap = MapFragment.newInstance();
        FragmentTransaction mTranscation = getFragmentManager().beginTransaction();
        mTranscation.add(R.id.maps_place,mFragmentMap);
        mTranscation.commit();
        mFragmentMap.getMapAsync(this);
        requestQueue = Volley.newRequestQueue(this);
        getData();
    }

    @Override
    public void onMapReady(GoogleMap gMap) {
        mGoogleMap = gMap;
        mGoogleMap.getUiSettings().setZoomControlsEnabled(true);
        LatLng Pusat = new LatLng(-6.423719, 107.094323);
        mGoogleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(Pusat,18.0f));
        mGoogleMap.setOnMarkerClickListener(this);
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    public void getData() {
        String url = "https://projectfinal20001.000webhostapp.com/Android/marker_google_map_api/markers.php";
        final ProgressDialog loading = ProgressDialog.show(peta.this,"Loading Data", "Please wait...",false,false);
        JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.GET, url,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        loading.dismiss();
                        Log.d("hasilnya ", response.toString());
                        try {

                            JSONArray data_peta = response.getJSONArray("datars");

                            for (int a = 0; a < data_peta.length(); a++) {
                                model_peta data = new model_peta();
                                JSONObject json = data_peta.getJSONObject(a);
                                String pnama = json.getString("id");
                                String ptempat = json.getString("nama");
                                String plat = String.valueOf(json.getString("lat"));
                                String plng = String.valueOf(json.getString("lng"));
                                mGoogleMap.addMarker(new MarkerOptions().position(
                                new LatLng(Double.parseDouble(plat),Double.parseDouble(plng))).title(pnama).snippet(ptempat));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            System.out.println("ini kesalahannya " + e.getMessage());
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        loading.dismiss();
                        Log.d("ini kesalahannya",error.toString());
                        System.out.println("ini kesalahannya " + error.getMessage());
                    }
                });

        requestQueue.add(jsonRequest);
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        Intent intent = new Intent(this, detail_maps.class);
        marker.hideInfoWindow();
        String s = marker.getTitle();
        intent.putExtra("id",s);
        startActivity(intent);
        return false;
    }
}

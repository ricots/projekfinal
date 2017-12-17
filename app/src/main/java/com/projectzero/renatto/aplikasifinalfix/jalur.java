package com.projectzero.renatto.aplikasifinalfix;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.projectzero.renatto.aplikasifinalfix.util.DirectionFinder;
import com.projectzero.renatto.aplikasifinalfix.util.DirectionFinderListener;
import com.projectzero.renatto.aplikasifinalfix.util.GPSTracker;
import com.projectzero.renatto.aplikasifinalfix.util.Route;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class jalur extends AppCompatActivity implements OnMapReadyCallback,
        DirectionFinderListener,GoogleApiClient.OnConnectionFailedListener, GoogleApiClient.ConnectionCallbacks{
    Activity activity;
    private EditText etOrigin1;
    private EditText etDestination1;
    GPSTracker gpsx;
    private WifiManager wfm;
    private ConnectivityManager cntm;
    com.android.volley.RequestQueue RequestQueue;
    private ArrayList<String> jalur;
    private Button btnFindPath1;
    private List<Marker> originMarkers = new ArrayList<>();
    private List<Marker> destinationMarkers = new ArrayList<>();
    private List<Polyline> polylinePaths = new ArrayList<>();
    private GoogleMap mMap;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jalur);
        MapFragment mFragmentMap = MapFragment.newInstance();
        android.app.FragmentTransaction mTranscation = getFragmentManager().beginTransaction();
        mTranscation.add(R.id.map1,mFragmentMap);
        mTranscation.commit();
        mFragmentMap.getMapAsync(this);

        btnFindPath1 = (Button) findViewById(R.id.btnFindPath1);
        etOrigin1 = (EditText) findViewById(R.id.etOrigin1);
        etDestination1 = (EditText) findViewById(R.id.etDestination1);

        Intent intent = getIntent();
        String title = intent.getStringExtra("nama_tempat");
        etDestination1.setText(title);

        jalur = new ArrayList<String>();
        RequestQueue = Volley.newRequestQueue(this);

        //proses mendapatkan lokasi kita
        if(!network()){
            createNetErrorDialog();
        }
        else{
            LocationToAddress();
        }

        CekGPS();

        btnFindPath1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendRequest();
            }
        });
    }

    public boolean network() {

//Get the wifi service of device
        wfm = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
//Get the Mobile Dtata service of device
        cntm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        final NetworkInfo nInfo = cntm.getActiveNetworkInfo();

        if ((wfm.isWifiEnabled()) || (nInfo != null && nInfo.isConnected())) {
            return true;
        }

        else{
            return false;
        }

    }

    protected void createNetErrorDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("You need a network connection to use this application. Please turn on mobile network or Wi-Fi in Settings.")
                .setTitle("Unable to connect")
                .setCancelable(false)
                .setPositiveButton("Enable",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Intent i = new Intent(Settings.ACTION_SETTINGS);

                                //startActivity(new Intent(android.provider.Settings.ACTION_WIRELESS_SETTINGS));

                                startActivity(i);
                                //finish();
                            }
                        }
                )  .setNegativeButton("Cancel",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        finish();
                    }
                }
        )

        ;
        AlertDialog alert = builder.create();
        alert.show();
    }

    public void CekGPS() {
        try {
            LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("info");
                builder.setMessage("Apakah anda akan mengaktifkan GPS?");
                builder.setPositiveButton("Ya",
                        new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface arg0, int arg1) {
                                // TODO Auto-generated method stub
                                Intent i = new Intent(
                                        android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                                startActivity(i);

                            }
                        });
                builder.setNegativeButton("Tidak",
                        new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int arg1) {
                                // TODO Auto-generated method stub
                                dialog.dismiss();
                            }
                        });
                builder.create().show();
            }
        } catch (Exception e) {
            // TODO: handle exception

        }
    }

    public void LocationToAddress(){
        gpsx = new GPSTracker(jalur.this);
        double lat = gpsx.getLatitude();
        double longt = gpsx.getLongitude();

        final String lattadd = String.valueOf(lat);
        final String logtadd = String.valueOf(longt);


        //    JsonObjectRequest request = new JsonObjectRequest("https://maps.googleapis.com/maps/api/geocode/json?latlng=23.781522,"90.3704991&key=AIzaSyBma_A78YGbZwGav3SR3vSGoAXka8FGFzQ", new Response.Listener<JSONObject>() {

        JsonObjectRequest request = new JsonObjectRequest("https://maps.googleapis.com/maps/api/geocode/json?latlng="+lattadd+","+logtadd+"&key=AIzaSyDnX4KCoDJv9bis5NoVrLQzuQwXE2U4KVg",
                new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    String getaddress = response.getJSONArray("results").getJSONObject(0).getString("formatted_address");
                    etOrigin1.setText(getaddress);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        RequestQueue.add(request);
    }

    private void sendRequest() {
        String origin = etOrigin1.getText().toString();
        //String destination = etDestination.getText().toString();
        String destination = etDestination1.getText().toString();
        System.out.println("destination " +destination);
        if (origin.isEmpty()) {
            Toast.makeText(this, "Please enter origin address!", Toast.LENGTH_SHORT).show();
            return;
        }
        if (destination.isEmpty()) {
            Toast.makeText(this, "Please enter destination address!", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            new DirectionFinder(this, origin, destination).execute();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.getUiSettings().setZoomControlsEnabled(true);
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            return;
        }
        mMap.setMyLocationEnabled(true);
    }


    @Override
    public void onDirectionFinderStart() {
        progressDialog = ProgressDialog.show(this, "Please wait.",
                "Finding direction..!", true);

        if (originMarkers != null) {
            for (Marker marker : originMarkers) {
                marker.remove();
            }
        }

        if (destinationMarkers != null) {
            for (Marker marker : destinationMarkers) {
                marker.remove();
            }
        }

        if (polylinePaths != null) {
            for (Polyline polyline:polylinePaths ) {
                polyline.remove();
            }
        }
    }

    @Override
    public void onDirectionFinderSuccess(List<Route> routes) {
        progressDialog.dismiss();
        polylinePaths = new ArrayList<>();
        originMarkers = new ArrayList<>();
        destinationMarkers = new ArrayList<>();

        for (Route route : routes) {
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(route.startLocation, 16));
            ((TextView) findViewById(R.id.tvDuration1)).setText(route.duration.text);
            ((TextView) findViewById(R.id.tvDistance1)).setText(route.distance.text);

            originMarkers.add(mMap.addMarker(new MarkerOptions()
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.start_blue))
                    .title(route.startAddress)
                    .position(route.startLocation)));
            destinationMarkers.add(mMap.addMarker(new MarkerOptions()
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.end_green))
                    .title(route.endAddress)
                    .position(route.endLocation)));

            PolylineOptions polylineOptions = new PolylineOptions().
                    geodesic(true).
                    color(Color.BLUE).
                    width(10);

            for (int i = 0; i < route.points.size(); i++)
                polylineOptions.add(route.points.get(i));

            polylinePaths.add(mMap.addPolyline(polylineOptions));
        }
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}

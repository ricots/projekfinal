package com.projectzero.renatto.aplikasifinalfix;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.Volley;
import com.projectzero.renatto.aplikasifinalfix.app.AppController;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class detail_maps extends AppCompatActivity {
    NetworkImageView thumb_image;
    TextView nama, alamat, poly, notelp, nofaskes;
    private ImageView callPhone, Navigasi;
    String title;
    String url = "https://projectfinal20001.000webhostapp.com/Android/marker_google_map_api/detailnya.php?id=";
    RequestQueue requestQueue;
    ImageLoader imageLoader = AppController.getInstance().getImageLoader();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_maps);

        thumb_image = (NetworkImageView) findViewById(R.id.image1);
        nama       = (TextView) findViewById(R.id.nama1);
        alamat         = (TextView) findViewById(R.id.alamat1);
        poly         = (TextView) findViewById(R.id.poly1);
        notelp         = (TextView) findViewById(R.id.notelp1);
        nofaskes         = (TextView) findViewById(R.id.nofaskes1);
        Navigasi = (ImageView) findViewById(R.id.navigasi_button1);

        Navigasi.setOnClickListener(new View.OnClickListener(){
                                        @Override public void onClick(View v){
                                            Intent intent = new Intent(detail_maps.this, jalur.class);
                                            intent.putExtra("nama_tempat",alamat.getText().toString());
                                            startActivity(intent);

                                        }
                                    }
        );

        callPhone = (ImageView) findViewById(R.id.call_button1);
        callPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(detail_maps.this);
                builder.setMessage("Call " + "notelp" + " ?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent i = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + notelp.getText().toString()));
                        try {
                            startActivity(i);
                        } catch (SecurityException se) {
                            Toast.makeText(detail_maps.this, "Permission Denied", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                AlertDialog alert = builder.create();
                alert.show();
            }
        });

        Intent intent = getIntent();
        title = intent.getStringExtra("id");

        requestQueue = Volley.newRequestQueue(this);
        getdata();
    }

    public void getdata(){
        final ProgressDialog loading = ProgressDialog.show(detail_maps.this,"Loading Data", "Please wait...",false,false);
        JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.GET, url + title,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        loading.dismiss();
                        Log.d("hasilnya ", response.toString());
                        try {

                            JSONArray data_trans = response.getJSONArray("detail");

                            for (int a = 0; a < data_trans.length(); a++) {

                                JSONObject json = data_trans.getJSONObject(a);
                                nama.setText(json.getString("nama"));
                                alamat.setText(json.getString("alamat"));
                                notelp.setText(json.getString("notelp"));
                                nofaskes.setText(json.getString("nofaskes"));
                                alamat.setText(json.getString("alamat"));
                                notelp.setText(json.getString("notelp"));
                                poly.setText(json.getString("poly"));
                                String Image   = json.getString("image");
                                if (json.getString("image")!=""){
                                    thumb_image.setImageUrl(Image, imageLoader);
                                }
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

}

package com.projectzero.renatto.aplikasifinalfix;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.StringRequest;
import com.projectzero.renatto.aplikasifinalfix.util.Server;
import com.projectzero.renatto.aplikasifinalfix.app.AppController;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class Detail extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

    NetworkImageView thumb_image;
    TextView nama, alamat, poly, notelp, nofaskes;
    ImageLoader imageLoader = AppController.getInstance().getImageLoader();
    SwipeRefreshLayout swipe;
    String id;

    private ImageView callPhone, Navigasi;
    public static Double latitude, longitude;
    public static String Call;

    private static final String TAG = Detail.class.getSimpleName();


    public static final String TAG_NO       = "no";
    public static final String TAG_ID       = "id";
    public static final String TAG_NAMA    = "nama";
    public static final String TAG_ALAMAT      = "alamat";
    public static final String TAG_NOTELP      = "notelp";
    public static final String TAG_NOFASKES   = "nofaskes";
    public static final String TAG_POLY   = "poly";
    public static final String TAG_IMAGE   = "image";

    private static final String url_detail  = Server.URL + "detail.php";
    String tag_json_obj = "json_obj_req";

    //urlnya = https://projectfinal20001.000webhostapp.com/Android/marker_google_map_api/detailnya.php?id=100003

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("Detail Klinik");

        thumb_image = (NetworkImageView) findViewById(R.id.image);
        nama       = (TextView) findViewById(R.id.nama);
        alamat         = (TextView) findViewById(R.id.alamat);
        poly         = (TextView) findViewById(R.id.poly);
        notelp         = (TextView) findViewById(R.id.notelp);
        nofaskes         = (TextView) findViewById(R.id.nofaskes);
        swipe       = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_layout);
        Navigasi = (ImageView) findViewById(R.id.navigasi_button);

        Navigasi.setOnClickListener(new View.OnClickListener(){
                                      @Override public void onClick(View v){
                                          Intent intent = new Intent(Detail.this, jalur.class);
                                          intent.putExtra("nama_tempat",alamat.getText().toString());
                                          startActivity(intent);

                                      }
                                  }
        );

        this.id = getIntent().getStringExtra(TAG_ID);
        this.id = getIntent().getStringExtra(TAG_ID);
        this.id = getIntent().getStringExtra(TAG_ID);


        swipe.setOnRefreshListener(this);

        swipe.post(new Runnable() {
                       @Override
                       public void run() {
                           if (!id.isEmpty()) {
                               callDetailRumahSakit(id);
                           }
                       }
                   }
        );


        callPhone = (ImageView) findViewById(R.id.call_button);
        callPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(Detail.this);
                builder.setMessage("Call " + TAG_NOTELP + " ?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent i = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + notelp.getText().toString()));
                        try {
                            startActivity(i);
                        } catch (SecurityException se) {
                            Toast.makeText(Detail.this, "Permission Denied", Toast.LENGTH_SHORT).show();
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

    }

    private void callDetailRumahSakit(final String id){
        swipe.setRefreshing(true);

        Toast.makeText(this,url_detail,Toast.LENGTH_LONG).show();
        StringRequest strReq = new StringRequest(Request.Method.POST, url_detail, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Response " + response.toString());
                swipe.setRefreshing(false);

                try {
                    JSONObject obj = new JSONObject(response);

                    String ID = obj.getString(TAG_ID);
                    String Nama    = obj.getString(TAG_NAMA);
                    String Image   = obj.getString(TAG_IMAGE);
                    String Alamat      = obj.getString(TAG_ALAMAT);
                    String Notelp      = obj.getString(TAG_NOTELP);
                    String Nofaskes      = obj.getString(TAG_NOFASKES);
                    String Poly      = obj.getString(TAG_POLY);

                    nama.setText(Nama);
                    alamat.setText(Alamat);
                    nofaskes.setText(Nofaskes);
                    notelp.setText(Notelp);
                    poly.setText(Poly);

                    if (obj.getString(TAG_IMAGE)!=""){
                        thumb_image.setImageUrl(Image, imageLoader);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Detail News Error: " + error.getMessage());
                Toast.makeText(Detail.this,
                        error.getMessage(), Toast.LENGTH_LONG).show();
                swipe.setRefreshing(false);
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to post url
                Map<String, String> params = new HashMap<String, String>();
                params.put("id", id);

                return params;
            }

        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_json_obj);
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onRefresh() {
        callDetailRumahSakit(id);
    }



}
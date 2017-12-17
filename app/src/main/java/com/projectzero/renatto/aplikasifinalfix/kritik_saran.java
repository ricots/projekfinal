package com.projectzero.renatto.aplikasifinalfix;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class kritik_saran extends AppCompatActivity {
    Button kirim;
    EditText nama,kritik;
    ProgressDialog PD;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kritik_saran);
        PD = new ProgressDialog(this);
        PD.setMessage("silahkan tunggu.....");
        PD.setCancelable(false);
        nama = (EditText) findViewById(R.id.nama);
        kritik = (EditText) findViewById(R.id.kritiksaran);
        kirim = (Button) findViewById(R.id.kirim);
        kirim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                kritik();
            }
        });
    }

    public void kritik() {
        PD.show();
            StringRequest postRequest = new StringRequest(Request.Method.POST, "http://projectfinal.xyz/Android/marker_google_map_api/kritiksaran.php",
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            PD.dismiss();
                            Toast.makeText(getApplicationContext(),
                                    response.toString(),
                                    Toast.LENGTH_SHORT).show();
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    PD.dismiss();
                    Toast.makeText(getApplicationContext(),
                            error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("nama", nama.getText().toString());
                    params.put("kritiksaran", kritik.getText().toString());
                    return params;
                }
            };

            // Adding request to request queue
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            requestQueue.add(postRequest);
    }
}

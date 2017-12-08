package com.projectzero.renatto.aplikasifinalfix;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;

public class MenuUtama extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_menu_utama);

        ImageButton carirs = (ImageButton) findViewById(R.id.carirs);
        ImageButton peta = (ImageButton) findViewById(R.id.peta);
        ImageButton bantuan = (ImageButton) findViewById(R.id.bantuan);
        ImageButton tentang = (ImageButton) findViewById(R.id.tentang);

        carirs.setOnClickListener(new View.OnClickListener(){
            @Override public void onClick(View v){
            Intent i = new Intent(MenuUtama.this, MainActivity.class);
            startActivity(i);
            }
        }
        );
        peta.setOnClickListener(new View.OnClickListener(){
            @Override public void onClick(View v){
                Intent i = new Intent(MenuUtama.this, peta.class);
                startActivity(i);
            }
        }
        );
        bantuan.setOnClickListener(new View.OnClickListener(){
            @Override public void onClick(View v){
                 Intent i = new Intent(MenuUtama.this, Bantuan.class);
                 startActivity(i);
            }
        }
        );
        tentang.setOnClickListener(new View.OnClickListener(){
             @Override public void onClick(View v){
                 Intent i = new Intent(MenuUtama.this, Tentang.class);
                 startActivity(i);
                 }
        }
        );
    }
}
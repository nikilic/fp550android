package com.nikilic.fp550apiclient;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void g11open(View view){
        Intent intent = new Intent(this, g11.class);
        startActivity(intent);
    }

    public void g12open(View view){
        Intent intent = new Intent(this, g12.class);
        startActivity(intent);
    }

    public void g13open(View view){
        Intent intent = new Intent(this, g13.class);
        startActivity(intent);
    }

    public void g14open(View view){
        Intent intent = new Intent(this, g14.class);
        startActivity(intent);
    }

    public void g15open(View view){
        Intent intent = new Intent(this, g15.class);
        startActivity(intent);
    }

    public void g16open(View view){
        Intent intent = new Intent(this, g16.class);
        startActivity(intent);
    }

    public void g17open(View view){
        Intent intent = new Intent(this, g17.class);
        startActivity(intent);
    }

    public void g18open(View view){
        Intent intent = new Intent(this, g18.class);
        startActivity(intent);
    }

    public void g21open(View view){
        Intent intent = new Intent(this, g21.class);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {

    }
}

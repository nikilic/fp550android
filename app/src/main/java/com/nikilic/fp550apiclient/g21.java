package com.nikilic.fp550apiclient;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class g21 extends AppCompatActivity {

    ImageView result;
    String resultold;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_g21);
    }

    public void send(View view){
        String url = "https://fp550irvas.localtunnel.me/g21_cmd";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(response.substring(1, response.length() - 2)));
                        Log.v("URL",response.substring(1, response.length() - 1));
                        Log.v("URL",Uri.parse(response.substring(1, response.length() - 1)).toString());
                        startActivity(browserIntent);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Connection Error", Toast.LENGTH_SHORT).show();
                Log.e("ERROR CONNECTION", error.toString());
            }
        });
        volleyqueue.getInstance(this).addToRequestQueue(stringRequest);
    }

    public void returnhome(View view){
        Intent intent = new Intent(getApplicationContext(),MainActivity.class);
        startActivity(intent);
    }
}
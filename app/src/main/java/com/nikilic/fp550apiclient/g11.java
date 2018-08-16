package com.nikilic.fp550apiclient;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

public class g11 extends AppCompatActivity {

    TextView result;
    TextView resultchar, ack, cmd, len, seq;
    String resultold,resultcharold;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_g1);
    }

    public void send(View view){
        result = findViewById(R.id.result);
        resultchar = findViewById(R.id.resultchar);
        ack = findViewById(R.id.ack);
        cmd = findViewById(R.id.cmd);
        len = findViewById(R.id.len);
        seq = findViewById(R.id.seq);
        String url = "https://fp550irvas.localtunnel.me/g11_cmd";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        result.setText("");
                            try {
                                JSONObject jObject = new JSONObject(response);
                                ack.setText("ACK: "+jObject.getString("ACKS"));
                                cmd.setText("CMD: "+jObject.getString("Cmd"));
                                len.setText("LEN: "+jObject.getString("LEN"));
                                seq.setText("SEQ: "+jObject.getString("SEQ"));
                                String data = jObject.getString("recv_pck_Data").substring(1, jObject.getString("recv_pck_Data").length() - 1);
                                String[] dataarray = data.split(",");
                                for(String str:dataarray){
                                    resultold = result.getText().toString();
                                    Log.v("INTEG",str);
                                    result.setText(resultold+" "+str);
                                    resultcharold = resultchar.getText().toString();
                                    resultchar.setText(resultcharold + (char) Integer.parseInt(str));
                                }
                            } catch (JSONException e) {
                                Log.e("ERROR", "unexpected JSON exception", e);
                            }
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

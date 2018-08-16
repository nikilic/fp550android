package com.nikilic.fp550apiclient;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

public class custom extends AppCompatActivity {

    EditText data,command;
    String url,resultold;
    TextView result,ack,cmd,len,seq;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom);

        data = findViewById(R.id.data);
        command = findViewById(R.id.command);
        result = findViewById(R.id.result);
        ack = findViewById(R.id.ack);
        cmd = findViewById(R.id.cmd);
        len = findViewById(R.id.len);
        seq = findViewById(R.id.seq);
    }

    public void allcmd(View view){
        Intent intent = new Intent(getApplicationContext(),generic.class);
        startActivity(intent);
    }

    public void sendrequest(View view){
        url = "https://fp550irvas.localtunnel.me/g1?cmd="+command.getText().toString()+"&data="+data.getText().toString();
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
}

package com.nikilic.fp550apiclient;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

public class generic extends AppCompatActivity {

    RadioGroup radioGroup;
    EditText data;
    String url,resultold,resultcharold;
    TextView result,ack,cmd,len,seq,resultchar;
    boolean convert;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generic);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        radioGroup = findViewById(R.id.radioGroup);
        data = findViewById(R.id.data);
        result = findViewById(R.id.resultchar);
        resultchar = findViewById(R.id.result);
        ack = findViewById(R.id.ack);
        cmd = findViewById(R.id.cmd);
        len = findViewById(R.id.len);
        seq = findViewById(R.id.seq);
    }

    public void sendrequest(View view){
        int selectedId = radioGroup.getCheckedRadioButtonId();
        convert = false;
        switch (selectedId){
            case R.id.g11:
                url = "https://fp550irvas.localtunnel.me/g1?cmd=62";
                convert=true;
                break;
            case R.id.g12:
                url = "https://fp550irvas.localtunnel.me/g1?cmd=99";
                convert=true;
                break;
            case R.id.g13:
                url = "https://fp550irvas.localtunnel.me/g1?cmd=44&data="+Integer.parseInt(data.getText().toString());
                break;
            case R.id.g14:
                url = "https://fp550irvas.localtunnel.me/g1?cmd=74";
                break;
            case R.id.g15:
                url = "https://fp550irvas.localtunnel.me/g1?cmd=97";
                convert=true;
                break;
            case R.id.g16:
                url = "https://fp550irvas.localtunnel.me/g1?cmd=71";
                break;
            case R.id.g17:
                url = "https://fp550irvas.localtunnel.me/g1?cmd=111&data=1";
                break;
            case R.id.g18:
                url = "https://fp550irvas.localtunnel.me/g1?cmd=83";
                convert=true;
                break;
        }

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        result.setText("");
                        resultchar.setText("");
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
                                if(convert){
                                    resultcharold = resultchar.getText().toString();
                                    resultchar.setText(resultcharold + (char) Integer.parseInt(str));
                                }
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

    public void customcmd(View view){
        Intent intent = new Intent(getApplicationContext(),custom.class);
        startActivity(intent);
    }

    public void takephoto(View view){
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

    @Override
    public void onBackPressed() {

    }
}

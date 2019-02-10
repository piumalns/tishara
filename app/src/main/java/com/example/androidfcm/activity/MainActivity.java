package com.example.androidfcm.activity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.androidfcm.R;
import com.example.androidfcm.Service.MyFirebaseInstanceIDService;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    Button btngetToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btngetToken = (Button) findViewById(R.id.btngetToken);

        startService(new Intent(MainActivity.this, MyFirebaseInstanceIDService.class));
//
//        FirebaseMessaging.getInstance().subscribeToTopic("weather")
//                .addOnCompleteListener(new OnCompleteListener<Void>() {
//                    @Override
//                    public void onComplete(@NonNull Task<Void> task) {
//                        String msg = getString(R.string.msg_subscribed);
//                        if (!task.isSuccessful()) {
//                            msg = getString(R.string.msg_subscribe_failed);
//                        }
//                        Log.d(TAG, msg);
//                        Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
//                    }
//                });
        btngetToken.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                postToken();
            }
        });



   }

    public void postToken(String result){
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Notification");
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "";
        JSONObject body = new JSONObject();
        try {
            body.put("title","new mes");
            body.put("messa",)
//            body.put("password", "123456");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        final ProgressDialog dialog = new ProgressDialog(MainActivity.this);
        dialog.setMessage("Loading...");
        dialog.show();
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, body, new Response.Listener<JSONObject>() {


            @Override
            public void onResponse(JSONObject response) {
//                Toast.makeText(qrScaner.this, response.toString(), Toast.LENGTH_LONG).show();

                try {
                    final String machineId =  response.getString("id");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                builder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
//                        zXingScannerView.resumeCameraPreview(qrScaner.this);
                    }
                });
                builder.setMessage(response.toString());

                builder.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
//                        Intent startIntent = new Intent(qrScaner.this,MachineDetail.class);
//                        startActivity(startIntent);
                    }
                });
                AlertDialog alertDialog = builder.create();

                alertDialog.show();
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, error.getMessage(), Toast.LENGTH_LONG).show();
                dialog.dismiss();

                builder.setMessage(error.toString());
                AlertDialog alertDialog = builder.create();

                alertDialog.show();
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("Content-Type", "application/json");
                map.put("Accept", "application/json");
                map.put("X-Requested-With", "XMLHttpRequest");
                map.put("Authorization","key=AIzaSyC...akjgSX0e4{}");
                return map;
            }
        };
        queue.add(request);
        queue.start();

    }



    }

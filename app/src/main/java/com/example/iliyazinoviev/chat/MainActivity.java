package com.example.iliyazinoviev.chat;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {
    private Button btnConfirm;
    private Intent intent_to_rooms;
    private RequestQueue queue;
    private String email;
    private String name;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i("savedInstanceState", ""+(savedInstanceState == null));

        if (savedInstanceState == null){
            super.onCreate(savedInstanceState);
            intent_to_rooms = new Intent(this, RoomsActivity.class);
            queue = Volley.newRequestQueue(this);
            login();
        }
    }

    private void login(){
        AccountManager am = AccountManager.get(this);
        Account[] accounts = am.getAccountsByType("com.google");
        email =  accounts[0].name;
        Log.i("email", email);
        JSONObject json = new JSONObject();
        try {
            json.put("email",accounts[0].name);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String url = this.getResources().getString(R.string.url_login);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, json,
            new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        if(response.getBoolean("exist")) {
                            Rooms rooms = new Rooms(JSONParser.toList(response.getJSONArray("rooms")));
                            intent_to_rooms.putExtra(Rooms.class.getSimpleName(), rooms);
                            startActivity(intent_to_rooms);
                            finish();
                        }
                        else {
                            setContentView(R.layout.activity_main);
                            btnConfirm = findViewById(R.id.confirm);
                            View.OnClickListener heandlerBtnConfirm = new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    createLogin();
                                }
                            };
                            btnConfirm.setOnClickListener(heandlerBtnConfirm);
                        }
                    }catch (JSONException e){
                        e.printStackTrace();
                    }
                }
            },
            new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.i("String Response", "Error getting response");
            }
        });
        jsonObjectRequest.setTag("CHECK_EMAIL");
        queue.add(jsonObjectRequest);
    }

    private void createLogin() {
        name = ((EditText) findViewById(R.id.inputLogin)).getText().toString();
        JSONObject json = new JSONObject();
        try {
            json.put("email",email);
            json.put("name", name);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String url = this.getResources().getString(R.string.url_add_user);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, json,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            Rooms rooms = new Rooms(JSONParser.toList(response.getJSONArray("rooms")));
                            intent_to_rooms.putExtra(Rooms.class.getSimpleName(), rooms);
                            startActivity(intent_to_rooms);
                            finish();
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.i("String Response", "Error getting response");
                    }
                });
        jsonObjectRequest.setTag("ADD_USER");
        queue.add(jsonObjectRequest);
    }



}

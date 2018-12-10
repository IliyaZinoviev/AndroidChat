package com.example.iliyazinoviev.chat;

import android.accounts.AccountManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.Socket;

import org.json.JSONException;
import org.json.JSONObject;


public class LoginActivity extends AppCompatActivity {
    private Intent intent_to_rooms = new Intent();
    private String email;
    private String name;
    private Socket mSocket;
    private Rooms data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            Log.i("TAG!!!", "create");
            ChatApplication app = (ChatApplication) this.getApplication();
            mSocket = app.getSocket();
            email = AccountManager.get(this).getAccountsByType("com.google")[0].name;
            app.setEmail(email);
            mSocket.emit("login", email);
            mSocket.on("login", onLogin);
    }

    private Emitter.Listener onLogin = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            try {
                JSONObject res = (JSONObject) args[0];
//                Log.i("Users_name", res.toString());

                Log.i("TAG!!!", res.getJSONObject("data").getJSONArray("rooms").get(0).toString());
                data = new Rooms(JSONParser.toList(res.getJSONObject("data").getJSONArray("rooms")));
//                app.setDataForRoomsFragment(JSONParser.toMap(res.getJSONObject("data")));
                if (res.getBoolean("exist")) {
                    intent_to_rooms.putExtra(Rooms.class.getSimpleName(), data);
                    setResult(RESULT_OK, intent_to_rooms);
                    finish();
                } else {
                    Button btnConfirm = findViewById(R.id.confirm);
                    setContentView(R.layout.activity_main);
                    View.OnClickListener heandlerBtnConfirm = new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            name = ((EditText) findViewById(R.id.inputLogin)).getText().toString();
                            JSONObject json = new JSONObject();
                            try {
                                json.put("email",email);
                                json.put("name", name);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            mSocket.emit("add user", json);
                            //data.getData().put("userName", name);
                            intent_to_rooms.putExtra(Rooms.class.getSimpleName(), data);
                            setResult(RESULT_OK, intent_to_rooms);
                            finish();
                        }
                    };
                    btnConfirm.setOnClickListener(heandlerBtnConfirm);
                }
            }catch (JSONException e) {
                e.printStackTrace();
            }
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mSocket.off("login", onLogin);
    }
}

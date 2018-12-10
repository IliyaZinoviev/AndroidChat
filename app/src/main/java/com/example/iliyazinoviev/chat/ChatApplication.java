package com.example.iliyazinoviev.chat;

import android.app.Application;

import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;

import java.net.URISyntaxException;
import java.util.Map;

public class ChatApplication extends Application {
    private Map<String, Object> dataForRoomsFragment;

    private Socket mSocket;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    private String email;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    private String userName;
    {
        try {
            mSocket = IO.socket("http://192.168.1.215:3000");
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    public Socket getSocket() {
        return mSocket;
    }

    public Map<String, Object> getDataForRoomsFragment() {
        return dataForRoomsFragment;
    }

    public void setDataForRoomsFragment(Map<String, Object> dataForRoomsFragment) {
        this.dataForRoomsFragment = dataForRoomsFragment;
    }
}

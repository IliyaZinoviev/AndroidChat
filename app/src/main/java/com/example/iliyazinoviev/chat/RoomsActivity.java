package com.example.iliyazinoviev.chat;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class RoomsActivity extends AppCompatActivity {
    private static FragmentManager fragmentManager;
    private Socket mSocket;
    {
        try {
            mSocket = IO.socket("http://192.168.1.215:3000").connect();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rooms);
        Bundle arguments = getIntent().getExtras();
        final Rooms rooms;
        if(arguments!=null){
            if (savedInstanceState == null) {
                rooms = (Rooms) arguments.getSerializable(Rooms.class.getSimpleName());
                Bundle args = new Bundle();
                RoomsFragment roomsFragment = new RoomsFragment();
                args.putStringArrayList("USERS_NAMES", getArrayStringFromRoomsData(rooms.getData(), "user_name"));
                args.putStringArrayList("CHATS_NAMES", getArrayStringFromRoomsData(rooms.getData(), "chat_name"));
                args.putStringArrayList("CHATS_ID", getArrayStringFromRoomsData(rooms.getData(), "id"));
                roomsFragment.setArguments(args);
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.roomsFragment, roomsFragment)
                        .commit();
            }
        }
        mSocket.emit("event", "message");
    }

    private ArrayList<String> getArrayStringFromRoomsData(List<Object> data, String key){
        ArrayList<String> arrayList = new ArrayList<String>();
        for(Object el: data){
            arrayList.add(((HashMap<String, Object>)el).get(key).toString());
        }
        return arrayList;
    }
}




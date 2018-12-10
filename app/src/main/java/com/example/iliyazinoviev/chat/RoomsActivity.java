package com.example.iliyazinoviev.chat;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class RoomsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rooms);
        RoomsFragment roomsFragment = new RoomsFragment();
        getSupportFragmentManager().beginTransaction()
                .add(R.id.roomsFragment, roomsFragment)
                .commit();
    }
}




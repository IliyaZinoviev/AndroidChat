package com.example.iliyazinoviev.chat;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.github.nkzawa.socketio.client.Socket;

import java.util.ArrayList;


public class RoomsFragment extends ListFragment {

    private ArrayList<Item> items = new ArrayList<Item>();
    private ChatListAdapter mAdapter;
    private Socket mSocket;
    private String mUsername;
    private String mEmail;
    private static final int REQUEST_LOGIN = 0;

    public RoomsFragment(){

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mAdapter = new ChatListAdapter(getActivity(), R.layout.item, items);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSocket = ((ChatApplication) getActivity().getApplication()).getSocket();
        mSocket.connect();
        startSignIn();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.rooms_list, null);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        Intent intent_to_messages;
        intent_to_messages = new Intent(getActivity(), MessagesActivity.class);
        startActivity(intent_to_messages);
    }

    private void startSignIn() {
        mUsername = null;
        Intent intent = new Intent(getActivity(), LoginActivity.class);
        startActivityForResult(intent, REQUEST_LOGIN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (Activity.RESULT_OK != resultCode) {
            getActivity().finish();
            return;
        }
        Rooms rooms = (Rooms)data.getExtras().getSerializable(Rooms.class.getSimpleName());
        Log.i("work!", rooms.getUsers().get(4));
        for (int i = 0; i < rooms.getChats().size(); i++)
            items.add(new Item(rooms.getIds().get(i), rooms.getChats().get(i), rooms.getUsers().get(i)));
        setListAdapter(mAdapter);
    }
}

package com.example.iliyazinoviev.chat;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;


public class RoomsFragment extends ListFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.i("onCreateView1", "onCreateView1");
        Bundle args = getArguments();
        Log.i("chats_id", getArguments() == null ? "true" : "" + args.getStringArrayList("CHATS_NAMES").size());
        ArrayList<Item> items = new ArrayList<Item>();
        if (args != null){
            ArrayList<String> ids = args.getStringArrayList("CHATS_ID");
            ArrayList<String> users = args.getStringArrayList("CHATS_NAMES");
            ArrayList<String> chats = args.getStringArrayList("USERS_NAMES");

            for (int i = 0; i < ids.size(); i++)
                items.add(new Item(ids.get(i), chats.get(i), users.get(i)));
        }
        ChatListAdapter adapter = new ChatListAdapter(getActivity(),
                R.layout.item, items);
        setListAdapter(adapter);
        return inflater.inflate(R.layout.items_list, null);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        Intent intent_to_room;
        intent_to_room = new Intent(getActivity(), RoomActivity.class);
        startActivity(intent_to_room);
    }
}

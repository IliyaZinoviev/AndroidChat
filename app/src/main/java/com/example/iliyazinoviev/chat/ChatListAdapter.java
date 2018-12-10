package com.example.iliyazinoviev.chat;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class ChatListAdapter extends ArrayAdapter<Item> {

    private LayoutInflater inflater;
    private int layout;
    private List<Item> items;

    public ChatListAdapter(Context context, int resource, List<Item> items) {
        super(context, resource, items);
        this.items = items;
        this.layout = resource;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = inflater.inflate(this.layout, parent, false);

        TextView str1 = (TextView) view.findViewById(R.id.str1);
        TextView str2 = (TextView) view.findViewById(R.id.str2);

        Item item = items.get(position);

        str1.setText(item.getStr1());
        str2.setText(item.getStr2());

        return view;
    }
}
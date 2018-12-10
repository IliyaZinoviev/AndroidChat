package com.example.iliyazinoviev.chat;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Rooms implements Serializable {
    private ArrayList<String> ids;
    private ArrayList<String> users;
    private ArrayList<String> chats;


    public Rooms(List<Object> data) {

        users = getArrayString(data, "user_name");
        chats = getArrayString(data, "chat_name");
        ids = getArrayString(data, "id");

    }

    private ArrayList<String> getArrayString(List<Object> data, String key){
        ArrayList<String> arrayList = new ArrayList<String>();
        for(Object el: data){
            arrayList.add(((HashMap<String, Object>)el).get(key).toString());
        }
        return arrayList;
    }

    public ArrayList<String> getIds() {
        return ids;
    }

    public ArrayList<String> getUsers() {
        return users;
    }

    public ArrayList<String> getChats() {
        return chats;
    }


}
package com.example.iliyazinoviev.chat;

import java.io.Serializable;
import java.util.List;

public class Rooms implements Serializable {

    public List<Object> getData() {
        return data;
    }

    public void setData(List<Object> data) {
        this.data = data;
    }

    private List<Object> data;

    public Rooms(List<Object> data) {
        this.data = data;
    }


}
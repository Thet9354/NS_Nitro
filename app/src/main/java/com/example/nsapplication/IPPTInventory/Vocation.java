package com.example.nsapplication.IPPTInventory;

import java.io.Serializable;

public class Vocation implements Serializable {

    private String name;

    public Vocation(){

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

package com.example.android.testandroiddev2018.entities;

import android.graphics.drawable.Drawable;

public class App {

    private String name;
    private String lastTimeStamp;
    private Drawable image;

    public App(String name, String lastTimeStamp, Drawable image) {
        this.name = name;
        this.lastTimeStamp = lastTimeStamp;
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public String getLastTimeStamp() {
        return lastTimeStamp;
    }

    public Drawable getImage() {
        return image;
    }
}

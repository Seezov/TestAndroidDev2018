package com.example.android.testandroiddev2018.entities;

import android.graphics.drawable.Drawable;

public class App {

    private String name;
    private String packageName;
    private String lastTimeStamp;
    private Drawable image;

    public App(String name, String packageName, String lastTimeStamp, Drawable image) {
        this.name = name;
        this.packageName = packageName;
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

    public String getPackageName() {
        return packageName;
    }
}

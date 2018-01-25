package com.example.android.testandroiddev2018;

import android.app.Activity;

public interface AppLifecycle {
    void onAppBackgrounded();
    void onAppForegrounded(Activity activity);
}

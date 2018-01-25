package com.example.android.testandroiddev2018;

import android.app.Activity;
import android.app.Application;
import android.content.Intent;

import com.example.android.testandroiddev2018.activities.SplashActivity;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

public class MyApp extends Application implements AppLifecycle {
    @Override
    public void onCreate() {
        super.onCreate();
        AppLifecycleHandler lifeCycleHandler = new AppLifecycleHandler(this);
        registerLifecycleHandler(lifeCycleHandler);
    }

    private void registerLifecycleHandler(AppLifecycleHandler lifeCycleHandler) {
        registerActivityLifecycleCallbacks(lifeCycleHandler);
        registerComponentCallbacks(lifeCycleHandler);
    }

    @Override
    public void onAppBackgrounded() {
    }

    // Call EnterPassword activity every time user brings up the app from Background
    @Override
    public void onAppForegrounded(Activity activity) {
        Intent intent = new Intent(activity, SplashActivity.class);
        intent.setFlags(FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}

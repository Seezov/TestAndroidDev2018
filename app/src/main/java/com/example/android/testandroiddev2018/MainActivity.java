package com.example.android.testandroiddev2018;

import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.content.Context;
import android.content.Intent;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.android.testandroiddev2018.entities.App;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    public static final String APP_TAG = "TEST_APP";

    RecyclerView recyclerView;
    AppAdapter appAdapter;

    List<App> apps;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        apps = new ArrayList<>();

        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        apps.add(new App("YOUTUBE", "WATCH CONTENT",R.drawable.youtube));
        apps.add(new App("Skype", "TALK CALL",R.drawable.skype));
        apps.add(new App("ANDRIOD", "HAHAHA",R.drawable.android));

        appAdapter = new AppAdapter(this,apps);
        recyclerView.setAdapter(appAdapter);
        //checkAppUsages();
    }

    private void checkAppUsages() {
        UsageStatsManager usageStatsManager = (UsageStatsManager) getSystemService(Context.USAGE_STATS_SERVICE);

        long time = System.currentTimeMillis();
        long delta = 7 * 24 * 60 * 60 * 1000;

        List<UsageStats> usageStatsList = usageStatsManager.queryUsageStats(UsageStatsManager.INTERVAL_DAILY, time - delta, time);

        Intent intent = new Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS);
        startActivity(intent);

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US);

        for (UsageStats stats : usageStatsList) {
            Log.i(APP_TAG, "*********************************************************************");
            Log.i(APP_TAG, "package name: " + stats.getPackageName());
            Log.i(APP_TAG, "first time stamp: " + dateFormat.format(stats.getFirstTimeStamp()));
            Log.i(APP_TAG, "last time stamp: " + dateFormat.format(stats.getLastTimeStamp()));
            Log.i(APP_TAG, "total time in foreground: " + stats.getTotalTimeInForeground() + "ms");
        }
    }
}

package com.example.android.testandroiddev2018.activities;

import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.android.testandroiddev2018.adapters.AppAdapter;
import com.example.android.testandroiddev2018.R;
import com.example.android.testandroiddev2018.listeners.RecyclerViewClickListener;
import com.example.android.testandroiddev2018.entities.App;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements RecyclerViewClickListener {

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

        checkAppUsages();

        appAdapter = new AppAdapter(this, apps, this);
        recyclerView.setAdapter(appAdapter);
    }

    private void checkAppUsages() {
        UsageStatsManager usageStatsManager = (UsageStatsManager) getSystemService(Context.USAGE_STATS_SERVICE);
        Calendar prevYear = Calendar.getInstance();
        prevYear.add(Calendar.YEAR, -1);
        long start = prevYear.getTimeInMillis();
        long end = System.currentTimeMillis();
        List<UsageStats> usageStatsList = usageStatsManager.queryUsageStats(UsageStatsManager.INTERVAL_BEST, start, end);
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US);
        Drawable icon = null;
        List<String> appNames = new ArrayList<>();
        for (int i = usageStatsList.size() - 1; i >= 0; i--) {
            try {
                icon = getPackageManager().getApplicationIcon(usageStatsList.get(i).getPackageName());
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }
            String desc;
            if (usageStatsList.get(i).getLastTimeUsed() < start) {
                desc = "This app had not been used since " + dateFormat.format(start);
            } else {
                desc = "Last time used: " + dateFormat.format(usageStatsList.get(i).getLastTimeUsed());
            }

            App newApp = new App(usageStatsList.get(i).getPackageName(), desc, icon);
            if (!appNames.contains(newApp.getName())) {
                appNames.add(newApp.getName());
                apps.add(newApp);
            }
        }
    }

    @Override
    public void recyclerViewListClicked(View v, int position) {
        String item = apps.get(position).getName();
        Intent i = new Intent(getApplicationContext(), DetailedAppInfoActivity.class);
        i.putExtra("appName", item);
        startActivity(i);
    }
}

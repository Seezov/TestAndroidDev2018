package com.example.android.testandroiddev2018.Activities;

import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.method.CharacterPickerDialog;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.android.testandroiddev2018.AppAdapter;
import com.example.android.testandroiddev2018.R;
import com.example.android.testandroiddev2018.RecyclerViewClickListener;
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

        appAdapter = new AppAdapter(this,apps,this);
        recyclerView.setAdapter(appAdapter);

    }

    private void checkAppUsages() {
        UsageStatsManager usageStatsManager = (UsageStatsManager) getSystemService(Context.USAGE_STATS_SERVICE);
        Calendar today = Calendar.getInstance();
        Calendar prevYear = Calendar.getInstance();
        prevYear.add(Calendar.YEAR, -1);
        List<UsageStats> usageStatsList = usageStatsManager.queryUsageStats(UsageStatsManager.INTERVAL_DAILY, prevYear.getTimeInMillis(), today.getTimeInMillis());
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US);
        Drawable icon = null;
        for (UsageStats stats : usageStatsList) {
            try {
                icon = getPackageManager().getApplicationIcon(stats.getPackageName());
            }
            catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }
            apps.add(new App(stats.getPackageName(),
                    "Last time stamp: " + dateFormat.format(stats.getLastTimeStamp()),
                    icon));
        }


    }

    @Override
    public void recyclerViewListClicked(View v, int position) {
        String item = apps.get(position).getName();
        Intent i = new Intent(getApplicationContext(), DetailedAppInfoActivity.class);
        i.putExtra("appName",item);
        startActivity(i);
    }
}

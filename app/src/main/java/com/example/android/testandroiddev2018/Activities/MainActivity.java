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

        checkAppUsages();

        appAdapter = new AppAdapter(this,apps,this);
        recyclerView.setAdapter(appAdapter);

    }

    private void checkAppUsages() {
        UsageStatsManager usageStatsManager = (UsageStatsManager) getSystemService(Context.USAGE_STATS_SERVICE);
        long currentTime = System.currentTimeMillis();
        Calendar prevYear = Calendar.getInstance();
        prevYear.add(Calendar.YEAR, -1);
        List<UsageStats> usageStatsList = usageStatsManager.queryUsageStats(UsageStatsManager.INTERVAL_DAILY, prevYear.getTimeInMillis(), currentTime);

        Intent intent = new Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS);
        startActivity(intent);

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US);

        for (UsageStats stats : usageStatsList) {
            Log.i(APP_TAG, "*********************************************************************");
            Log.i(APP_TAG, "package name: " + stats.getPackageName());
            Log.i(APP_TAG, "first time stamp: " + dateFormat.format(stats.getFirstTimeStamp()));
            Log.i(APP_TAG, "last time stamp: " + dateFormat.format(stats.getLastTimeStamp()));
            Log.i(APP_TAG, "total time in foreground: " + stats.getTotalTimeInForeground() + "ms");
            Drawable icon = null;
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
        Toast.makeText(this, item, Toast.LENGTH_LONG).show();
        Intent i = new Intent(getApplicationContext(), DetailedAppInfoActivity.class);
        i.putExtra("appName","value");
        startActivity(i);
    }
}

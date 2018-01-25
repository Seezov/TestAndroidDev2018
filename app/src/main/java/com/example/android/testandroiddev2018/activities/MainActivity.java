package com.example.android.testandroiddev2018.activities;

import android.Manifest;
import android.app.AppOpsManager;
import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

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
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
    }

    /*@Override
    protected void onResume() {
        super.onResume();
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.PACKAGE_USAGE_STATS)
                == PackageManager.PERMISSION_DENIED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.PACKAGE_USAGE_STATS)) {
                Toast.makeText(MainActivity.this, "LOOOOOOOOOOL", Toast.LENGTH_SHORT).show();
                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

            } else {

                // No explanation needed, we can request the permission.

                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.READ_CONTACTS},
                        1);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        }
    }*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        apps = new ArrayList<>();


        boolean granted;
        AppOpsManager appOps = (AppOpsManager) this.getSystemService(Context.APP_OPS_SERVICE);
        int mode = appOps.checkOpNoThrow(AppOpsManager.OPSTR_GET_USAGE_STATS,
                android.os.Process.myUid(), this.getPackageName());

        if (mode == AppOpsManager.MODE_DEFAULT) {
            granted = (this.checkCallingOrSelfPermission(android.Manifest.permission.PACKAGE_USAGE_STATS) == PackageManager.PERMISSION_GRANTED);
        } else {
            granted = (mode == AppOpsManager.MODE_ALLOWED);
        }

        if(granted) {
            Toast.makeText(MainActivity.this, "Permission GRANTED", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(MainActivity.this, "You MUST grant PACKAGE_USAGE_STATS permission!", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS);
            startActivity(intent);
            finish();
        }



        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        checkAppUsages();

        appAdapter = new AppAdapter(this, apps, this);
        recyclerView.setAdapter(appAdapter);
    }

    /*@Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1: {
                // If request is cancelled, the result arrays are empty.
                if (!(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    Toast.makeText(MainActivity.this, "Permission denied to read your app stats", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS);
                    startActivity(intent);
                }
                // other 'case' lines to check for other
                // permissions this app might request
            }
        }
    }*/

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

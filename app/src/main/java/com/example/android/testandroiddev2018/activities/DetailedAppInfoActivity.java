package com.example.android.testandroiddev2018.activities;

import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.testandroiddev2018.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Objects;


public class DetailedAppInfoActivity  extends AppCompatActivity {

    String currentAppName;
    Calendar today;
    Calendar pastTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.datailed_info_layout);
        currentAppName = getIntent().getStringExtra("appName");
        UsageStats dailyStats = getInfoAboutApp(currentAppName, UsageStatsManager.INTERVAL_DAILY);
        UsageStats monthlyStats = getInfoAboutApp(currentAppName, UsageStatsManager.INTERVAL_MONTHLY);
        UsageStats yearlyStats = getInfoAboutApp(currentAppName, UsageStatsManager.INTERVAL_YEARLY);
        UsageStats mainStats = null;
        TextView usedDayTV, usedMonthTV, usedYearTV;
        usedDayTV = findViewById(R.id.textViewUsedDay);
        usedMonthTV = findViewById(R.id.textViewUsedMonth);
        usedYearTV = findViewById(R.id.textViewUsedYear);
        if (null == dailyStats && null == monthlyStats && null == yearlyStats) {
            Toast.makeText(this, "There is no stats for this app", Toast.LENGTH_SHORT).show();
        } else {
            if (null == dailyStats) {
                Toast.makeText(this, "There is no daily stats for this app", Toast.LENGTH_SHORT).show();
            } else {
                addGeneralToLayout(dailyStats, usedDayTV, "day");
                mainStats = dailyStats;
            }
            if (null == monthlyStats) {
                Toast.makeText(this, "There is no monthly stats for this app", Toast.LENGTH_SHORT).show();

            } else {
                addGeneralToLayout(monthlyStats, usedMonthTV, "month");
                mainStats = dailyStats;
            }
            if (null == yearlyStats) {
                Toast.makeText(this, "There is no yearly stats for this app", Toast.LENGTH_SHORT).show();
            } else {
                addGeneralToLayout(yearlyStats, usedYearTV, "year");
                mainStats = yearlyStats;
            }
            addMainDataToLayout(mainStats);
        }
    }

    private void addGeneralToLayout(UsageStats stats, TextView textView, String interval) {
        long totalSecs = stats.getTotalTimeInForeground() / 1000;
        String timeOfUseString = getTimeString(totalSecs);
        textView.setText("Used for the last " + interval + " : " + timeOfUseString);
    }

    private UsageStats getInfoAboutApp(String currentAppName, int interval) {
        UsageStatsManager usageStatsManager = (UsageStatsManager) getSystemService(Context.USAGE_STATS_SERVICE);
        today = Calendar.getInstance();
        pastTime = Calendar.getInstance();
        switch (interval) {
            case 0:
                pastTime.add(Calendar.DAY_OF_YEAR, -1);
                break;
            case 2:
                pastTime.add(Calendar.MONTH, -1);
                break;
            case 3:
                pastTime.add(Calendar.YEAR, -1);
                break;
        }
        List<UsageStats> appStats = usageStatsManager.queryUsageStats(interval, pastTime.getTimeInMillis(), today.getTimeInMillis());
        UsageStats currentAppStats = null;
        for (int i = appStats.size() - 1; i >= 0; i--) {
            if (Objects.equals(appStats.get(i).getPackageName(), currentAppName)) {
                currentAppStats = appStats.get(i);
                break;
            }
        }
        return currentAppStats;
    }

    private void addMainDataToLayout(UsageStats mainStats) {
        TextView appNameTV, lastTimeUsedTV;
        ImageView appImageIM;

        appImageIM = findViewById(R.id.imageViewAppImage);
        appNameTV = findViewById(R.id.textViewAppName);

        lastTimeUsedTV = findViewById(R.id.textViewLastTimeUsed);
        Drawable icon = null;
        try {
            icon = getPackageManager().getApplicationIcon(mainStats.getPackageName());
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        appImageIM.setImageDrawable(icon);
        appNameTV.setText(mainStats.getPackageName());
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US);

        String desc;
        if (mainStats.getLastTimeUsed() < pastTime.getTimeInMillis()) {
            desc = "This app had not been used since " + dateFormat.format(pastTime.getTimeInMillis());
        } else {
            desc = "Last time used: " + dateFormat.format(mainStats.getLastTimeUsed());
        }
        lastTimeUsedTV.setText(desc);
    }

    private String getTimeString(long totalSecs) {
        long hours = totalSecs / 3600;
        long minutes = (totalSecs % 3600) / 60;
        long seconds = totalSecs % 60;
        return String.format("%02d:%02d:%02d", hours, minutes, seconds);
    }
}

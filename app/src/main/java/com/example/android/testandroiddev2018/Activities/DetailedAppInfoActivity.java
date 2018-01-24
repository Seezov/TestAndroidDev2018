package com.example.android.testandroiddev2018.Activities;

import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.testandroiddev2018.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Objects;


public class DetailedAppInfoActivity  extends AppCompatActivity {

    String currentAppName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.datailed_info_layout);
        currentAppName = getIntent().getStringExtra("appName");
        UsageStats dailyStats = getInfoAboutApp(currentAppName, UsageStatsManager.INTERVAL_DAILY);
        UsageStats monthlyStats = getInfoAboutApp(currentAppName, UsageStatsManager.INTERVAL_MONTHLY);
        UsageStats yearlyStats = getInfoAboutApp(currentAppName, UsageStatsManager.INTERVAL_YEARLY);
        addDataToLayout(dailyStats, monthlyStats, yearlyStats);
    }

    private UsageStats getInfoAboutApp(String currentAppName, int interval) {
        UsageStatsManager usageStatsManager = (UsageStatsManager) getSystemService(Context.USAGE_STATS_SERVICE);
        Calendar today = Calendar.getInstance();
        Calendar prevYear = Calendar.getInstance();
        switch (interval) {
            case 0:
                prevYear.add(Calendar.DAY_OF_YEAR, -1);
                break;
            case 2:
                prevYear.add(Calendar.MONTH, -1);
                break;
            case 3:
                prevYear.add(Calendar.YEAR, -1);
                break;
        }
        List<UsageStats> appStats = usageStatsManager.queryUsageStats(interval, prevYear.getTimeInMillis(), today.getTimeInMillis());
        UsageStats currentAppStats = null;
        for (UsageStats stats: appStats) {
            if(Objects.equals(stats.getPackageName(), currentAppName)){
                currentAppStats = stats;
                break;
            }
        }
       return currentAppStats;
    }

    private void addDataToLayout(UsageStats dailyStats, UsageStats monthlyStats, UsageStats yearlyStats) {
        TextView appNameTV, usedDayTV, usedMonthTV, usedYearTV, lastTimeUsedTV;
        ImageView appImageIM;

        appImageIM = findViewById(R.id.imageViewAppImage);
        appNameTV = findViewById(R.id.textViewAppName);

        lastTimeUsedTV = findViewById(R.id.textViewLastTimeUsed);
        usedDayTV = findViewById(R.id.textViewUsedDay);
        usedMonthTV = findViewById(R.id.textViewUsedMonth);
        usedYearTV = findViewById(R.id.textViewUsedYear);
        Drawable icon = null;
        try {
            icon = getPackageManager().getApplicationIcon(dailyStats.getPackageName());
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        appImageIM.setImageDrawable(icon);
        appNameTV.setText(dailyStats.getPackageName());
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US);
        lastTimeUsedTV.setText("Last time used: " + dateFormat.format(dailyStats.getLastTimeStamp()));
        long totalDaySecs = dailyStats.getTotalTimeInForeground() / 1000;
        long totalMonthSecs = monthlyStats.getTotalTimeInForeground() / 1000;
        long totalYearSecs = yearlyStats.getTotalTimeInForeground() / 1000;
        String timeOfUseStringDay = getTimeString(totalDaySecs);
        String timeOfUseStringMonth = getTimeString(totalMonthSecs);
        String timeOfUseStringYear = getTimeString(totalYearSecs);
        usedDayTV.setText("Used for the last day : " + timeOfUseStringDay);
        usedMonthTV.setText("Used for the last month : " + timeOfUseStringMonth);
        usedYearTV.setText("Used for the last year : " + timeOfUseStringYear);
        // Added detailed app info to the second activity
    }

    private String getTimeString(long totalSecs) {
        long hours = totalSecs / 3600;
        long minutes = (totalSecs % 3600) / 60;
        long seconds = totalSecs % 60;
        return String.format("%02d:%02d:%02d", hours, minutes, seconds);
    }
}

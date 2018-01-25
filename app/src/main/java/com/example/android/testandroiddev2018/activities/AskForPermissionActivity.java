package com.example.android.testandroiddev2018.activities;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.android.testandroiddev2018.R;

public class AskForPermissionActivity extends AppCompatActivity {

    Button settingsButton;

    // Deactivating back button
    @Override
    public void onBackPressed() {
    }

    // This is needed to check if permission is given every time we return to this activity
    @Override
    protected void onRestart() {
        super.onRestart();
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ask_for_permission);

        settingsButton = findViewById(R.id.goToSettingsButton);

        settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS);
                startActivity(intent);
            }
        });
    }
}

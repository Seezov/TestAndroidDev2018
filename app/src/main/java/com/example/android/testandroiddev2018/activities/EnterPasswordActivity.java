package com.example.android.testandroiddev2018.activities;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.android.testandroiddev2018.R;

public class EnterPasswordActivity extends AppCompatActivity {

    EditText enterPass;
    Button button;

    String pass;

    @Override
    public void onBackPressed() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_password);

        SharedPreferences settings = getSharedPreferences("PREFS", 0);
        pass = settings.getString("password", "");

        enterPass = findViewById(R.id.mainPassEditText);
        button = findViewById(R.id.confirmPassButton);


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String enteredPass = enterPass.getText().toString();
                if (enteredPass.equals(pass)) {
                    finish();
/*                    Intent intent = new Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS);
                    startActivity(intent);*/
                } else {
                    Toast.makeText(EnterPasswordActivity.this, "Incorrect password", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}


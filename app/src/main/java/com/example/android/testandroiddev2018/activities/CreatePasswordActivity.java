package com.example.android.testandroiddev2018.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.android.testandroiddev2018.R;

public class CreatePasswordActivity extends AppCompatActivity {

    EditText enterPass, repeatPass;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_password);

        enterPass = findViewById(R.id.passEditText);
        repeatPass = findViewById(R.id.passRepeatEditText);
        button = findViewById(R.id.confirmNewPassButton);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String pass = enterPass.getText().toString();
                String repeatedPass = repeatPass.getText().toString();

                if(pass.equals("") || repeatedPass.equals("")){
                    Toast.makeText(CreatePasswordActivity.this,"No password entered",Toast.LENGTH_SHORT).show();
                } else{
                    if(pass.equals(repeatedPass)){
                        if(pass.length()== 4) {
                            SharedPreferences settings = getSharedPreferences("PREFS", 0);
                            SharedPreferences.Editor editor = settings.edit();
                            editor.putString("password", pass);
                            editor.apply();

                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                            startActivity(intent);
                            finish();
                        } else{
                            Toast.makeText(CreatePasswordActivity.this,"Passwords must be 4 symbols long",Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(CreatePasswordActivity.this,"Passwords do not match",Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
}

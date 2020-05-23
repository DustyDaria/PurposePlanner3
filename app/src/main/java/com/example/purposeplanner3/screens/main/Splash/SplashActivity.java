package com.example.purposeplanner3.screens.main.Splash;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.example.purposeplanner3.R;
import com.example.purposeplanner3.screens.main.FireBase.SignupActivity;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent (SplashActivity.this, SignupActivity.class);
                startActivity(i);
                finish();
            }
        }, 3*1000);
    }
}

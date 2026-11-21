package com.example.todoapplication.modules.splash;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.todoapplication.R;
import com.example.todoapplication.core.data.CacheHelper;
import com.example.todoapplication.core.functions.HideStatusAndNavBar;
import com.example.todoapplication.core.helpers.Constants;
import com.example.todoapplication.modules.auth.view.LoginActivity;
import com.example.todoapplication.modules.onboarding.view.OnboardingActivity;

@SuppressLint("CustomSplashScreen")
public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_splash);
        HideStatusAndNavBar.hide(this);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.splash), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        CacheHelper.init(this);

        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            boolean isOnboardingCompleted = CacheHelper.getBoolean(Constants.ONBOARDING_KEY, false);

            Class<?> destination = isOnboardingCompleted
                    ? LoginActivity.class
                    : OnboardingActivity.class;

            Intent intent = new Intent(SplashActivity.this, destination);
            startActivity(intent);
            finish();
        }, 2500);
    }
}
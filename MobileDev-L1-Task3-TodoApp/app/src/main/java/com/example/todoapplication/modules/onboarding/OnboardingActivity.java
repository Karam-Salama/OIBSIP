package com.example.todoapplication.modules.onboarding;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.todoapplication.R;
import com.example.todoapplication.core.HideStatusAndNavBar;
import com.example.todoapplication.modules.auth.LoginActivity;
import com.example.todoapplication.modules.auth.RegisterActivity;
import com.google.android.material.button.MaterialButton;

public class OnboardingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_onboarding);
        HideStatusAndNavBar.hide(this);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.onboarding), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        MaterialButton btnGetStarted = findViewById(R.id.btnGetStarted);
        TextView tvLogin = findViewById(R.id.tvLogin);

        // Get Started -> Register
        btnGetStarted.setOnClickListener(v ->
                startActivity(new Intent(this, RegisterActivity.class)));

        // Log in -> Login
        tvLogin.setOnClickListener(v ->
                startActivity(new Intent(this, LoginActivity.class)));
    }
}
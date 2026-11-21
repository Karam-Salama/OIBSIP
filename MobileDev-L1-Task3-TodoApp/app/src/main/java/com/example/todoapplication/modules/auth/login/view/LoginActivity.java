package com.example.todoapplication.modules.auth.login.view;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.todoapplication.modules.auth.register.view.RegisterActivity;
import com.example.todoapplication.modules.home.MainActivity;
import com.example.todoapplication.R;
import com.example.todoapplication.core.functions.HideStatusAndNavBar;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        HideStatusAndNavBar.hide(this);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.layoutLogin), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Login button -> MainActivity
        findViewById(R.id.btnLogin).setOnClickListener(v -> {
            startActivity(new Intent(this, MainActivity.class));
            finish();
        });

        // "Don't have an account? Sign up" -> RegisterActivity
        findViewById(R.id.tvSignUp).setOnClickListener(v -> {
            startActivity(new Intent(this, RegisterActivity.class));
            finish();
        });
    }
}
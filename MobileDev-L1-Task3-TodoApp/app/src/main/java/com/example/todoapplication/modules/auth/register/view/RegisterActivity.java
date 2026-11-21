package com.example.todoapplication.modules.auth.register.view;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.todoapplication.modules.auth.login.view.LoginActivity;
import com.example.todoapplication.modules.home.MainActivity;
import com.example.todoapplication.R;
import com.example.todoapplication.core.functions.HideStatusAndNavBar;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);
        HideStatusAndNavBar.hide(this);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.layoutRegister), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        EditText etName = findViewById(R.id.et_Name);

        // Sign up button -> MainActivity
        findViewById(R.id.btnRegister).setOnClickListener(v -> {
            Intent intent = new Intent(this, MainActivity.class);
            String name = etName.getText().toString().trim();
            if (!name.isEmpty()) {
                intent.putExtra("user_name", name);
            }
            startActivity(intent);
            finish();
        });

        // "Already have an account? Log in" -> LoginActivity
        findViewById(R.id.tvLogin).setOnClickListener(v -> {
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        });
    }
}
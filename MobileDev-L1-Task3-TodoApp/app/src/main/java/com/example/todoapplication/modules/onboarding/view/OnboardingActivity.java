package com.example.todoapplication.modules.onboarding.view;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;

import com.example.todoapplication.R;
import com.example.todoapplication.core.data.CacheHelper;
import com.example.todoapplication.core.functions.HideStatusAndNavBar;
import com.example.todoapplication.modules.auth.view.LoginActivity;
import com.example.todoapplication.modules.auth.view.RegisterActivity;
import com.example.todoapplication.modules.onboarding.model.OnboardingRepository;
import com.example.todoapplication.modules.onboarding.viewmodel.OnboardingViewModel;
import com.example.todoapplication.modules.onboarding.viewmodel.OnboardingViewModelFactory;
import com.google.android.material.button.MaterialButton;

public class OnboardingActivity extends AppCompatActivity {

    private MaterialButton btnGetStarted;
    private TextView tvLogin;
    private OnboardingViewModel viewModel;

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

        CacheHelper.init(this);
        setupViews();
        setupViewModel();
        observeNavigationEvents();
        setupClickListeners();
    }

    private void setupViews() {
        btnGetStarted = findViewById(R.id.btnGetStarted);
        tvLogin = findViewById(R.id.tvLogin);
    }

    private void setupViewModel() {
        OnboardingRepository repository = new OnboardingRepository();
        OnboardingViewModelFactory factory = new OnboardingViewModelFactory(repository);
        viewModel = new ViewModelProvider(this, factory).get(OnboardingViewModel.class);
    }

    private void observeNavigationEvents() {
        viewModel.navigationEvent.observe(this, destination -> {
            if (destination == null) return;

            switch (destination) {
                case REGISTER:
                    navigateTo(RegisterActivity.class);
                    break;
                case LOGIN:
                    navigateTo(LoginActivity.class);
                    break;
            }
        });
    }

    private void setupClickListeners() {
        btnGetStarted.setOnClickListener(v -> viewModel.handleGetStartedClick());
        tvLogin.setOnClickListener(v -> viewModel.handleLoginClick());
    }

    private void navigateTo(Class<?> destination) {
        startActivity(new Intent(this, destination));
        finish();
    }
}
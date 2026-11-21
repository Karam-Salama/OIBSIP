package com.example.todoapplication.modules.auth.view;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;

import com.example.todoapplication.R;
import com.example.todoapplication.core.functions.HideStatusAndNavBar;
import com.example.todoapplication.core.functions.Validation;
import com.example.todoapplication.modules.auth.model.AuthRepository;
import com.example.todoapplication.modules.auth.viewmodel.AuthViewModelFactory;
import com.example.todoapplication.modules.auth.viewmodel.ForgetPasswordState;
import com.example.todoapplication.modules.auth.viewmodel.ForgetPasswordViewModel;
import com.google.android.material.snackbar.Snackbar;

public class ForgetPasswordActivity extends AppCompatActivity {

    private EditText etEmail;
    private Button btnResetPassword;
    private ImageButton btnBack;
    private ForgetPasswordViewModel viewModel;
    private Snackbar loadingSnackbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_forget_password);
        HideStatusAndNavBar.hide(this);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.forgetPassword), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        setupViews();
        setupViewModel();
        setupClickListeners();
        observeResetState();
    }

    private void setupViews() {
        etEmail = findViewById(R.id.etEmail);
        btnResetPassword = findViewById(R.id.btn_resetPassword);
        btnBack = findViewById(R.id.btnBack);
    }

    private void setupViewModel() {
        AuthRepository repository = new AuthRepository();
        AuthViewModelFactory factory = new AuthViewModelFactory(repository);
        viewModel = new ViewModelProvider(this, factory).get(ForgetPasswordViewModel.class);
    }

    private void setupClickListeners() {
        btnResetPassword.setOnClickListener(v -> {
            if (validateInputs()) {
                String email = etEmail.getText().toString().trim();
                viewModel.sendPasswordResetEmail(email);
            }
        });

        btnBack.setOnClickListener(v -> {
            Intent intent = new Intent(ForgetPasswordActivity.this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
            finish();
        });    }

    private boolean validateInputs() {
        String email = etEmail.getText().toString().trim();
        String emailError = Validation.validateEmail(email);
        etEmail.setError(emailError);
        return emailError == null;
    }

    private void observeResetState() {
        viewModel.resetState.observe(this, state -> {
            if (state instanceof ForgetPasswordState.Loading) {
                btnResetPassword.setEnabled(false);
                showLoadingSnackbar(((ForgetPasswordState.Loading) state).message);
            } else if (state instanceof ForgetPasswordState.Success) {
                btnResetPassword.setEnabled(true);
                dismissLoadingSnackbar();
                showSuccessDialog(((ForgetPasswordState.Success) state).message);
            } else if (state instanceof ForgetPasswordState.Error) {
                btnResetPassword.setEnabled(true);
                dismissLoadingSnackbar();
                showErrorSnackbar(((ForgetPasswordState.Error) state).message);
            }
        });
    }

    private void showLoadingSnackbar(String message) {
        dismissLoadingSnackbar();
        loadingSnackbar = Snackbar.make(findViewById(android.R.id.content), message, Snackbar.LENGTH_INDEFINITE);
        loadingSnackbar.show();
    }

    private void dismissLoadingSnackbar() {
        if (loadingSnackbar != null && loadingSnackbar.isShown()) {
            loadingSnackbar.dismiss();
        }
    }

    private void showSuccessDialog(String message) {
        new AlertDialog.Builder(this)
                .setMessage(message)
                .setCancelable(false)
                .setPositiveButton(android.R.string.ok, (dialog, which) -> finish())
                .show();
    }

    private void showErrorSnackbar(String message) {
        Snackbar.make(findViewById(android.R.id.content), message, Snackbar.LENGTH_LONG).show();
    }
}
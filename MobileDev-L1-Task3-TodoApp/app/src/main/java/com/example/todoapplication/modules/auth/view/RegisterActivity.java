package com.example.todoapplication.modules.auth.view;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

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
import com.example.todoapplication.modules.auth.viewmodel.RegisterState;
import com.example.todoapplication.modules.auth.viewmodel.RegisterViewModel;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterActivity extends AppCompatActivity {

    private EditText etName;
    private EditText etEmail;
    private EditText etPassword;
    private MaterialButton btnRegister;
    private TextView tvLogin;

    private RegisterViewModel viewModel;
    private Snackbar loadingSnackbar;

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

        setupViews();
        setupViewModel();
        setupClickListeners();
        observeRegistrationState();
    }

    private void setupViews() {
        etName = findViewById(R.id.et_Name);
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        btnRegister = findViewById(R.id.btnRegister);
        tvLogin = findViewById(R.id.tvLogin);
    }

    private void setupViewModel() {
        AuthRepository repository = new AuthRepository();
        AuthViewModelFactory factory = new AuthViewModelFactory(repository);
        viewModel = new ViewModelProvider(this, factory).get(RegisterViewModel.class);
    }

    private void setupClickListeners() {
        tvLogin.setOnClickListener(v -> navigateToLogin());

        btnRegister.setOnClickListener(v -> {
            if (validateInputs()) {
                viewModel.registerUser(
                        etName.getText().toString().trim(),
                        etEmail.getText().toString().trim(),
                        etPassword.getText().toString().trim()
                );
            }
        });
    }

    private void observeRegistrationState() {
        viewModel.registrationState.observe(this, state -> {
            if (state instanceof RegisterState.Loading) {
                btnRegister.setEnabled(false);
                showLoadingSnackbar(((RegisterState.Loading) state).message);
            } else if (state instanceof RegisterState.Success) {
                btnRegister.setEnabled(true);
                dismissLoadingSnackbar();
                showSuccessDialog(((RegisterState.Success) state).message);
            } else if (state instanceof RegisterState.Error) {
                btnRegister.setEnabled(true);
                dismissLoadingSnackbar();
                showErrorSnackbar(((RegisterState.Error) state).message);
            }
        });
    }

    private boolean validateInputs() {
        String fullName = etName.getText().toString().trim();
        String email = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString().trim();

        boolean isValid = true;

        String nameError = Validation.validateName(fullName);
        etName.setError(nameError);
        if (nameError != null) isValid = false;

        String emailError = Validation.validateEmail(email);
        etEmail.setError(emailError);
        if (emailError != null) isValid = false;

        String passwordError = Validation.validatePassword(password);
        etPassword.setError(passwordError);
        if (passwordError != null) isValid = false;

        return isValid;
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
                .setPositiveButton(android.R.string.ok, (dialog, which) -> {
                    FirebaseAuth.getInstance().signOut();
                    navigateToLogin();
                })
                .show();
    }

    private void showErrorSnackbar(String message) {
        Snackbar.make(findViewById(android.R.id.content), message, Snackbar.LENGTH_LONG).show();
    }

    private void navigateToLogin() {
        Intent intent = new Intent(this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finishAffinity();
    }
}
package com.example.todoapplication.modules.auth.view;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;

import com.example.todoapplication.R;
import com.example.todoapplication.core.functions.HideStatusAndNavBar;
import com.example.todoapplication.core.functions.Toggle;
import com.example.todoapplication.core.functions.Validation;
import com.example.todoapplication.modules.auth.model.AuthRepository;
import com.example.todoapplication.modules.auth.viewmodel.AuthViewModelFactory;
import com.example.todoapplication.modules.auth.viewmodel.LoginState;
import com.example.todoapplication.modules.auth.viewmodel.LoginViewModel;
import com.example.todoapplication.modules.home.MainActivity;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    private EditText etEmail;
    private EditText etPassword;
    private ImageButton btnTogglePassword;
    private MaterialButton btnLogin;
    private TextView tvSignUp, tvForgetPassword;

    private LoginViewModel viewModel;
    private Snackbar loadingSnackbar;

    @Override
    protected void onStart() {
        super.onStart();
        checkUserState();
    }

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

        setupViews();
        setupViewModel();
        setupClickListeners();
        observeLoginState();
    }

    private void setupViews() {
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        btnTogglePassword = findViewById(R.id.btnTogglePassword);
        btnLogin = findViewById(R.id.btnLogin);
        tvSignUp = findViewById(R.id.tvSignUp);
        tvForgetPassword = findViewById(R.id.tv_forgetPassword);
    }

    private void setupViewModel() {
        AuthRepository repository = new AuthRepository();
        AuthViewModelFactory factory = new AuthViewModelFactory(repository);
        viewModel = new ViewModelProvider(this, factory).get(LoginViewModel.class);
    }

    private void setupClickListeners() {
        btnTogglePassword.setOnClickListener(v ->
                Toggle.togglePasswordVisibility(etPassword, btnTogglePassword));

        tvSignUp.setOnClickListener(v -> {
            startActivity(new Intent(this, RegisterActivity.class));
            finish();
        });

        tvForgetPassword.setOnClickListener(v -> {
            startActivity(new Intent(LoginActivity.this, ForgetPasswordActivity.class));
        });

        btnLogin.setOnClickListener(v -> {
            if (validateInputs()) {
                viewModel.loginWithEmail(
                        etEmail.getText().toString().trim(),
                        etPassword.getText().toString().trim()
                );
            }
        });
    }

    private boolean validateInputs() {
        String email = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString().trim();
        boolean isValid = true;

        String emailError = Validation.validateEmail(email);
        etEmail.setError(emailError);
        if (emailError != null) isValid = false;

        String passwordError = Validation.validatePassword(password);
        etPassword.setError(passwordError);
        if (passwordError != null) isValid = false;

        return isValid;
    }

    private void observeLoginState() {
        viewModel.loginState.observe(this, state -> {
            if (state instanceof LoginState.Loading) {
                btnLogin.setEnabled(false);
                showLoadingSnackbar(((LoginState.Loading) state).message);
            } else if (state instanceof LoginState.Success) {
                btnLogin.setEnabled(true);
                dismissLoadingSnackbar();
                navigateToHome();
            } else if (state instanceof LoginState.Error) {
                btnLogin.setEnabled(true);
                dismissLoadingSnackbar();
                showErrorSnackbar(((LoginState.Error) state).message);
            } else if (state instanceof LoginState.EmailNotVerified) {
                btnLogin.setEnabled(true);
                dismissLoadingSnackbar();
                handleEmailNotVerified();
            }
        });
    }

    private void handleEmailNotVerified() {
        new AlertDialog.Builder(this)
                .setMessage("Please verify your email first. Check your inbox.")
                .setPositiveButton("Resend Verification", (dialog, which) -> resendVerificationEmail())
                .setNegativeButton(android.R.string.ok, (dialog, which) -> FirebaseAuth.getInstance().signOut())
                .setCancelable(false)
                .show();
    }

    private void resendVerificationEmail() {
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null) {
            currentUser.sendEmailVerification()
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            Toast.makeText(this, "Verification email sent", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(this, "Failed to send verification", Toast.LENGTH_SHORT).show();
                        }
                        FirebaseAuth.getInstance().signOut();
                    });
        }
    }

    private void retryLogin() {
        String email = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString().trim();
        if (!email.isEmpty() && !password.isEmpty()) {
            viewModel.loginWithEmail(email, password);
        }
    }

    private void checkUserState() {
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null && currentUser.isEmailVerified()) {
            navigateToHome();
        }
    }

    private void navigateToHome() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
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

    private void showErrorSnackbar(String message) {
        Snackbar.make(findViewById(android.R.id.content), message, Snackbar.LENGTH_LONG)
                .setAction("Retry", v -> retryLogin())
                .show();
    }
}
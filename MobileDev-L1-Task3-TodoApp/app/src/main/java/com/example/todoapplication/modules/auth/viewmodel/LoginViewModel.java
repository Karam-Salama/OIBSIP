package com.example.todoapplication.modules.auth.viewmodel;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.todoapplication.modules.auth.model.AuthRepository;
import com.google.firebase.auth.FirebaseUser;

public class LoginViewModel extends ViewModel {

    private final AuthRepository authRepository;
    private final MutableLiveData<LoginState> _loginState = new MutableLiveData<>();
    public LiveData<LoginState> loginState = _loginState;

    public LoginViewModel(@NonNull AuthRepository authRepository) {
        this.authRepository = authRepository;
    }

    public void loginWithEmail(@NonNull String email, @NonNull String password) {
        _loginState.setValue(new LoginState.Loading("Signing in..."));

        authRepository.loginWithEmail(email, password, new AuthRepository.AuthCallback<FirebaseUser>() {
            @Override
            public void onSuccess(FirebaseUser user) {
                if (user != null && !user.isEmailVerified()) {
                    _loginState.postValue(LoginState.EmailNotVerified.INSTANCE);
                } else {
                    _loginState.postValue(new LoginState.Success("Login successful"));
                }
            }

            @Override
            public void onFailure(@NonNull Exception e) {
                String msg = e.getMessage() != null ? e.getMessage() : "Login failed";
                if ("Email not verified".equals(msg)) {
                    _loginState.postValue(LoginState.EmailNotVerified.INSTANCE);
                } else {
                    _loginState.postValue(new LoginState.Error(msg));
                }
            }
        });
    }

    public void setErrorState(@NonNull String message) {
        _loginState.postValue(new LoginState.Error(message));
    }
}
package com.example.todoapplication.modules.auth.viewmodel;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.todoapplication.modules.auth.model.AuthRepository;
import com.google.firebase.auth.FirebaseUser;

public class RegisterViewModel extends ViewModel {

    private final AuthRepository authRepository;
    private final MutableLiveData<RegisterState> _registrationState = new MutableLiveData<>();
    public LiveData<RegisterState> registrationState = _registrationState;

    public RegisterViewModel(@NonNull AuthRepository authRepository) {
        this.authRepository = authRepository;
    }

    public void registerUser(@NonNull String fullName,
                             @NonNull String email,
                             @NonNull String password) {
        _registrationState.setValue(new RegisterState.Loading("Registering your account..."));

        authRepository.registerWithEmail(
                fullName,
                null, // لا يوجد حقل هاتف في شاشة التسجيل الحالية
                email,
                password,
                new AuthRepository.AuthCallback<FirebaseUser>() {
                    @Override
                    public void onSuccess(FirebaseUser user) {
                        _registrationState.postValue(
                                new RegisterState.Success(
                                        user,
                                        "Please check your email to verify your account"
                                )
                        );
                    }

                    @Override
                    public void onFailure(@NonNull Exception e) {
                        String errorMsg = e.getMessage() != null ? e.getMessage() : "Registration failed";
                        _registrationState.postValue(new RegisterState.Error(errorMsg));
                    }
                }
        );
    }
}

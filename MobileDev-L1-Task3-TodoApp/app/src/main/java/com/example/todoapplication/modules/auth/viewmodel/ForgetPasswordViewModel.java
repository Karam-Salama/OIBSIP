package com.example.todoapplication.modules.auth.viewmodel;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.todoapplication.modules.auth.model.AuthRepository;

public class ForgetPasswordViewModel extends ViewModel {

    private final AuthRepository authRepository;
    private final MutableLiveData<ForgetPasswordState> _resetState = new MutableLiveData<>();
    public LiveData<ForgetPasswordState> resetState = _resetState;

    public ForgetPasswordViewModel(@NonNull AuthRepository authRepository) {
        this.authRepository = authRepository;
    }

    public void sendPasswordResetEmail(@NonNull String email) {
        _resetState.setValue(new ForgetPasswordState.Loading("Sending reset link..."));

        authRepository.sendPasswordResetEmail(email, new AuthRepository.AuthCallback<Void>() {
            @Override
            public void onSuccess(Void result) {
                _resetState.postValue(
                        new ForgetPasswordState.Success(
                                "Password reset link has been sent to your email. Please check your inbox or spam folder."
                        )
                );
            }

            @Override
            public void onFailure(@NonNull Exception e) {
                String msg = e.getMessage() != null ? e.getMessage() : "Failed to send reset email";
                _resetState.postValue(new ForgetPasswordState.Error(msg));
            }
        });
    }
}

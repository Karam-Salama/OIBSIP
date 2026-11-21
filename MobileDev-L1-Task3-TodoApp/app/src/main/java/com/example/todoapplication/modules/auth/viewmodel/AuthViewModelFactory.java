package com.example.todoapplication.modules.auth.viewmodel;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.todoapplication.modules.auth.model.AuthRepository;

public class AuthViewModelFactory implements ViewModelProvider.Factory {

    private final AuthRepository repository;

    public AuthViewModelFactory(@NonNull AuthRepository repository) {
        this.repository = repository;
    }

    @NonNull
    @Override
    @SuppressWarnings("unchecked")
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(RegisterViewModel.class)) {
            return (T) new RegisterViewModel(repository);
        } else if (modelClass.isAssignableFrom(LoginViewModel.class)) {
            return (T) new LoginViewModel(repository);
        } else if (modelClass.isAssignableFrom(ForgetPasswordViewModel.class)) {
            return (T) new ForgetPasswordViewModel(repository);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
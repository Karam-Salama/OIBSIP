package com.example.todoapplication.modules.onboarding.viewmodel;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.todoapplication.modules.onboarding.model.OnboardingRepository;

public class OnboardingViewModelFactory implements ViewModelProvider.Factory {

    private final OnboardingRepository repository;

    public OnboardingViewModelFactory(@NonNull OnboardingRepository repository) {
        this.repository = repository;
    }

    @NonNull
    @Override
    @SuppressWarnings("unchecked")
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(OnboardingViewModel.class)) {
            return (T) new OnboardingViewModel(repository);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}

package com.example.todoapplication.modules.onboarding.viewmodel;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.todoapplication.modules.onboarding.model.OnboardingRepository;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class OnboardingViewModel extends ViewModel {

    public enum NavigationDestination {
        REGISTER,
        LOGIN
    }

    private final OnboardingRepository repo;
    private final ExecutorService executor = Executors.newSingleThreadExecutor();

    private final MutableLiveData<NavigationDestination> _navigationEvent = new MutableLiveData<>();
    public LiveData<NavigationDestination> navigationEvent = _navigationEvent;

    public OnboardingViewModel(@NonNull OnboardingRepository repo) {
        this.repo = repo;
    }

    // عند الضغط على Get Started
    public void handleGetStartedClick() {
        executor.execute(() -> {
            repo.setOnboardingCompleted();
            _navigationEvent.postValue(NavigationDestination.REGISTER);
        });
    }

    // عند الضغط على Log in
    public void handleLoginClick() {
        executor.execute(() -> {
            repo.setOnboardingCompleted();
            _navigationEvent.postValue(NavigationDestination.LOGIN);
        });
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        executor.shutdown();
    }
}
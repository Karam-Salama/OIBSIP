package com.example.todoapplication.modules.onboarding.model;

import com.example.todoapplication.core.data.CacheHelper;
import com.example.todoapplication.core.helpers.Constants;

public class OnboardingRepository {

    public void setOnboardingCompleted() {
        CacheHelper.saveData(Constants.ONBOARDING_KEY, true);
    }
}
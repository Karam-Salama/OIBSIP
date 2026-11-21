package com.example.todoapplication.modules.auth.viewmodel;

import androidx.annotation.NonNull;

public abstract class ForgetPasswordState {

    private ForgetPasswordState() {}

    public static final class Loading extends ForgetPasswordState {
        @NonNull
        public final String message;

        public Loading(@NonNull String message) {
            this.message = message;
        }
    }

    public static final class Success extends ForgetPasswordState {
        @NonNull
        public final String message;

        public Success(@NonNull String message) {
            this.message = message;
        }
    }

    public static final class Error extends ForgetPasswordState {
        @NonNull
        public final String message;

        public Error(@NonNull String message) {
            this.message = message;
        }
    }
}
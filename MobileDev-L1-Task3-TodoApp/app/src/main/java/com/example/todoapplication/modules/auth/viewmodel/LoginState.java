package com.example.todoapplication.modules.auth.viewmodel;

import androidx.annotation.NonNull;

public abstract class LoginState {

    private LoginState() {}

    public static final class Loading extends LoginState {
        @NonNull
        public final String message;

        public Loading(@NonNull String message) {
            this.message = message;
        }
    }

    public static final class Success extends LoginState {
        @NonNull
        public final String message;

        public Success(@NonNull String message) {
            this.message = message;
        }
    }

    public static final class Error extends LoginState {
        @NonNull
        public final String message;

        public Error(@NonNull String message) {
            this.message = message;
        }
    }

    public static final class EmailNotVerified extends LoginState {
        public static final EmailNotVerified INSTANCE = new EmailNotVerified();

        private EmailNotVerified() {}
    }
}
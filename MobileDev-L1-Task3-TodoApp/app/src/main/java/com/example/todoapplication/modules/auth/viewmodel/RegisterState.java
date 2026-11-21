package com.example.todoapplication.modules.auth.viewmodel;

import androidx.annotation.NonNull;
import com.google.firebase.auth.FirebaseUser;

public abstract class RegisterState {

    private RegisterState() {}

    public static final class Loading extends RegisterState {
        @NonNull
        public final String message;

        public Loading(@NonNull String message) {
            this.message = message;
        }
    }

    public static final class Success extends RegisterState {
        @NonNull
        public final FirebaseUser user;
        @NonNull
        public final String message;

        public Success(@NonNull FirebaseUser user, @NonNull String message) {
            this.user = user;
            this.message = message;
        }
    }

    public static final class Error extends RegisterState {
        @NonNull
        public final String message;

        public Error(@NonNull String message) {
            this.message = message;
        }
    }
}

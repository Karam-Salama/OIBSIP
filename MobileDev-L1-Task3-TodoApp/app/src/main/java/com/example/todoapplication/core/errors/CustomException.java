package com.example.todoapplication.core.errors;
import androidx.annotation.NonNull;

public class CustomException extends Exception {

    @NonNull
    private final String message;

    public CustomException(@NonNull String message) {
        super(message);
        this.message = message;
    }

    @Override
    @NonNull
    public String getMessage() {
        return message;
    }
}
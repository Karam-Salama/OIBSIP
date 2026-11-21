package com.example.todoapplication.core.errors;
import androidx.annotation.Nullable;

public abstract class Failure extends Throwable {

    Failure(@Nullable String message) {
        super(message);
    }
}
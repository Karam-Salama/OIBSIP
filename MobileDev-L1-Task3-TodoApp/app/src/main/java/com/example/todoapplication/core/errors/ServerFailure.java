package com.example.todoapplication.core.errors;
import androidx.annotation.NonNull;

public final class ServerFailure extends Failure {

    public ServerFailure(@NonNull String message) {
        super(message);
    }
}
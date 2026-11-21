package com.example.todoapplication.core.functions;

import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.annotation.NonNull;

import com.example.todoapplication.R;

public final class Toggle {

    private Toggle() {
        // Prevent instantiation
    }

    public static void togglePasswordVisibility(@NonNull EditText editText,
                                                @NonNull ImageButton toggleButton) {
        boolean isCurrentlyHidden = editText.getTransformationMethod() instanceof PasswordTransformationMethod;

        if (isCurrentlyHidden) {
            // إظهار كلمة المرور
            editText.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            toggleButton.setImageResource(R.drawable.ic_visibility);
        } else {
            // إخفاء كلمة المرور
            editText.setTransformationMethod(PasswordTransformationMethod.getInstance());
            toggleButton.setImageResource(R.drawable.ic_visibility_off);
        }

        // إبقاء مؤشر الكتابة في نهاية النص
        editText.setSelection(editText.getText().length());
    }
}
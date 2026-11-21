package com.example.todoapplication.core.functions;
import android.util.Patterns;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import java.util.regex.Pattern;

public final class Validation {

    private static final Pattern NAME_PATTERN = Pattern.compile("^[a-zA-Z\\p{L}\\s]+$");
    private static final Pattern PHONE_PATTERN = Pattern.compile("^(010|011|012|015)\\d{8}$");
    private static final Pattern UPPERCASE_PATTERN = Pattern.compile("[A-Z]");
    private static final Pattern LOWERCASE_PATTERN = Pattern.compile("[a-z]");
    private static final Pattern DIGIT_PATTERN = Pattern.compile("\\d");
    private static final Pattern SPECIAL_CHAR_PATTERN = Pattern.compile("[!@#$%^&*()]");

    private Validation() {
        // Prevent instantiation to mirror Kotlin's `object`
    }

    @Nullable
    public static String validateName(@NonNull String name) {
        if (name.isEmpty()) {
            return "Full name is required.";
        } else if (name.length() < 3) {
            return "Full name must be at least 3 characters.";
        } else if (!NAME_PATTERN.matcher(name).matches()) {
            return "Full name can only contain letters and spaces.";
        }
        return null;
    }

    @Nullable
    public static String validatePhone(@NonNull String phone) {
        if (phone.isEmpty()) {
            return "Phone number is required.";
        } else if (!PHONE_PATTERN.matcher(phone).matches()) {
            return "Invalid phone number format.";
        }
        return null;
    }

    @Nullable
    public static String validateEmail(@NonNull String email) {
        if (email.isEmpty()) {
            return "Email is required.";
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            return "Invalid email format.";
        }
        return null;
    }

    @Nullable
    public static String validatePassword(@NonNull String password) {
        if (password.isEmpty()) {
            return "Password is required.";
        } else if (password.length() < 8) {
            return "Password must be at least 8 characters long.";
        } else if (!UPPERCASE_PATTERN.matcher(password).find()) {
            return "Password must contain at least one uppercase letter.";
        } else if (!LOWERCASE_PATTERN.matcher(password).find()) {
            return "Password must contain at least one lowercase letter.";
        } else if (!DIGIT_PATTERN.matcher(password).find()) {
            return "Password must contain at least one digit.";
        } else if (!SPECIAL_CHAR_PATTERN.matcher(password).find()) {
            return "Password must contain at least one special character (!@#$%^&*()).";
        }
        return null;
    }

    @Nullable
    public static String validateConfirmPassword(@Nullable String confirmPassword, @Nullable String password) {
        if (confirmPassword == null || confirmPassword.isEmpty()) {
            return "This Field is required.";
        } else if (password == null || password.isEmpty()) {
            return "Password Field is required.";
        } else if (!confirmPassword.equals(password)) {
            return "Passwords don't match.";
        }
        return null;
    }
}
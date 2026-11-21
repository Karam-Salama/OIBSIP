package com.example.todoapplication.core.functions;
import androidx.annotation.NonNull;
import java.util.regex.Pattern;

public final class AppRegex {

    private static final Pattern EMAIL_PATTERN =
            Pattern.compile("^.+@[a-zA-Z]+\\.[a-zA-Z]+(\\.{0,1}[a-zA-Z]+)$");
    private static final Pattern PASSWORD_PATTERN =
            Pattern.compile("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$");
    private static final Pattern PHONE_NUMBER_PATTERN =
            Pattern.compile("^(010|011|012|015)[0-9]{8}$");
    private static final Pattern LOWER_CASE_PATTERN =
            Pattern.compile("^(?=.*[a-z])");
    private static final Pattern UPPER_CASE_PATTERN =
            Pattern.compile("^(?=.*[A-Z])");
    private static final Pattern NUMBER_PATTERN =
            Pattern.compile("^(?=.*?[0-9])");
    private static final Pattern SPECIAL_CHARACTER_PATTERN =
            Pattern.compile("^(?=.*?[#?!@$%^&*-])");

    private AppRegex() {
        // Prevent instantiation to mirror Kotlin's `object`
    }

    public static boolean isEmailValid(@NonNull String email) {
        return EMAIL_PATTERN.matcher(email).matches();
    }

    public static boolean isPasswordValid(@NonNull String password) {
        return PASSWORD_PATTERN.matcher(password).matches();
    }

    public static boolean isPhoneNumberValid(@NonNull String phoneNumber) {
        return PHONE_NUMBER_PATTERN.matcher(phoneNumber).matches();
    }

    public static boolean hasLowerCase(@NonNull String password) {
        return LOWER_CASE_PATTERN.matcher(password).find();
    }

    public static boolean hasUpperCase(@NonNull String password) {
        return UPPER_CASE_PATTERN.matcher(password).find();
    }

    public static boolean hasNumber(@NonNull String password) {
        return NUMBER_PATTERN.matcher(password).find();
    }

    public static boolean hasSpecialCharacter(@NonNull String password) {
        return SPECIAL_CHARACTER_PATTERN.matcher(password).find();
    }

    public static boolean passHasMinLength(@NonNull String password) {
        return password.length() >= 8;
    }

    public static boolean nameHasMinLength(@NonNull String name) {
        return name.length() >= 3;
    }
}
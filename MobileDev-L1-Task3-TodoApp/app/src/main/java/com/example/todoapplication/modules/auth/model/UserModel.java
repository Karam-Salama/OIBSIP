package com.example.todoapplication.modules.auth.model;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.HashMap;
import java.util.Map;

public class UserModel {

    @NonNull
    private String uid;
    @NonNull
    private String name;
    @NonNull
    private String email;
    @Nullable
    private String phone;

    // Default constructor مطلوب لـ Firestore deserialization
    public UserModel() {
        this("", "", "", null);
    }

    public UserModel(@NonNull String uid,
                     @NonNull String name,
                     @NonNull String email,
                     @Nullable String phone) {
        this.uid = uid;
        this.name = name;
        this.email = email;
        this.phone = phone;
    }

    @NonNull
    public String getUid() {
        return uid;
    }

    @NonNull
    public String getName() {
        return name;
    }

    @NonNull
    public String getEmail() {
        return email;
    }

    @Nullable
    public String getPhone() {
        return phone;
    }

    @NonNull
    public Map<String, Object> toMap() {
        Map<String, Object> map = new HashMap<>();
        map.put("uid", uid);
        map.put("name", name);
        map.put("email", email);
        map.put("phone", phone);
        return map;
    }

    @NonNull
    public static UserModel fromMap(@NonNull Map<String, Object> map) {
        String uid = map.get("uid") instanceof String ? (String) map.get("uid") : "";
        String name = map.get("name") instanceof String ? (String) map.get("name") : "";
        String email = map.get("email") instanceof String ? (String) map.get("email") : "";
        String phone = map.get("phone") instanceof String ? (String) map.get("phone") : null;

        return new UserModel(uid, name, email, phone);
    }
}
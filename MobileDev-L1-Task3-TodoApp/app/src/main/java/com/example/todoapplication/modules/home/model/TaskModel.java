package com.example.todoapplication.modules.home.model;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class TaskModel {

    private long id;
    @NonNull
    private String userId;
    @NonNull
    private String title;
    @Nullable
    private String note;
    @NonNull
    private String priority; // "Low", "Medium", "High"
    @Nullable
    private String dueDate;  // "yyyy-MM-dd"
    private boolean isCompleted;
    private long createdAt;

    public TaskModel(long id,
                     @NonNull String userId,
                     @NonNull String title,
                     @Nullable String note,
                     @NonNull String priority,
                     @Nullable String dueDate,
                     boolean isCompleted,
                     long createdAt) {
        this.id = id;
        this.userId = userId;
        this.title = title;
        this.note = note;
        this.priority = priority;
        this.dueDate = dueDate;
        this.isCompleted = isCompleted;
        this.createdAt = createdAt;
    }

    public long getId() { return id; }
    @NonNull public String getUserId() { return userId; }
    @NonNull public String getTitle() { return title; }
    @Nullable public String getNote() { return note; }
    @NonNull public String getPriority() { return priority; }
    @Nullable public String getDueDate() { return dueDate; }
    public boolean isCompleted() { return isCompleted; }
    public long getCreatedAt() { return createdAt; }
}

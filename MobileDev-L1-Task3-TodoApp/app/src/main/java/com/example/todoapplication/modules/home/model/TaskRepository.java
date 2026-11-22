package com.example.todoapplication.modules.home.model;


import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.todoapplication.modules.home.data.TaskDbHelper;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Collections;
import java.util.List;

public class TaskRepository {

    private final TaskDbHelper dbHelper;
    private final FirebaseAuth firebaseAuth;

    public TaskRepository(@NonNull Context context) {
        this.dbHelper = TaskDbHelper.getInstance(context);
        this.firebaseAuth = FirebaseAuth.getInstance();
    }

    @Nullable
    public String getCurrentUserId() {
        FirebaseUser user = firebaseAuth.getCurrentUser();
        return user != null ? user.getUid() : null;
    }

    public long addTask(@NonNull String title,
                        @Nullable String note,
                        @NonNull String priority,
                        @Nullable String dueDate) throws IllegalStateException {
        String userId = getCurrentUserId();
        if (userId == null) {
            throw new IllegalStateException("No logged-in user found.");
        }
        return dbHelper.addTask(userId, title, note, priority, dueDate);
    }

    @NonNull
    public List<TaskModel> getUserTasks() {
        String userId = getCurrentUserId();
        if (userId == null) {
            return Collections.emptyList();
        }
        return dbHelper.getTasksForUser(userId);
    }

    public void toggleTaskCompletion(long taskId, boolean isCompleted) {
        String userId = getCurrentUserId();
        if (userId != null) {
            dbHelper.updateTaskCompletion(taskId, userId, isCompleted);
        }
    }

    public void deleteTask(long taskId) {
        String userId = getCurrentUserId();
        if (userId != null) {
            dbHelper.deleteTask(taskId, userId);
        }
    }
}

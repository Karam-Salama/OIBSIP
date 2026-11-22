package com.example.todoapplication.modules.home.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.todoapplication.modules.home.model.TaskRepository;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AddTaskViewModel extends AndroidViewModel {

    public static class SaveResult {
        public final boolean success;
        public final String message;

        public SaveResult(boolean success, String message) {
            this.success = success;
            this.message = message;
        }
    }

    private final TaskRepository repository;
    private final ExecutorService executor = Executors.newSingleThreadExecutor();

    private final MutableLiveData<SaveResult> _saveResult = new MutableLiveData<>();
    public LiveData<SaveResult> saveResult = _saveResult;

    public AddTaskViewModel(@NonNull Application application) {
        super(application);
        this.repository = new TaskRepository(application);
    }

    public void saveTask(@NonNull String title,
                         @Nullable String note,
                         @NonNull String priority,
                         @Nullable String dueDate) {
        executor.execute(() -> {
            try {
                long rowId = repository.addTask(title, note, priority, dueDate);
                if (rowId != -1) {
                    _saveResult.postValue(new SaveResult(true, "Task added"));
                } else {
                    _saveResult.postValue(new SaveResult(false, "Failed to save task"));
                }
            } catch (Exception e) {
                _saveResult.postValue(new SaveResult(false, e.getMessage() != null ? e.getMessage() : "Error saving task"));
            }
        });
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        executor.shutdown();
    }
}
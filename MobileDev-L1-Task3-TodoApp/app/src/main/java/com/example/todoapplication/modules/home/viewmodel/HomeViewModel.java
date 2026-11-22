package com.example.todoapplication.modules.home.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.todoapplication.modules.home.model.TaskModel;
import com.example.todoapplication.modules.home.model.TaskRepository;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class HomeViewModel extends AndroidViewModel {

    private final TaskRepository repository;
    private final ExecutorService executor = Executors.newSingleThreadExecutor();

    private final MutableLiveData<List<TaskModel>> _tasks = new MutableLiveData<>();
    public LiveData<List<TaskModel>> tasks = _tasks;

    public HomeViewModel(@NonNull Application application) {
        super(application);
        this.repository = new TaskRepository(application);
    }

    public void loadTasks() {
        executor.execute(() -> {
            List<TaskModel> userTasks = repository.getUserTasks();
            _tasks.postValue(userTasks);
        });
    }

    public void toggleTaskCompletion(long taskId, boolean isCompleted) {
        executor.execute(() -> {
            repository.toggleTaskCompletion(taskId, isCompleted);
            List<TaskModel> updated = repository.getUserTasks();
            _tasks.postValue(updated);
        });
    }

    public void deleteTask(long taskId) {
        executor.execute(() -> {
            repository.deleteTask(taskId);
            List<TaskModel> updated = repository.getUserTasks();
            _tasks.postValue(updated);
        });
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        executor.shutdown();
    }
}
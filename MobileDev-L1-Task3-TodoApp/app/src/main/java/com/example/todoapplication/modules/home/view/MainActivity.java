package com.example.todoapplication.modules.home.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todoapplication.R;
import com.example.todoapplication.core.functions.HideStatusAndNavBar;
import com.example.todoapplication.modules.auth.view.LoginActivity;
import com.example.todoapplication.modules.home.view.AddTaskActivity;
import com.example.todoapplication.modules.home.model.TaskModel;
import com.example.todoapplication.modules.home.viewmodel.HomeViewModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity implements TaskAdapter.OnTaskActionListener {

    private TextView tvGreeting;
    private TextView tvTodoLabel;
    private RecyclerView rvTasks;
    private LinearLayout layoutEmptyState;

    private HomeViewModel viewModel;
    private TaskAdapter taskAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        HideStatusAndNavBar.hide(this);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        setupViews();
        setupUserGreeting();
        setupRecyclerView();
        setupViewModel();
        setupClickListeners();
    }

    @Override
    protected void onResume() {
        super.onResume();
        // تحديث القائمة عند العودة من AddTaskActivity
        viewModel.loadTasks();
    }

    private void setupViews() {
        tvGreeting = findViewById(R.id.tvGreeting);
        tvTodoLabel = findViewById(R.id.tvTodoLabel);
        rvTasks = findViewById(R.id.rvTasks);
        layoutEmptyState = findViewById(R.id.layoutEmptyState);
    }

    private void setupUserGreeting() {
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser == null) {
            navigateToLogin();
            return;
        }
        String displayName = currentUser.getDisplayName();
        if (displayName != null && !displayName.trim().isEmpty()) {
            tvGreeting.setText("Hi, " + displayName);
        } else if (currentUser.getEmail() != null) {
            tvGreeting.setText("Hi, " + currentUser.getEmail());
        }
    }

    private void setupRecyclerView() {
        taskAdapter = new TaskAdapter(this);
        rvTasks.setLayoutManager(new LinearLayoutManager(this));
        rvTasks.setAdapter(taskAdapter);
    }

    private void setupViewModel() {
        viewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        viewModel.tasks.observe(this, tasks -> {
            if (tasks == null || tasks.isEmpty()) {
                rvTasks.setVisibility(View.GONE);
                tvTodoLabel.setVisibility(View.GONE);
                layoutEmptyState.setVisibility(View.VISIBLE);
            } else {
                layoutEmptyState.setVisibility(View.GONE);
                tvTodoLabel.setVisibility(View.VISIBLE);
                rvTasks.setVisibility(View.VISIBLE);
                taskAdapter.submitList(tasks);
            }
        });
    }

    private void setupClickListeners() {
        findViewById(R.id.btnAddTask).setOnClickListener(v ->
                startActivity(new Intent(this, AddTaskActivity.class)));

        findViewById(R.id.btnLogout).setOnClickListener(v -> confirmLogout());
    }

    @Override
    public void onTaskCheckedChanged(@NonNull TaskModel task, boolean isCompleted) {
        viewModel.toggleTaskCompletion(task.getId(), isCompleted);
    }

    @Override
    public void onTaskDeleteClicked(@NonNull TaskModel task) {
        new AlertDialog.Builder(this)
                .setTitle("Delete Task")
                .setMessage("Are you sure you want to permanently delete \"" + task.getTitle() + "\"?")
                .setPositiveButton("Delete", (dialog, which) -> {
                    viewModel.deleteTask(task.getId());
                    Toast.makeText(this, "Task deleted", Toast.LENGTH_SHORT).show();
                })
                .setNegativeButton("Cancel", null)
                .show();
    }

    private void confirmLogout() {
        new AlertDialog.Builder(this)
                .setTitle("Logout")
                .setMessage("Are you sure you want to log out?")
                .setPositiveButton("Logout", (dialog, which) -> {
                    FirebaseAuth.getInstance().signOut();
                    navigateToLogin();
                })
                .setNegativeButton("Cancel", null)
                .show();
    }

    private void navigateToLogin() {
        Intent intent = new Intent(this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }
}
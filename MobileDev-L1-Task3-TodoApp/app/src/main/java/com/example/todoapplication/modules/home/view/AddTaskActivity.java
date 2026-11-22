package com.example.todoapplication.modules.home.view;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;

import com.example.todoapplication.R;
import com.example.todoapplication.modules.home.viewmodel.AddTaskViewModel;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.chip.Chip;

import java.util.Calendar;
import java.util.Locale;

public class AddTaskActivity extends AppCompatActivity {

    private EditText etTitle, etNote;
    private TextView tvDueDate;
    private Chip chipLow, chipMedium, chipHigh;
    private MaterialButton btnSaveTask;
    private String dueDate = null; // yyyy-MM-dd

    private AddTaskViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_task);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.layoutAddTask), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        etTitle = findViewById(R.id.etTitle);
        etNote = findViewById(R.id.etNote);
        tvDueDate = findViewById(R.id.tvDueDate);
        btnSaveTask = findViewById(R.id.btnSaveTask);

        setupPriorityChips();

        viewModel = new ViewModelProvider(this).get(AddTaskViewModel.class);
        observeSaveResult();

        findViewById(R.id.btnBack).setOnClickListener(v -> finish());
        findViewById(R.id.btnCancel).setOnClickListener(v -> finish());
        findViewById(R.id.llDueDate).setOnClickListener(v -> showDatePicker());
        btnSaveTask.setOnClickListener(v -> saveTask());
    }

    private void setupPriorityChips() {
        chipLow = findViewById(R.id.chipLow);
        chipMedium = findViewById(R.id.chipMedium);
        chipHigh = findViewById(R.id.chipHigh);

        chipLow.setOnClickListener(v -> selectChip(chipLow));
        chipMedium.setOnClickListener(v -> selectChip(chipMedium));
        chipHigh.setOnClickListener(v -> selectChip(chipHigh));
    }

    private void selectChip(Chip selected) {
        chipLow.setChecked(selected == chipLow);
        chipMedium.setChecked(selected == chipMedium);
        chipHigh.setChecked(selected == chipHigh);
    }

    private void observeSaveResult() {
        viewModel.saveResult.observe(this, result -> {
            if (result == null) return;
            btnSaveTask.setEnabled(true);
            Toast.makeText(this, result.message, Toast.LENGTH_SHORT).show();
            if (result.success) {
                finish();
            }
        });
    }

    private void showDatePicker() {
        Calendar c = Calendar.getInstance();
        new DatePickerDialog(this, (view, year, month, day) -> {
            dueDate = String.format(Locale.US, "%04d-%02d-%02d", year, month + 1, day);
            tvDueDate.setText(dueDate);
            tvDueDate.setTextColor(ContextCompat.getColor(this, R.color.tf_text_primary));
        }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH)).show();
    }

    private String selectedPriority() {
        if (chipLow != null && chipLow.isChecked()) return "Low";
        if (chipHigh != null && chipHigh.isChecked()) return "High";
        return "Medium";
    }

    private void saveTask() {
        String title = etTitle.getText().toString().trim();
        String note = etNote.getText().toString().trim();

        if (title.isEmpty()) {
            etTitle.setError("Please enter a title");
            etTitle.requestFocus();
            return;
        }

        btnSaveTask.setEnabled(false);
        String priority = selectedPriority();
        viewModel.saveTask(title, note, priority, dueDate);
    }
}
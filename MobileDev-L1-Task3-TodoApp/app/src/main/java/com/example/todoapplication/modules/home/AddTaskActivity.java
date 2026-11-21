package com.example.todoapplication.modules.home;

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

import com.example.todoapplication.R;

import java.util.Calendar;
import java.util.Locale;

public class AddTaskActivity extends AppCompatActivity {

    private EditText etTitle, etNote;
    private TextView tvDueDate;
    private String dueDate = null; // yyyy-MM-dd

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_task);

        // FIX: the root id in activity_add_task.xml is layoutAddTask, not main
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.layoutAddTask), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        etTitle = findViewById(R.id.etTitle);
        etNote = findViewById(R.id.etNote);
        tvDueDate = findViewById(R.id.tvDueDate);

        findViewById(R.id.btnBack).setOnClickListener(v -> finish());
        findViewById(R.id.btnCancel).setOnClickListener(v -> finish());
        findViewById(R.id.llDueDate).setOnClickListener(v -> showDatePicker());
        findViewById(R.id.btnSaveTask).setOnClickListener(v -> saveTask());
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
        int id = ((com.google.android.material.chip.ChipGroup)
                findViewById(R.id.chipGroupPriority)).getCheckedChipId();
        if (id == R.id.chipLow) return "Low";
        if (id == R.id.chipHigh) return "High";
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

        String priority = selectedPriority();

        // TODO: insert into SQLite for the logged-in user, e.g.
        // dbHelper.addTask(userId, title, note, priority, dueDate);

        Toast.makeText(this, "Task added", Toast.LENGTH_SHORT).show();
        finish();
    }
}
package com.example.todoapplication.modules.home.view;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todoapplication.R;
import com.example.todoapplication.modules.home.model.TaskModel;

import java.util.ArrayList;
import java.util.List;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder> {

    public interface OnTaskActionListener {
        void onTaskCheckedChanged(@NonNull TaskModel task, boolean isCompleted);
        void onTaskDeleteClicked(@NonNull TaskModel task);
    }

    private final List<TaskModel> taskList = new ArrayList<>();
    private final OnTaskActionListener listener;

    public TaskAdapter(@NonNull OnTaskActionListener listener) {
        this.listener = listener;
    }

    public void submitList(@NonNull List<TaskModel> tasks) {
        taskList.clear();
        taskList.addAll(tasks);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_task, parent, false);
        return new TaskViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {
        holder.bind(taskList.get(position));
    }

    @Override
    public int getItemCount() {
        return taskList.size();
    }

    class TaskViewHolder extends RecyclerView.ViewHolder {

        private final CheckBox cbCompleted;
        private final TextView tvTaskTitle;
        private final TextView tvTaskNote;
        private final TextView tvPriorityBadge;
        private final TextView tvTaskDueDate;
        private final ImageButton btnDeleteTask;

        TaskViewHolder(@NonNull View itemView) {
            super(itemView);
            cbCompleted = itemView.findViewById(R.id.cbCompleted);
            tvTaskTitle = itemView.findViewById(R.id.tvTaskTitle);
            tvTaskNote = itemView.findViewById(R.id.tvTaskNote);
            tvPriorityBadge = itemView.findViewById(R.id.tvPriorityBadge);
            tvTaskDueDate = itemView.findViewById(R.id.tvTaskDueDate);
            btnDeleteTask = itemView.findViewById(R.id.btnDeleteTask);
        }

        void bind(@NonNull TaskModel task) {
            tvTaskTitle.setText(task.getTitle());

            // Note visibility
            if (task.getNote() != null && !task.getNote().isEmpty()) {
                tvTaskNote.setVisibility(View.VISIBLE);
                tvTaskNote.setText(task.getNote());
            } else {
                tvTaskNote.setVisibility(View.GONE);
            }

            // Due Date visibility
            if (task.getDueDate() != null && !task.getDueDate().isEmpty()) {
                tvTaskDueDate.setVisibility(View.VISIBLE);
                tvTaskDueDate.setText(task.getDueDate());
            } else {
                tvTaskDueDate.setVisibility(View.GONE);
            }

            // Priority badge styling
            tvPriorityBadge.setText(task.getPriority());
            stylePriorityBadge(task.getPriority());

            // Strikethrough & dim when completed
            cbCompleted.setOnCheckedChangeListener(null);
            cbCompleted.setChecked(task.isCompleted());
            applyCompletedStyle(task.isCompleted());

            cbCompleted.setOnCheckedChangeListener((buttonView, isChecked) ->
                    listener.onTaskCheckedChanged(task, isChecked));

            btnDeleteTask.setOnClickListener(v ->
                    listener.onTaskDeleteClicked(task));
        }

        private void applyCompletedStyle(boolean isCompleted) {
            if (isCompleted) {
                tvTaskTitle.setPaintFlags(tvTaskTitle.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                tvTaskTitle.setTextColor(ContextCompat.getColor(itemView.getContext(), R.color.subtitle_text));
                itemView.setAlpha(0.65f);
            } else {
                tvTaskTitle.setPaintFlags(tvTaskTitle.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
                tvTaskTitle.setTextColor(ContextCompat.getColor(itemView.getContext(), R.color.title_text));
                itemView.setAlpha(1.0f);
            }
        }

        private void stylePriorityBadge(@NonNull String priority) {
            switch (priority) {
                case "High":
                    tvPriorityBadge.setTextColor(Color.parseColor("#C62828"));
                    tvPriorityBadge.getBackground().setTint(Color.parseColor("#FFCDD2"));
                    break;
                case "Low":
                    tvPriorityBadge.setTextColor(Color.parseColor("#2E7D32"));
                    tvPriorityBadge.getBackground().setTint(Color.parseColor("#C8E6C9"));
                    break;
                default: // Medium
                    tvPriorityBadge.setTextColor(ContextCompat.getColor(itemView.getContext(), R.color.tf_primary));
                    tvPriorityBadge.getBackground().setTint(Color.parseColor("#FFDFD0"));
                    break;
            }
        }
    }
}
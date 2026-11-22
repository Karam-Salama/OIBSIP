package com.example.todoapplication.modules.home.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.todoapplication.modules.home.model.TaskModel;

import java.util.ArrayList;
import java.util.List;

public class TaskDbHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "todo_tasks.db";
    private static final int DATABASE_VERSION = 1;

    public static final String TABLE_TASKS = "tasks";
    public static final String COL_ID = "id";
    public static final String COL_USER_ID = "user_id";
    public static final String COL_TITLE = "title";
    public static final String COL_NOTE = "note";
    public static final String COL_PRIORITY = "priority";
    public static final String COL_DUE_DATE = "due_date";
    public static final String COL_IS_COMPLETED = "is_completed";
    public static final String COL_CREATED_AT = "created_at";

    private static volatile TaskDbHelper instance;

    private static final String CREATE_TABLE_SQL =
            "CREATE TABLE " + TABLE_TASKS + " (" +
                    COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COL_USER_ID + " TEXT NOT NULL, " +
                    COL_TITLE + " TEXT NOT NULL, " +
                    COL_NOTE + " TEXT, " +
                    COL_PRIORITY + " TEXT NOT NULL DEFAULT 'Medium', " +
                    COL_DUE_DATE + " TEXT, " +
                    COL_IS_COMPLETED + " INTEGER NOT NULL DEFAULT 0, " +
                    COL_CREATED_AT + " INTEGER NOT NULL" +
                    ")";

    private static final String CREATE_USER_INDEX_SQL =
            "CREATE INDEX idx_tasks_user_id ON " + TABLE_TASKS + " (" + COL_USER_ID + ")";

    private TaskDbHelper(@NonNull Context context) {
        super(context.getApplicationContext(), DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static TaskDbHelper getInstance(@NonNull Context context) {
        if (instance == null) {
            synchronized (TaskDbHelper.class) {
                if (instance == null) {
                    instance = new TaskDbHelper(context);
                }
            }
        }
        return instance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_SQL);
        db.execSQL(CREATE_USER_INDEX_SQL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TASKS);
        onCreate(db);
    }

    // إضافة مهمة جديدة مرتبطة بالمستخدم
    public long addTask(@NonNull String userId,
                        @NonNull String title,
                        @Nullable String note,
                        @NonNull String priority,
                        @Nullable String dueDate) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_USER_ID, userId);
        values.put(COL_TITLE, title);
        values.put(COL_NOTE, (note != null && !note.isEmpty()) ? note : null);
        values.put(COL_PRIORITY, priority);
        values.put(COL_DUE_DATE, dueDate);
        values.put(COL_IS_COMPLETED, 0);
        values.put(COL_CREATED_AT, System.currentTimeMillis());

        return db.insert(TABLE_TASKS, null, values);
    }

    // جلب مهام المستخدم الحالي فقط
    @NonNull
    public List<TaskModel> getTasksForUser(@NonNull String userId) {
        List<TaskModel> tasks = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();

        try (Cursor cursor = db.query(
                TABLE_TASKS,
                null,
                COL_USER_ID + " = ?",
                new String[]{userId},
                null,
                null,
                COL_IS_COMPLETED + " ASC, " + COL_CREATED_AT + " DESC"
        )) {
            while (cursor.moveToNext()) {
                long id = cursor.getLong(cursor.getColumnIndexOrThrow(COL_ID));
                String uid = cursor.getString(cursor.getColumnIndexOrThrow(COL_USER_ID));
                String title = cursor.getString(cursor.getColumnIndexOrThrow(COL_TITLE));
                String note = cursor.getString(cursor.getColumnIndexOrThrow(COL_NOTE));
                String priority = cursor.getString(cursor.getColumnIndexOrThrow(COL_PRIORITY));
                String dueDate = cursor.getString(cursor.getColumnIndexOrThrow(COL_DUE_DATE));
                boolean isCompleted = cursor.getInt(cursor.getColumnIndexOrThrow(COL_IS_COMPLETED)) == 1;
                long createdAt = cursor.getLong(cursor.getColumnIndexOrThrow(COL_CREATED_AT));

                tasks.add(new TaskModel(id, uid, title, note, priority, dueDate, isCompleted, createdAt));
            }
        }
        return tasks;
    }

    // تحديث حالة إنجاز المهمة (خاصة بالمستخدم نفسه)
    public int updateTaskCompletion(long taskId, @NonNull String userId, boolean isCompleted) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_IS_COMPLETED, isCompleted ? 1 : 0);

        return db.update(
                TABLE_TASKS,
                values,
                COL_ID + " = ? AND " + COL_USER_ID + " = ?",
                new String[]{String.valueOf(taskId), userId}
        );
    }

    // حذف مهمة (خاصة بالمستخدم نفسه)
    public int deleteTask(long taskId, @NonNull String userId) {
        SQLiteDatabase db = getWritableDatabase();
        return db.delete(
                TABLE_TASKS,
                COL_ID + " = ? AND " + COL_USER_ID + " = ?",
                new String[]{String.valueOf(taskId), userId}
        );
    }
}

package com.example.todoapplication.core.data;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import androidx.annotation.NonNull;

public final class CacheHelper {

    private static CacheDbHelper dbHelper;

    private CacheHelper() {
        // Prevent instantiation
    }

    // تهيئة قاعدة البيانات SQLite
    public static void init(@NonNull Context context) {
        if (dbHelper == null) {
            dbHelper = new CacheDbHelper(context.getApplicationContext());
        }
    }

    // حفظ بيانات في SQLite
    public static void saveData(@NonNull String key, @NonNull Object value) {
        SQLiteDatabase db = getDatabase();
        ContentValues values = new ContentValues();
        values.put(CacheDbHelper.COLUMN_KEY, key);

        if (value instanceof String) {
            values.put(CacheDbHelper.COLUMN_VALUE, (String) value);
            values.put(CacheDbHelper.COLUMN_TYPE, "STRING");
        } else if (value instanceof Boolean) {
            values.put(CacheDbHelper.COLUMN_VALUE, String.valueOf(value));
            values.put(CacheDbHelper.COLUMN_TYPE, "BOOLEAN");
        } else if (value instanceof Integer) {
            values.put(CacheDbHelper.COLUMN_VALUE, String.valueOf(value));
            values.put(CacheDbHelper.COLUMN_TYPE, "INT");
        } else if (value instanceof Float) {
            values.put(CacheDbHelper.COLUMN_VALUE, String.valueOf(value));
            values.put(CacheDbHelper.COLUMN_TYPE, "FLOAT");
        } else if (value instanceof Long) {
            values.put(CacheDbHelper.COLUMN_VALUE, String.valueOf(value));
            values.put(CacheDbHelper.COLUMN_TYPE, "LONG");
        } else {
            return;
        }

        db.insertWithOnConflict(
                CacheDbHelper.TABLE_NAME,
                null,
                values,
                SQLiteDatabase.CONFLICT_REPLACE
        );
    }

    // جلب بيانات نصية من SQLite
    @NonNull
    public static String getString(@NonNull String key) {
        return getString(key, "");
    }

    @NonNull
    public static String getString(@NonNull String key, @NonNull String defaultValue) {
        String rawValue = queryValue(key);
        return rawValue != null ? rawValue : defaultValue;
    }

    // جلب بيانات منطقية من SQLite
    public static boolean getBoolean(@NonNull String key) {
        return getBoolean(key, false);
    }

    public static boolean getBoolean(@NonNull String key, boolean defaultValue) {
        String rawValue = queryValue(key);
        return rawValue != null ? Boolean.parseBoolean(rawValue) : defaultValue;
    }

    // جلب بيانات رقمية من SQLite
    public static int getInt(@NonNull String key) {
        return getInt(key, 0);
    }

    public static int getInt(@NonNull String key, int defaultValue) {
        String rawValue = queryValue(key);
        if (rawValue == null) {
            return defaultValue;
        }
        try {
            return Integer.parseInt(rawValue);
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }

    // إزالة بيانات باستخدام مفتاح محدد
    public static void removeData(@NonNull String key) {
        SQLiteDatabase db = getDatabase();
        db.delete(
                CacheDbHelper.TABLE_NAME,
                CacheDbHelper.COLUMN_KEY + " = ?",
                new String[]{key}
        );
    }

    // حذف جميع البيانات
    public static void clearData() {
        SQLiteDatabase db = getDatabase();
        db.delete(CacheDbHelper.TABLE_NAME, null, null);
    }

    private static String queryValue(@NonNull String key) {
        SQLiteDatabase db = getDatabase();
        String result = null;
        try (Cursor cursor = db.query(
                CacheDbHelper.TABLE_NAME,
                new String[]{CacheDbHelper.COLUMN_VALUE},
                CacheDbHelper.COLUMN_KEY + " = ?",
                new String[]{key},
                null,
                null,
                null
        )) {
            if (cursor.moveToFirst()) {
                result = cursor.getString(0);
            }
        }
        return result;
    }

    private static SQLiteDatabase getDatabase() {
        if (dbHelper == null) {
            throw new IllegalStateException("CacheHelper.init(context) must be called before accessing data.");
        }
        return dbHelper.getWritableDatabase();
    }

    private static final class CacheDbHelper extends SQLiteOpenHelper {

        private static final String DATABASE_NAME = "app_cache.db";
        private static final int DATABASE_VERSION = 1;
        private static final String TABLE_NAME = "key_value_cache";
        private static final String COLUMN_KEY = "cache_key";
        private static final String COLUMN_VALUE = "cache_value";
        private static final String COLUMN_TYPE = "value_type";

        private static final String CREATE_TABLE_SQL =
                "CREATE TABLE " + TABLE_NAME + " (" +
                        COLUMN_KEY + " TEXT PRIMARY KEY, " +
                        COLUMN_VALUE + " TEXT, " +
                        COLUMN_TYPE + " TEXT" +
                        ")";

        CacheDbHelper(@NonNull Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(CREATE_TABLE_SQL);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
            onCreate(db);
        }
    }
}
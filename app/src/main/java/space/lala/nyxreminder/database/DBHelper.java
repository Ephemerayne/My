package space.lala.nyxreminder.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "reminderlist.db";
    public static final int DATABASE_VERSION = 1;

    public DBHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String SQL_CREATE_REMINDERLIST_TABLE = "CREATE TABLE " +
                ReminderContract.ReminderEntry.TABLE_NAME + " (" +
                ReminderContract.ReminderEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                ReminderContract.ReminderEntry.COLUMN_TITLE + " TEXT NOT NULL, " +
                ReminderContract.ReminderEntry.COLUMN_DESCRIPTION + " TEXT NOT NULL, " +
                ReminderContract.ReminderEntry.COLUMN_YEAR + " INTEGER NOT NULL, " +
                ReminderContract.ReminderEntry.COLUMN_MONTH + " INTEGER NOT NULL, " +
                ReminderContract.ReminderEntry.COLUMN_DAY + " INTEGER NOT NULL, " +
                ReminderContract.ReminderEntry.COLUMN_HOUR + " INTEGER NOT NULL, " +
                ReminderContract.ReminderEntry.COLUMN_MINUTE + " INTEGER NOT NULL, " +
                ReminderContract.ReminderEntry.COLUMN_TIMESTAMP + " TIMESTAMP DEFAULT CURRENT_TIMESTAMP" +
                ");";

        db.execSQL(SQL_CREATE_REMINDERLIST_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + ReminderContract.ReminderEntry.TABLE_NAME);
        onCreate(db);
    }
}

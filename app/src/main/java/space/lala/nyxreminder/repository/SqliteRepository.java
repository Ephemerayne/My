package space.lala.nyxreminder.repository;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import org.threeten.bp.LocalDateTime;

import java.util.ArrayList;

import space.lala.nyxreminder.database.DBHelper;
import space.lala.nyxreminder.database.ReminderContract;
import space.lala.nyxreminder.model.ReminderModel;

public class SqliteRepository implements ReminderRepository {

    private final SQLiteDatabase database;

    public SqliteRepository(DBHelper dbHelper) {
        database = dbHelper.getWritableDatabase();
    }

    @Override
    public ArrayList<ReminderModel> getAllReminders() {
        ArrayList<ReminderModel> reminderModels = new ArrayList<>();

        Cursor cursor = database.query(ReminderContract.ReminderEntry.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                null
        );

        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                String title = cursor.getString(cursor.getColumnIndex(ReminderContract.ReminderEntry.COLUMN_TITLE));
                String description = cursor.getString(cursor.getColumnIndex(ReminderContract.ReminderEntry.COLUMN_DESCRIPTION));
                int year = cursor.getInt(cursor.getColumnIndex(ReminderContract.ReminderEntry.COLUMN_YEAR));
                int month = cursor.getInt(cursor.getColumnIndex(ReminderContract.ReminderEntry.COLUMN_MONTH));
                int day = cursor.getInt(cursor.getColumnIndex(ReminderContract.ReminderEntry.COLUMN_DAY));
                int hour = cursor.getInt(cursor.getColumnIndex(ReminderContract.ReminderEntry.COLUMN_HOUR));
                int minute = cursor.getInt(cursor.getColumnIndex(ReminderContract.ReminderEntry.COLUMN_MINUTE));

                int id = cursor.getInt(cursor.getColumnIndex(ReminderContract.ReminderEntry._ID));
                LocalDateTime localDateTime = LocalDateTime.of(year, month, day, hour, minute);

                ReminderModel reminderModel = new ReminderModel(title, description, localDateTime, id);
                reminderModels.add(reminderModel);

                cursor.moveToNext();
            }
        }

        cursor.close();
        return reminderModels;
    }

    @Override
    public ReminderModel getReminder(int id) {

        String query = "SELECT * FROM " + ReminderContract.ReminderEntry.TABLE_NAME + " WHERE " + ReminderContract.ReminderEntry._ID + "='" + id + "'";
        Cursor cursor = database.rawQuery(query, null);

        cursor.moveToFirst();
        String title = cursor.getString(cursor.getColumnIndex(ReminderContract.ReminderEntry.COLUMN_TITLE));
        String description = cursor.getString(cursor.getColumnIndex(ReminderContract.ReminderEntry.COLUMN_DESCRIPTION));
        int year = cursor.getInt(cursor.getColumnIndex(ReminderContract.ReminderEntry.COLUMN_YEAR));
        int month = cursor.getInt(cursor.getColumnIndex(ReminderContract.ReminderEntry.COLUMN_MONTH));
        int day = cursor.getInt(cursor.getColumnIndex(ReminderContract.ReminderEntry.COLUMN_DAY));
        int hour = cursor.getInt(cursor.getColumnIndex(ReminderContract.ReminderEntry.COLUMN_HOUR));
        int minute = cursor.getInt(cursor.getColumnIndex(ReminderContract.ReminderEntry.COLUMN_MINUTE));

        LocalDateTime localDateTime = LocalDateTime.of(year, month, day, hour, minute);

        cursor.close();
        return new ReminderModel(title, description, localDateTime, id);
    }

    @Override
    public void updateReminder(int id) {

    }

    @Override
    public void deleteReminder(int id) {

    }

    @Override
    public void addReminder(ReminderModel reminderModel) {
        LocalDateTime dateTime = reminderModel.getDateTime();

        String title = reminderModel.getTitle();
        String description = reminderModel.getDescription();
        int year = dateTime.getYear();
        int month = dateTime.getMonthValue();
        int day = dateTime.getDayOfMonth();
        int hour = dateTime.getHour();
        int minute = dateTime.getMinute();

        ContentValues contentValues = new ContentValues();
        contentValues.put(ReminderContract.ReminderEntry.COLUMN_TITLE, title);
        contentValues.put(ReminderContract.ReminderEntry.COLUMN_DESCRIPTION, description);
        contentValues.put(ReminderContract.ReminderEntry.COLUMN_YEAR, year);
        contentValues.put(ReminderContract.ReminderEntry.COLUMN_MONTH, month);
        contentValues.put(ReminderContract.ReminderEntry.COLUMN_DAY, day);
        contentValues.put(ReminderContract.ReminderEntry.COLUMN_HOUR, hour);
        contentValues.put(ReminderContract.ReminderEntry.COLUMN_MINUTE, minute);

        database.insert(ReminderContract.ReminderEntry.TABLE_NAME, null, contentValues);
    }
}

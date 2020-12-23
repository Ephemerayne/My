package space.lala.nyxreminder.database;

import android.provider.BaseColumns;

public class ReminderContract {

    private ReminderContract() {
    }

    public static final class ReminderEntry implements BaseColumns {
        public static final String TABLE_NAME = "reminderList";
        public static final String COLUMN_TITLE = "title";
        public static final String COLUMN_DESCRIPTION = "description";
        public static final String COLUMN_YEAR = "year";
        public static final String COLUMN_MONTH = "month";
        public static final String COLUMN_DAY = "day";
        public static final String COLUMN_HOUR = "hour";
        public static final String COLUMN_MINUTE = "minute";
        public static final String COLUMN_TIMESTAMP = "timestamp";
    }
}

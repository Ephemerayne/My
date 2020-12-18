package space.lala.nyxreminder.model;

import java.util.Date;

public class ReminderModel {
    private final String title;
    private final String description;
    private final Date dateTime;


    public ReminderModel(String title, String description, Date dateTime) {
        this.title = title;
        this.description = description;
        this.dateTime = dateTime;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public Date getDateTime() {
        return dateTime;
    }
}

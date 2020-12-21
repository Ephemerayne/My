package space.lala.nyxreminder.model;

import java.util.Date;

public class ReminderModel {
    private final String title;
    private final String description;
    private final Date dateTime;
    private int id;
    private boolean isSelected = false;


    public ReminderModel(String title, String description, Date dateTime, int id) {
        this.title = title;
        this.description = description;
        this.dateTime = dateTime;
        this.id = id;
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

    public int getId() {
        return id;
    }


    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}


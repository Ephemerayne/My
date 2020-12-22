package space.lala.nyxreminder.model;

import org.threeten.bp.LocalDateTime;

public class ReminderModel {
    private final String title;
    private final String description;
    private final LocalDateTime dateTime;
    private int id;
    private boolean isSelected = false;


    public ReminderModel(String title, String description, LocalDateTime dateTime, int id) {
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

    public LocalDateTime getDateTime() {
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


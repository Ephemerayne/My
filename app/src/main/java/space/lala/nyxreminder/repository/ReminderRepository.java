package space.lala.nyxreminder.repository;

import java.util.ArrayList;

import space.lala.nyxreminder.model.ReminderModel;

public interface ReminderRepository {

    ArrayList<ReminderModel> getAllReminders();

    ReminderModel getReminder(int id);

    void updateReminder(int id);

    void deleteReminder(int id);

    void addReminder(ReminderModel reminderModel);
}

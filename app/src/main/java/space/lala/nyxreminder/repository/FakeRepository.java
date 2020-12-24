package space.lala.nyxreminder.repository;

import org.threeten.bp.LocalDateTime;

import java.sql.SQLOutput;
import java.util.ArrayList;

import space.lala.nyxreminder.model.ReminderModel;

public class FakeRepository implements ReminderRepository {
    ArrayList<ReminderModel> fakeReminders = new ArrayList<>();

    public FakeRepository() {
        fakeReminders.add(new ReminderModel("Покупки", "Купить хлеб, яйца, молоко", LocalDateTime.of(2020, 12, 05, 12, 8), 0));
        fakeReminders.add(new ReminderModel("Уборка", "Вымыть пол, протереть полки, пропылесосить", LocalDateTime.of(2018, 12, 05, 16, 8), 1));
        fakeReminders.add(new ReminderModel("Поздравить с праздником родственников", "Тетю, дядю, бабушку, маму и папу", LocalDateTime.of(2022, 12, 11, 12, 43), 2));
        fakeReminders.add(new ReminderModel("Пойти на прогулку", "", LocalDateTime.of(2020, 12, 05, 12, 8), 3));
        fakeReminders.add(new ReminderModel("Накормить кота", "", LocalDateTime.of(2014, 2, 03, 1, 18), 4));
    }

    @Override
    public ArrayList<ReminderModel> getAllReminders() {
        return fakeReminders;
    }


    @Override
    public ReminderModel getReminder(int id) {
        for (ReminderModel reminderModel : fakeReminders) {
            if (reminderModel.getId() == id) {
                return reminderModel;
            }
        }
        return null;
    }

    @Override
    public void updateReminder(int id, ReminderModel reminderModel) {

    }

    @Override
    public void deleteReminder(int id) {
        System.out.println("debug: " + id);
    }

    @Override
    public void addReminder(ReminderModel reminderModel) {

    }
}

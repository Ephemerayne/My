package space.lala.nyxreminder.repository;

import java.util.ArrayList;
import java.util.Date;

import space.lala.nyxreminder.model.ReminderModel;

public class FakeRepository implements ReminderRepository {
    ArrayList<ReminderModel> fakeReminders = new ArrayList<>();

    public FakeRepository() {
        fakeReminders.add(new ReminderModel("Покупки", "Купить хлеб, яйца, молоко", new Date(), 0));
        fakeReminders.add(new ReminderModel("Уборка", "Вымыть пол, протереть полки, пропылесосить", new Date(), 1));
        fakeReminders.add(new ReminderModel("Поздравить с праздником родственников", "Тетю, дядю, бабушку, маму и папу", new Date(), 2));
        fakeReminders.add(new ReminderModel("Пойти на прогулку", "", new Date(), 3));
        fakeReminders.add(new ReminderModel("Накормить кота", "", new Date(), 4));
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
    public void updateReminder(int id) {

    }

    @Override
    public void deleteReminder(int id) {
        System.out.println("debug: " + id);
    }
}

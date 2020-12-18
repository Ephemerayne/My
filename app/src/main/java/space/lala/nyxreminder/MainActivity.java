package space.lala.nyxreminder;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.Date;

import space.lala.nyxreminder.adapter.ReminderAdapter;
import space.lala.nyxreminder.model.ReminderModel;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerViewReminder;
    ReminderAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerViewReminder = findViewById(R.id.recycler_view_reminders);
        adapter = new ReminderAdapter(reminders());
        recyclerViewReminder.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewReminder.setAdapter(adapter);
    }

    private ArrayList<ReminderModel> reminders() {
        ArrayList<ReminderModel> reminders = new ArrayList<>();
        reminders.add(new ReminderModel("Покупки", "Купить хлеб, яйца, молоко", new Date()));
        reminders.add(new ReminderModel("Уборка", "Вымыть пол, протереть полки, пропылесосить", new Date()));
        reminders.add(new ReminderModel("Поздравить с праздником родственников", "Тетю, дядю, бабушку, маму и папу", new Date()));
        reminders.add(new ReminderModel("Пойти на прогулку", "", new Date()));
        reminders.add(new ReminderModel("Накормить кота", "", new Date()));
        return reminders;
    }
}
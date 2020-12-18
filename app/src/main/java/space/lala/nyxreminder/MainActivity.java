package space.lala.nyxreminder;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;
import java.util.Date;

import space.lala.nyxreminder.adapter.ReminderAdapter;
import space.lala.nyxreminder.model.ReminderModel;
import space.lala.nyxreminder.repository.FakeRepository;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerViewReminder;
    ReminderAdapter adapter;
    FakeRepository fakeRepository = new FakeRepository();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerViewReminder = findViewById(R.id.recycler_view_reminders);
        adapter = new ReminderAdapter(fakeRepository.getAllReminders());
        recyclerViewReminder.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewReminder.setAdapter(adapter);
    }
}
package space.lala.nyxreminder;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;
import java.util.Date;

import space.lala.nyxreminder.adapter.ReminderAdapter;
import space.lala.nyxreminder.model.ReminderModel;
import space.lala.nyxreminder.repository.FakeRepository;

public class MainActivity extends AppCompatActivity implements OnReminderClickListener {

    private RecyclerView recyclerViewReminder;
    ReminderAdapter adapter;
    FakeRepository fakeRepository = new FakeRepository();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerViewReminder = findViewById(R.id.recycler_view_reminders);
        adapter = new ReminderAdapter(fakeRepository.getAllReminders(), this);
        recyclerViewReminder.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewReminder.setAdapter(adapter);
    }

    @Override
    public void onReminderClick(int id) {
        ViewReminderDialog fragment = new ViewReminderDialog();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        fragment.show(transaction, "tag");
    }
}
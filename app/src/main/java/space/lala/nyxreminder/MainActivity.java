package space.lala.nyxreminder;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;

import java.util.ArrayList;

import space.lala.nyxreminder.adapter.ReminderAdapter;
import space.lala.nyxreminder.model.ReminderModel;
import space.lala.nyxreminder.repository.FakeRepository;
import space.lala.nyxreminder.repository.ReminderRepository;

public class MainActivity extends AppCompatActivity implements OnReminderListener {

    private RecyclerView recyclerViewReminder;
    ReminderAdapter adapter;
    ReminderRepository fakeRepository = new FakeRepository();
    private MenuItem deleteMenuItem;
    ArrayList<Integer> itemsToRemove = new ArrayList();
    private boolean isSelectModeActive = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageView trashBasket = (ImageView) findViewById(R.id.icon_trash_basket);
        // Drawable icon = trashBasket.getDrawable();
        recyclerViewReminder = findViewById(R.id.recycler_view_reminders);
        adapter = new ReminderAdapter(this);
        adapter.setReminders(fakeRepository.getAllReminders());
        recyclerViewReminder.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewReminder.setAdapter(adapter);
    }

    @Override
    public void onReminderClick(int id) {

        if (!isSelectModeActive) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            ViewReminderDialog.newInstance(id).show(transaction, "tag");
        }

        ArrayList<ReminderModel> reminders = new ArrayList<>(adapter.getReminders());
        for (ReminderModel reminder : reminders) {
            if (isSelectModeActive && reminder.getId() == id) {
                reminder.setSelected(!reminder.isSelected());
                manageItemsToRemove(reminder);
            }
        }

        adapter.setReminders(reminders);
    }

    @Override
    public void onReminderLongClick(int id) {
        ArrayList<ReminderModel> reminders = new ArrayList<>(adapter.getReminders());
        for (ReminderModel reminder : reminders) {
            if (reminder.getId() == id) {
                reminder.setSelected(!reminder.isSelected());
                manageItemsToRemove(reminder);
            }
        }

        adapter.setReminders(reminders);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.icon_trash_basket:
                deleteItems();
                return true;
            default:
                return false;
        }
    }

    private void deleteItems() {
        for (int id : itemsToRemove) {
            fakeRepository.deleteReminder(id);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.actionbar_icon_trashbasket, menu);

        deleteMenuItem = menu.findItem(R.id.icon_trash_basket);

        deleteMenuItem.setEnabled(true);
        return true;
    }

    private void showDeleteButton(boolean willShow) {
        deleteMenuItem.setVisible(willShow);
        deleteMenuItem.setEnabled(willShow);
    }

    private void manageItemsToRemove (ReminderModel reminder) {
        if (reminder.isSelected()) {
            itemsToRemove.add(reminder.getId());
        } else {
            itemsToRemove.remove((Integer) reminder.getId());
        }

        if (itemsToRemove.isEmpty()){
            isSelectModeActive = false;
        } else {
            isSelectModeActive = true;
        }
    }
}
package space.lala.nyxreminder.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import space.lala.nyxreminder.OnReminderListener;
import space.lala.nyxreminder.R;
import space.lala.nyxreminder.model.ReminderModel;

public class ReminderAdapter extends RecyclerView.Adapter<ReminderViewHolder> {

    private final ArrayList<ReminderModel> reminders = new ArrayList<>();
    OnReminderListener onReminderListener;

    public ReminderAdapter(OnReminderListener onReminderListener) {
        this.onReminderListener = onReminderListener;
    }

    public void setReminders(List<ReminderModel> reminders) {
        Collections.sort(reminders, (reminderModel, reminderModel2) -> {
            if (reminderModel.getDateTime() == null || reminderModel2.getDateTime() == null) {
                return 0;
            }
            return reminderModel.getDateTime().compareTo(reminderModel2.getDateTime());
        });

        this.reminders.clear();
        this.reminders.addAll(reminders);
        notifyDataSetChanged();
    }

    public ArrayList<ReminderModel> getReminders() {
        return reminders;
    }

    @NonNull
    @Override
    public ReminderViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.reminder_item, viewGroup, false);
        return new ReminderViewHolder(view, onReminderListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ReminderViewHolder holder, int position) {
        ReminderModel reminder = reminders.get(position);
        holder.setItemContent(reminder, reminders, position);
    }

    @Override
    public int getItemCount() {
        return reminders.size();
    }
}

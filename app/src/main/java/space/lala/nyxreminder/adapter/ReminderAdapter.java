package space.lala.nyxreminder.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import space.lala.nyxreminder.OnReminderClickListener;
import space.lala.nyxreminder.R;
import space.lala.nyxreminder.model.ReminderModel;

public class ReminderAdapter extends RecyclerView.Adapter<ReminderViewHolder> {

    private ArrayList<ReminderModel> reminders;
    OnReminderClickListener onReminderClickListener;

    public ReminderAdapter(ArrayList<ReminderModel> reminders, OnReminderClickListener onReminderClickListener) {
        this.reminders = reminders;
        this.onReminderClickListener = onReminderClickListener;
    }

    @NonNull
    @Override
    public ReminderViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.reminder_item, viewGroup, false);
        return new ReminderViewHolder(view, onReminderClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ReminderViewHolder holder, int position) {
        ReminderModel reminder = reminders.get(position);
        holder.setItemContent(reminder);
    }

    @Override
    public int getItemCount() {
        return reminders.size();
    }

}

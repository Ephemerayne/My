package space.lala.nyxreminder.adapter;

import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.Date;

import space.lala.nyxreminder.OnReminderListener;
import space.lala.nyxreminder.R;
import space.lala.nyxreminder.model.ReminderModel;


public class ReminderViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {

    private final TextView title;
    private final TextView description;
    private TextView date;
    private TextView time;
    OnReminderListener onReminderListener;

    public ReminderViewHolder(@NonNull View itemView, OnReminderListener onReminderListener) {
        super(itemView);
        title = itemView.findViewById(R.id.view_title);
        description = itemView.findViewById(R.id.view_description);
        date = itemView.findViewById(R.id.view_date);
        time = itemView.findViewById(R.id.view_time);
        itemView.setOnClickListener(this);
        itemView.setOnLongClickListener(this);
        this.onReminderListener = onReminderListener;

    }

    public void setItemContent(ReminderModel reminderModel) {
        String dateString = android.text.format.DateFormat.format("dd.MM.yy", new Date()).toString();
        String timeString = android.text.format.DateFormat.format("hh:mm", new Date()).toString();

        title.setText(reminderModel.getTitle());
        description.setText(reminderModel.getDescription());
        date.setText(dateString);
        time.setText(timeString);

        if (reminderModel.isSelected()) {
            itemView.setBackgroundColor(itemView.getResources().getColor(R.color.light_blue));
        } else {
            itemView.setBackgroundColor(itemView.getResources().getColor(android.R.color.transparent));
        }
    }

    @Override
    public void onClick(View view) {
        onReminderListener.onReminderClick(getAdapterPosition());
    }


    @Override
    public boolean onLongClick(View view) {
        onReminderListener.onReminderLongClick(getAdapterPosition());
        return true;
    }
}

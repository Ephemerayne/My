package space.lala.nyxreminder;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import java.util.Date;
import java.util.Objects;

import space.lala.nyxreminder.model.ReminderModel;
import space.lala.nyxreminder.repository.FakeRepository;
import space.lala.nyxreminder.repository.ReminderRepository;


public class ViewReminderDialog extends DialogFragment {

    private static final String idKey = "idKey";
    private int id;
    private ReminderRepository reminderRepository = new FakeRepository();
    TextView dateTime;
    TextView title;
    TextView description;

    public static ViewReminderDialog newInstance(int id) {
        ViewReminderDialog viewReminderDialog = new ViewReminderDialog();
        Bundle args = new Bundle();
        args.putInt(idKey, id);
        viewReminderDialog.setArguments(args);
        return viewReminderDialog;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.view_reminder_dialog, container, false);
        dateTime = view.findViewById(R.id.view_date_time);
        title = view.findViewById(R.id.view_dialog_title);
        description = view.findViewById(R.id.view_dialog_description);

        if (getArguments() != null) {
            id = getArguments().getInt(idKey);
            ReminderModel reminderModel = reminderRepository.getReminder(id);
            String titleString = reminderModel.getTitle();
            title.setText(titleString);
            String descString = reminderModel.getDescription();
            description.setText(descString);
            String dateString = android.text.format.DateFormat.format("dd.MM.yy", new Date()).toString();
            String timeString = android.text.format.DateFormat.format("hh:mm", new Date()).toString();
            dateTime.setText(getString(R.string.on_date_in_time, dateString, timeString));
        }
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        WindowManager.LayoutParams params = Objects.requireNonNull(getDialog()).getWindow().getAttributes();
        params.width = ViewGroup.LayoutParams.MATCH_PARENT;
        getDialog().getWindow().setAttributes(params);
    }
}

package space.lala.nyxreminder;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import java.util.Date;
import java.util.Objects;

import space.lala.nyxreminder.database.DBHelper;
import space.lala.nyxreminder.model.ReminderModel;
import space.lala.nyxreminder.repository.ReminderRepository;
import space.lala.nyxreminder.repository.SqliteRepository;


public class ViewReminderDialog extends DialogFragment {

    private static final String idKey = "idKey";
    private int id;
    private DBHelper dbHelper;
    private ReminderRepository repository;
    private TextView dateTime;
    private TextView title;
    private TextView description;
    private Button editReminder;
    private ImageButton close;

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

        initDb();

        dateTime = view.findViewById(R.id.view_date_time);
        title = view.findViewById(R.id.view_dialog_title);
        description = view.findViewById(R.id.view_dialog_description);
        editReminder = (Button) view.findViewById(R.id.view_dialog_button_edit);
        close = view.findViewById(R.id.close_button_view_dialog);

        if (getArguments() != null) {
            id = getArguments().getInt(idKey);
            ReminderModel reminderModel = repository.getReminder(id);
            setReminderDataForAdd(reminderModel);
        }
        initListeners();

        return view;
    }

    private void initListeners() {

        editReminder.setOnClickListener(view -> {
            dismiss();
            ((MainActivity) getActivity()).openAddEditDialog(id);
        });

        close.setOnClickListener(view -> dismiss());
    }


    private void initDb() {
        dbHelper = new DBHelper(getContext());
        repository = new SqliteRepository(dbHelper);
    }

    //Программная настройка ширины диалогового окна
    @Override
    public void onResume() {
        super.onResume();
        WindowManager.LayoutParams params = Objects.requireNonNull(getDialog()).getWindow().getAttributes();
        params.width = ViewGroup.LayoutParams.MATCH_PARENT;
        getDialog().getWindow().setAttributes(params);
    }

    private void setReminderDataForAdd(ReminderModel reminderModel) {
        String titleString = reminderModel.getTitle();
        title.setText(titleString);
        String descString = reminderModel.getDescription();
        description.setText(descString);
        String dateString = android.text.format.DateFormat.format("dd.MM.yy", new Date()).toString();
        String timeString = android.text.format.DateFormat.format("hh:mm", new Date()).toString();
        dateTime.setText(getString(R.string.on_date_in_time, dateString, timeString));

        //Скрывает строку с описанием напоминания, если она не была заполнена (убирает свободное пространство)
        if (reminderModel.getDescription().isEmpty()) {
            description.setVisibility(View.GONE);
        }
    }
}

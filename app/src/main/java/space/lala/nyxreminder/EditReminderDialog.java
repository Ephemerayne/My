package space.lala.nyxreminder;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import java.util.Objects;

public class EditReminderDialog extends DialogFragment {

    private EditText title;
    private EditText description;
    private TextView date;
    private TextView time;
    private Button save;
    private Button cancel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.edit_reminder_dialog, container, false);
        title = view.findViewById(R.id.title_edit_text);
        description = view.findViewById(R.id.description_edit_text);
        date = view.findViewById(R.id.edit_date);
        time = view.findViewById(R.id.edit_time);
        save = view.findViewById(R.id.button_save_reminder);
        cancel = view.findViewById(R.id.button_cancel_edit_reminder);

        initButtons();

        return view;
    }

    //метод установки листенеров кнопок
    private void initButtons() {
        cancel.setOnClickListener(view -> dismiss());

        save.setOnClickListener(view -> {

        });
    }

    //Программная настройка ширины диалогового окна
    @Override
    public void onResume() {
        super.onResume();
        WindowManager.LayoutParams params = Objects.requireNonNull(getDialog().getWindow().getAttributes());
        params.width = ViewGroup.LayoutParams.MATCH_PARENT;
        getDialog().getWindow().setAttributes(params);
    }
}

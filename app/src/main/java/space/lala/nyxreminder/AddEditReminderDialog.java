package space.lala.nyxreminder;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import com.jakewharton.threetenabp.AndroidThreeTen;
import org.threeten.bp.LocalDate;
import org.threeten.bp.LocalDateTime;
import org.threeten.bp.LocalTime;
import org.threeten.bp.format.DateTimeFormatter;
import java.util.Objects;

import space.lala.nyxreminder.database.DBHelper;
import space.lala.nyxreminder.model.ReminderModel;
import space.lala.nyxreminder.repository.FakeRepository;
import space.lala.nyxreminder.repository.ReminderRepository;
import space.lala.nyxreminder.repository.SqliteRepository;


public class AddEditReminderDialog extends DialogFragment {

    private EditText title;
    private EditText description;
    private TextView date;
    private TextView time;
    private Button save;
    private Button cancel;
    private LocalDate reminderDate;
    private LocalTime reminderTime;

    //Слушатель нажатия по дате
    DatePickerDialog.OnDateSetListener dateSetListener = (DatePicker datePicker, int year, int month, int day) -> {

        //Нужно +1, так как месяцы идут с индексом от 0
        month += 1;

        //метод установки даты в textView
        setDate(year, month, day);
    };

    //Слушатель нажатия по времени
    TimePickerDialog.OnTimeSetListener timeSetListener = (TimePicker timePicker, int hour, int minute) -> {

        //метод установки времени в textView
        setTime(hour, minute);
    };

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //инициализация из библиотеки ThreeTen для корректного отображения часовых поясов
        AndroidThreeTen.init(getContext());
    }

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

        //инициализация листенеров (слушателей кликов(событий))
        initListeners();

        //метод установки текущей даты и времени в окно добавления/изменения напоминания
        fillInDefaultDateTime();

        return view;
    }

    //метод установки листенеров кнопок
    private void initListeners() {
        cancel.setOnClickListener(view -> dismiss());

        save.setOnClickListener(view -> saveReminder(createReminder()));

        date.setOnClickListener(view -> showDatePicker());

        time.setOnClickListener(view -> showTimePicker());
    }

    @Override
    public void onResume() {
        super.onResume();

        //Программная настройка ширины диалогового окна
        WindowManager.LayoutParams params = Objects.requireNonNull(getDialog().getWindow().getAttributes());
        params.width = ViewGroup.LayoutParams.MATCH_PARENT;
        getDialog().getWindow().setAttributes(params);
    }

    // метод создания напоминания
    private ReminderModel createReminder() {

        //Берет данные из UI (ввода пользователя) и делает модель Ремайндера
        String reminderTitle = title.getText().toString();
        String reminderDescription = description.getText().toString();
        LocalDateTime dateTime = LocalDateTime.of(reminderDate, reminderTime);
        return new ReminderModel(reminderTitle, reminderDescription, dateTime, 5);
    }

    //метод сохранения напоминания в БД
    private void saveReminder(ReminderModel reminderModel) {
        DBHelper dbHelper = new DBHelper(getContext());
        ReminderRepository repository = new SqliteRepository(dbHelper);
        repository.addReminder(reminderModel);
    }

    //метод заполнения дефолтных значений времени и даты
    private void fillInDefaultDateTime() {
        LocalDateTime localDateTime = LocalDateTime.now();

        //Форматирование времени
        final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd.MM.yy");
        final DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");

        //присваивание переменной String форматтер времени
        String dateString = dateFormatter.format(localDateTime);
        String timeString = timeFormatter.format(localDateTime);

        //установка полученных значений в необходимые TextView

        date.setText(dateString);
        time.setText(timeString);

        reminderDate = localDateTime.toLocalDate();
        reminderTime = localDateTime.toLocalTime();
    }

    //метод показа окна календаря для выбора даты
    private void showDatePicker() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {

            //обработка клика по дате и установка пользовательского выбора
            DatePickerDialog datePickerDialog = new DatePickerDialog(getContext());
            datePickerDialog.setOnDateSetListener(dateSetListener);
            datePickerDialog.show();
        }
    }

    //метод выбора даты в календаре и ее установка в напоминание
    private void setDate(int year, int month, int day) {
        LocalDate localDate = LocalDate.of(year, month, day);

        //Форматирование даты
        final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd.MM.yy");

        //установка даты в TextView
        date.setText(dateFormatter.format(localDate));
        reminderDate = localDate;
    }

    //метод показа часов для выбора времени
    private void showTimePicker() {
        LocalTime localTime = LocalTime.now();
        int hour = localTime.getHour();
        int minute = localTime.getMinute();

        //обработка клика по часам и установка пользовательского выбора
        TimePickerDialog timePickerDialog = new TimePickerDialog(getContext(), timeSetListener, hour, minute, true);
        timePickerDialog.show();
    }

    //метод выбора времени в часах и их установка в напоминание
    private void setTime(int hour, int minute) {
        LocalTime localTime = LocalTime.of(hour, minute);

        //Форматирование времени
        final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm");

        //установка времени в TextView
        time.setText(dateTimeFormatter.format(localTime));
        reminderTime = localTime;
    }
}


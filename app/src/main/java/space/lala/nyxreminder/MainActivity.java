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

    //ресайклер (для генерируемого списка), адаптер (для ресайклера) и БД
    private RecyclerView recyclerViewReminder;
    ReminderAdapter adapter;
    ReminderRepository fakeRepository = new FakeRepository();
    ImageView trashBasket;

    //корзина в экшн баре
    private MenuItem deleteMenuItem;

    //Массив выделенных для удаления айтемов
    ArrayList<Integer> itemsToRemove = new ArrayList();
    //булиан, отражающий, активен ли режим выбора элементов или нет.
    private boolean isSelectModeActive = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        trashBasket = findViewById(R.id.icon_trash_basket);
        recyclerViewReminder = findViewById(R.id.recycler_view_reminders);

        //установка адаптера со слушателем кликов и зажатий
        adapter = new ReminderAdapter(this);

        //установка списка айтемов в адаптер
        //айтемы приходят из БД.
        adapter.setReminders(fakeRepository.getAllReminders());

        //установка ресайклера и адаптера к нему
        recyclerViewReminder.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewReminder.setAdapter(adapter);
    }

    @Override
    //обработка клика по айтему (напоминанию)
    public void onReminderClick(int id) {

        //условие: если не активен режим выбора айтемов (напоминания), то при нажатии открывается диалоговое окно информации о напоминании и его редактирование
        if (!isSelectModeActive) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            ViewReminderDialog.newInstance(id).show(transaction, "tag");
        }

        //Массив напоминаний, взятых из адаптера
        ArrayList<ReminderModel> reminders = new ArrayList<>(adapter.getReminders());

        //цикл для переборки всех моделей
        for (ReminderModel reminder : reminders) {

            //условие: если активен режим выбора айтемов и id айтема совпадает с тем, на котором произошло событие клик.
            if (isSelectModeActive && reminder.getId() == id) {

                //напоминанию устанавливается значение isSelected, обратное тому, которое у него в данный момент.
                reminder.setSelected(!reminder.isSelected());
                // управление массивом айтемов для удаления. Добавление/удаление айдишников айтемов в массив. И управление режимом выбора элементов
                manageItemsToRemove(reminder);
                setSelectModeActive(reminder);
            }
        }
        //установка айтемов обратно в адаптер из массива reminders
        adapter.setReminders(reminders);
    }

    @Override
    //обработка долгого нажатия на айтем
    public void onReminderLongClick(int id) {
        ArrayList<ReminderModel> reminders = new ArrayList<>(adapter.getReminders());
        for (ReminderModel reminder : reminders) {

            // если id айтема из массива совпадает с id айтема, на котором произошло событие,
            if (reminder.getId() == id) {
                //мы сделаем с ним следующее:
                reminder.setSelected(!reminder.isSelected());
                manageItemsToRemove(reminder);
                setSelectModeActive(reminder);
            }
        }

        adapter.setReminders(reminders);
    }

    @Override
    //Если айтем был выбран, становится видимой корзина для удаления
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.icon_trash_basket:
                // метод удаления айтемов
                deleteItems();

                //возвращение данных при состоявшемся удалении
                return true;
            //если удаление не было совершено, то возвращение того состояния, которое было до активации режима выбора айтемов
            default:
                return false;
        }
    }

    //метод удаления айтемов (напоминаний)
    private void deleteItems() {

        //цикл, проходящий по массиву айдишников айтемов, выбранных для удаления
        for (int id : itemsToRemove) {

            //удаление напоминания (по id) из БД (фейкового репозитория)
            fakeRepository.deleteReminder(id);
        }
    }

    @Override

    //создание опции удаления в экшн баре (корзина)
    public boolean onCreateOptionsMenu(Menu menu) {

        // отрисовка меню экшн бара
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.actionbar_icon_trashbasket, menu);

        //присваивание id из layout иконки для переменной
        deleteMenuItem = menu.findItem(R.id.icon_trash_basket);

        //кликабельность иконки
        deleteMenuItem.setEnabled(true);
        return true;
    }

    //метод для управления кликабельностью и видимостью кнопки удаления (иконки корзины)
    private void showDeleteButton(boolean willShow) {
        //ее видимость
        deleteMenuItem.setVisible(willShow);
        //ее кликабельность
        deleteMenuItem.setEnabled(willShow);
    }

    //метод для управления массивом айдишников айтемов для удаления
    private void manageItemsToRemove(ReminderModel reminder) {

        //если айтем (напоминание) был выбран
        if (reminder.isSelected()) {

            //происходит добавление этого айтема (по id) в массив выделенных для их удаления
            itemsToRemove.add(reminder.getId());

            // в ином случае
        } else {

            //удаляется из этого массива
            itemsToRemove.remove((Integer) reminder.getId());
        }
    }

    private void setSelectModeActive(ReminderModel reminder) {

        // если список массива выделенных для удаления айтемов пуст
        if (itemsToRemove.isEmpty()) {

            //режим выделения айтемов не активен
            isSelectModeActive = false;

            //иначе активен
        } else {
            isSelectModeActive = true;
        }
    }
}
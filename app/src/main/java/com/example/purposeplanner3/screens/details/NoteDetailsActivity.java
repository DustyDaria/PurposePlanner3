package com.example.purposeplanner3.screens.details;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.appcompat.app.AppCompatActivity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;

import com.example.purposeplanner3.App;
import com.example.purposeplanner3.R;
import com.example.purposeplanner3.model.Note;

import java.util.Date;

//Activity для редактирования и создания заметки
public class NoteDetailsActivity extends AppCompatActivity {

    //передача всей заметки целиком
    private static final String EXTRA_NOTE = "NoteDetailsActivity.EXTRA_NOTE";

    //заметка, которую мы в данный момент создаем или редактируем
    private Note note;

    //текстовое поле для ввода
    private EditText editText;

    private EditText editTextMap;

    //карта
    public LocationManager locationManager;


    //функция, необходимая для вызова одного Activity из другого
    public static void start(Activity caller, Note note) {
        Intent intent = new Intent(caller, NoteDetailsActivity.class);
        if (note != null) { //Если заметка != null, то добавим эту заметку к нашему intent
            intent.putExtra(EXTRA_NOTE, note);
        }
        caller.startActivity(intent); //запускаем Activity
    }

    /*public static void startMap(Activity caller, Note note) {
        Intent intent = new Intent(caller, MapsActivity.class);
        if (note != null) {
            intent.putExtra(EXTRA_NOTE, note);
        }
        caller.startActivity(intent);
    }*/

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_note_details);

        Toolbar toolbar = findViewById(R.id.toolbar);
        //Задание панели инструментов (toolbar) в качестве панели действий (Action Bar)
        setSupportActionBar(toolbar);
        //Кнопка "Назад"
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        //Задание текста заголовка
        setTitle(getString(R.string.note_details_title));

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        editText = findViewById(R.id.text);
        editTextMap = findViewById(R.id.textMap);

        //Функция getIntent() возвращает intent, который использовался при запуске Activity
        if (getIntent().hasExtra(EXTRA_NOTE)) {
            note = getIntent().getParcelableExtra(EXTRA_NOTE);
            editText.setText(note.text);
            //Если intent есть, то задаем сразу тот текст, который находится внутри заметки
        } else { //Иначе создаем новую заметку
            note = new Note();
        }


    }


    @Override
    protected void onResume() {
        super.onResume();
        if (ActivityCompat.checkSelfPermission(NoteDetailsActivity.this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(NoteDetailsActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                    1000 * 10, 10, locationListener);

            locationManager.requestLocationUpdates(
                    LocationManager.NETWORK_PROVIDER, 1000 * 10, 10,
                    locationListener);
            return;
        }

        checkEnabled();
    }

    @Override
    protected void onPause() {
        super.onPause();
        locationManager.removeUpdates(locationListener);
    }

    public LocationListener locationListener = new LocationListener() {

        @Override
        public void onLocationChanged(Location location) {
            showLocation(location);
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
        }

        @Override
        public void onProviderDisabled(String provider) {
            checkEnabled();
        }

        @Override
        public void onProviderEnabled(String provider) {

            if (ActivityCompat.checkSelfPermission(NoteDetailsActivity.this, Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED
                    && ActivityCompat.checkSelfPermission(NoteDetailsActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED) {
                checkEnabled();
                showLocation(locationManager.getLastKnownLocation(provider));
            }

        }

       /* @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
            if (provider.equals(LocationManager.GPS_PROVIDER)) {
                tvStatusGPS.setText("Status: " + String.valueOf(status));
            } else if (provider.equals(LocationManager.NETWORK_PROVIDER)) {
                tvStatusNet.setText("Status: " + String.valueOf(status));
            }
        }*/
    };

    private void showLocation(Location location) {
        if (location == null)
            return;
        if (location.getProvider().equals(LocationManager.GPS_PROVIDER)) {
            editTextMap.setText(formatLocation(location));
        } else if (location.getProvider().equals(
                LocationManager.NETWORK_PROVIDER)) {
            editTextMap.setText(formatLocation(location));
        }
    }

    private String formatLocation(Location location) {
        if (location == null)
            return "";
        return String.format("Coordinates: lat = %1$.4f, lon = %2$.4f, time = %3$tF %3$tT",
                location.getLatitude(), location.getLongitude(), new Date(
                        location.getTime()));
    }

    private void checkEnabled() {
        Toast.makeText(this, "Enabled: "
                        + locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER),
                Toast.LENGTH_SHORT).show();
        Toast.makeText(this, "Enabled: "
                        + locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER),
                Toast.LENGTH_SHORT).show();
        /*tvEnabledGPS.setText("Enabled: "
                + locationManager
                .isProviderEnabled(LocationManager.GPS_PROVIDER));
        tvEnabledNet.setText("Enabled: "
                + locationManager
                .isProviderEnabled(LocationManager.NETWORK_PROVIDER));*/
    }

    /*public void onClickLocationSettings(View view) {
        startActivity(new Intent(
                android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
    };*/

    //Создание меню
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //Извлекаем меню (выполняет роль кнопки сохранения)
        getMenuInflater().inflate(R.menu.menu_details, menu);
        return super.onCreateOptionsMenu(menu);
    }

    //обработка события для нажатия на кнопки в панели инструментов (toolbar),
    // которая выполняет роль панели действий (Action Bar)
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home: //Если это кнопка "Назад"
                finish(); //Завершаем Activity
                break;
            case R.id.action_save: //Если это кнопка "Сохранить", то сохраняем нашу заметку в БД
                if (editText.getText().length() > 0) { //Проверить, что пользователь ввел хоть какой-то текст
                    note.text = editText.getText().toString(); //Текст заметки берем из текстового поля
                    note.done = false; // Дело по умолчанию "не завершено"
                    note.timestamp = System.currentTimeMillis(); //Время создания
                    note.text = editTextMap.getText().toString(); //Координаты с карты

                    if (getIntent().hasExtra(EXTRA_NOTE)) { // Проверка на создание/редактирование заметки
                        App.getInstance().getNoteDao().update(note); //редактирование
                    } else {
                        App.getInstance().getNoteDao().insert(note);  //создание
                    }
                    finish(); //Завершаем Activity
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    /*public void onClickMaps(View view) {
        MapsActivity.startMap(NoteDetailsActivity.this, null);
    }*/
}

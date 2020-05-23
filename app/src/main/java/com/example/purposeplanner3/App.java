package com.example.purposeplanner3;

import android.app.Application;

import androidx.room.Room;

import com.example.purposeplanner3.data.AppDatabase;
import com.example.purposeplanner3.data.NoteDao;

//класс Application "живет" и сохраняет БД все время работы приложения
public class App extends Application {

    //объект класса AppDataBase
    private AppDatabase database;
    private NoteDao noteDao;

    private static App instance;

    public static App getInstance() {
        return instance;
    }

    //Выполнение метода при любом старте приложения
    @Override
    public void onCreate() {
        super.onCreate();

        instance = this;

        //создание БД при любом старте приложения
        database = Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, "app-db-name").allowMainThreadQueries().build();
        //.allowMainThreadQueries() - разрешения делать запросы к БД из основного потока
        // (для простоты в данном приложении). Запросы к БД обычно занимают очень много времени
        // и делать их в основном потоке не следует,
        // нужно выносить все подобные операции с БД в бэкграунд-потоки


        //получение объекта данных
        noteDao = database.noteDao();
    }

    public AppDatabase getDatabase() {
        return database;
    }

    public void setDatabase(AppDatabase database) {
        this.database = database;
    }

    public NoteDao getNoteDao() {
        return noteDao;
    }

    public void setNoteDao(NoteDao noteDao) {
        this.noteDao = noteDao;
    }
}

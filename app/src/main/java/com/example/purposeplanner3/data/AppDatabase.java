package com.example.purposeplanner3.data;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.purposeplanner3.model.Note;

//этот класс относится ко всем сущностям сразу (представляет из себя всю БД в целом)
//entities - указание сущностей для БД
//version - этот параметр необходим для корректно работы БД в уже запущенном "в люди" приложении.
//при обновлении приложения, если понадобится добавить новые таблицы и новые поля к уже существующим
//таблицам, то не придется каждый раз удалять БД и создавать ее заново.
// Зная версию текущей БД, и зная версию новой БД, можно выполнить скрипт миграции БД
@Database(entities = {Note.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    public abstract  NoteDao noteDao();
}
//когда мы создаем класс AppDataBase, автоматически генерируется реализация интерфейса NoteDao
//все генерируется библиотекой Room, необходимо только предоставить описание
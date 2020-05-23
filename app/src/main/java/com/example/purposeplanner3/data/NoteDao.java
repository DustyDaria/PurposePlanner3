package com.example.purposeplanner3.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.purposeplanner3.model.Note;

import java.util.List;

//класс, описывающий интерфейс для связи с БД
@Dao
public interface NoteDao {

    //запрос для возврата списка
    @Query("SELECT * FROM Note")
    List<Note> getAll();

    //запрос для обновления списка с данными
    //LiveData - специальный объект, на который может подписаться пользовательский интерфейс
    //(каждый раз, когда в таблице с заметками будет что-то меняться, LiveData будет обновляться
    // и сообщать всем своим подписчикам, что данные обновились)
    @Query("SELECT * FROM Note")
    LiveData<List<Note>> getAllLiveData();

    //загрузить все заметки, у который id из списка
    //двоеточие перед параматром означает, что в это место подставляется параметр с соответствующим именем
    @Query("SELECT * FROM Note WHERE uid IN (:noteIds)")
    List<Note> loadAllByIds(int[] noteIds);

    @Query("SELECT * FROM Note WHERE uid = :uid LIMIT 1")
    Note findById(int uid);

    //(onConflict) если я захочу вставить заметку в БД с id, который уже существует,
    // то вместо вставки будет произведена замена старой сущности на новую.
    //Если этого не написать, то попытка вставить в БД сущность, id которой в БД уже есть,
    // приведет к тому что ничего не будет выполнено
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Note note);

    @Update
    void update(Note note);

    @Delete
    void delete(Note note);

}
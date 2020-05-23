package com.example.purposeplanner3.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

//этот класс описывает конкретную сущность
@Entity
public class Note implements Parcelable {
    //Интерфейс Parcelable предоставляеет возможность передавать Note между Activity

    // уникальный индентификатор для каждой сущности
    @PrimaryKey(autoGenerate = true) // автоинкремент
    public int uid;

    // текст заметки
    @ColumnInfo(name = "text") // названия колонок в БД, в которой будут сохраняться соответствуюшие поля
    public String text;

    // время создания заметки, для упорядочивания
    @ColumnInfo(name = "timestamp")
    public long timestamp;

    // сделано/не сделано дело
    @ColumnInfo(name = "done")
    public boolean done;

    // Данные, полученные с карты
    @ColumnInfo(name = "textMaps")
    @NonNull public String textMaps;

    public Note() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Note note = (Note) o;

        if (uid != note.uid) return false;
        if (timestamp != note.timestamp) return false;
        if (done != note.done) return false;
        return text != null ? text.equals(note.text) : note.text == null;
    }

    @Override
    public int hashCode() {
        int result = uid;
        result = 31 * result + (text != null ? text.hashCode() : 0);
        result = 31 * result + (int) (timestamp ^ (timestamp >>> 32));
        result = 31 * result + (done ? 1 : 0);
        return result;
    }

    protected Note(Parcel in) {
        uid = in.readInt();
        text = in.readString();
        timestamp = in.readLong();
        done = in.readByte() != 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(uid);
        dest.writeString(text);
        dest.writeLong(timestamp);
        dest.writeByte((byte) (done ? 1 : 0));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Note> CREATOR = new Creator<Note>() {
        @Override
        public Note createFromParcel(Parcel in) {
            return new Note(in);
        }

        @Override
        public Note[] newArray(int size) {
            return new Note[size];
        }
    };
}

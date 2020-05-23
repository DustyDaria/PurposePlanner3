package com.example.purposeplanner3.screens.main.Note;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.purposeplanner3.App;
import com.example.purposeplanner3.model.Note;

import java.util.List;

public class MainViewModel extends ViewModel {
    //Предоставление доступа к данным о списке наших заметок
    private LiveData<List<Note>> noteLiveData = App.getInstance()
            .getNoteDao().getAllLiveData();

    public LiveData<List<Note>> getNoteLiveData(){
        return noteLiveData;
    }
}

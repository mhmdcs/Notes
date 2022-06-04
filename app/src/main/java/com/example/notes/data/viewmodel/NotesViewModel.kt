package com.example.notes.data.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.notes.data.NotesDatabase
import com.example.notes.data.models.NotesData
import com.example.notes.data.repository.NotesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NotesViewModel(application: Application): AndroidViewModel(application) {

    private val notesDao = NotesDatabase.getDatabase(application).NotesDao()

    private val repository: NotesRepository

     val getAllData: LiveData<List<NotesData>>

    init {
        repository = NotesRepository(notesDao)

        getAllData = repository.getAllData
    }

    fun insertData(notesData: NotesData){
        viewModelScope.launch(Dispatchers.IO){
            repository.insertData(notesData)
        }
    }

}
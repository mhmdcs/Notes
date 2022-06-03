package com.example.notes.data.repository

import androidx.lifecycle.LiveData
import com.example.notes.data.NotesDao
import com.example.notes.data.models.NotesData

class NotesRepository(private val notesDao: NotesDao) {

    val getAllData: LiveData<List<NotesData>> = notesDao.getAllData()

    suspend fun insertData(notesData: NotesData){
        notesDao.insertData(notesData)
    }

}
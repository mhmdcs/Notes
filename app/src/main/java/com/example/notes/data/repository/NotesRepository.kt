package com.example.notes.data.repository

import androidx.lifecycle.LiveData
import com.example.notes.data.NotesDao
import com.example.notes.data.models.NotesData

class NotesRepository(private val notesDao: NotesDao) {

    val getAllData: LiveData<List<NotesData>> = notesDao.getAllData()

    val sortByHighestPriority = notesDao.sortByHighestPriority()
    val sortByLowestPriority = notesDao.sortByLowestPriority()


    suspend fun insertData(notesData: NotesData){
        notesDao.insertData(notesData)
    }

    suspend fun updateData(notesData: NotesData){
        notesDao.updateData(notesData)
    }

    suspend fun deleteData(dataId: Int){
        notesDao.deleteData(dataId)
    }

    suspend fun deleteAllData(){
        notesDao.deleteAllData()
    }

     fun searchDatabase(query: String): LiveData<List<NotesData>>{
        return notesDao.searchDatabase(query)
    }

}
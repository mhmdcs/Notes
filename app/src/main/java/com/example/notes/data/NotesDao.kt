package com.example.notes.data

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.notes.data.models.NotesData

@Dao
interface NotesDao {

    @Query("SELECT * FROM notes_table ORDER BY id ASC")
    fun getAllData(): LiveData<List<NotesData>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertData(notesData: NotesData)

    @Update
    suspend fun updateData(notesData: NotesData)
}
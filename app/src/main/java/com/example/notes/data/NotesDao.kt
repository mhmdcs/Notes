package com.example.notes.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.notes.data.models.NotesData

@Dao
interface NotesDao {

    @Query("SELECT * FROM notes_table ORDER BY id ASC")
    fun getAllData(): LiveData<List<NotesData>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertData(notesData: NotesData)
}
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

    //or you could use the predefined @Delete query to delete a single item, and just pass a NotesData object instead of in id.
    @Query("DELETE FROM notes_table WHERE id = :dataId")
    suspend fun deleteData(dataId: Int)

    @Query("DELETE FROM notes_table")
    suspend fun deleteAllData()
}
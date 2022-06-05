package com.example.notes.data

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.notes.data.models.NotesData

@Dao
interface NotesDao {

    @Query("SELECT * FROM notes_table ORDER BY id ASC")
    fun getAllData(): LiveData<List<NotesData>> //Room executes queries that return LiveData on a background thread automatically. Room Database ensure that the query returning observable is run on background thread, which is why later won't need to suspend such functions or need to call it from a coroutine

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertData(notesData: NotesData)

    @Update
    suspend fun updateData(notesData: NotesData)

    //or you could use the predefined @Delete query to delete a single item, and just pass a NotesData object instead of in id.
    @Query("DELETE FROM notes_table WHERE id = :dataId")
    suspend fun deleteData(dataId: Int)

    @Query("DELETE FROM notes_table")
    suspend fun deleteAllData()

    @Query("SELECT * FROM notes_table WHERE title LIKE :query")
     fun searchDatabase(query: String): LiveData<List<NotesData>> //Room executes queries that return LiveData on a background thread automatically. Room Database ensure that the query returning observable is run on background thread, which is why later won't need to suspend such functions or need to call it from a coroutine

     @Query("SELECT * FROM notes_table ORDER BY CASE WHEN priority LIKE 'H%' THEN 1 WHEN priority LIKE 'M%' THEN 2 WHEN priority LIKE 'L%' THEN  3 END")
     fun sortByHighestPriority(): LiveData<List<NotesData>>

    @Query("SELECT * FROM notes_table ORDER BY CASE WHEN priority LIKE 'L%' THEN 1 WHEN priority LIKE 'M%' THEN 2 WHEN priority LIKE 'H%' THEN  3 END")
    fun sortByLowestPriority(): LiveData<List<NotesData>>
}
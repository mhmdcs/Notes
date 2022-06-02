package com.example.notes.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database (entities = [NotesData::class], version = 1, exportSchema = false)
@TypeConverters(Converter::class)
abstract class NotesDatabase: RoomDatabase()
{

    abstract fun NotesDao(): NotesDao

    companion object {
        @Volatile
        private var INSTANCE: NotesDatabase? = null

        fun getDatabase(context: Context): NotesDatabase{
            synchronized(this){
            var instance = INSTANCE

                if(instance != null ){
                    return instance //if the instance is not null, the getDatabase() function will finish here and won't enter the next lines
                }

                instance = Room.databaseBuilder(
                    context.applicationContext,
                    NotesDatabase::class.java,
                    "notes_database"
                ).build()

                INSTANCE = instance
                return instance

            }
        }

    }

}
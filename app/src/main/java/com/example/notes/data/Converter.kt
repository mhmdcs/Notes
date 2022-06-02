package com.example.notes.data

import androidx.room.TypeConverter

//Since Room can only accept primitive types, we need to create helper converter methods to convert
//the values of our Priority class which is a column in our notes_table, we can do this by writing converter methods while annotating them
//with @TypeConverter and then annotating our Database class with @Converters while supplying it with our Converter class

class Converter {

    @TypeConverter
    fun fromPriorityToString(priority: Priority): String{
        return priority.name
    }

    @TypeConverter
    fun fromStringToPriority(priority: String): Priority{
        return Priority.valueOf(priority)
    }

}
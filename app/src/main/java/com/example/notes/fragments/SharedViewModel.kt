package com.example.notes.fragments

import android.app.Application
import android.text.TextUtils
import android.view.View
import android.widget.AdapterView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.notes.R
import com.example.notes.data.models.NotesData
import com.example.notes.data.models.Priority

class SharedViewModel(application: Application): AndroidViewModel(application) {

    val emptyDatabase: MutableLiveData<Boolean> = MutableLiveData(false)

    fun checkIfDatabaseEmpty(dataList: List<NotesData>){
        emptyDatabase.value = dataList.isEmpty() //isEmpty() will return true when the list is empty, and false when it isn't
    }

    val listener: AdapterView.OnItemSelectedListener = object : AdapterView.OnItemSelectedListener{
        override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
            when(position){
                0 -> (parent?.getChildAt(0) as TextView).setTextColor(ContextCompat.getColor(application, R.color.red))
                1 -> (parent?.getChildAt(0) as TextView).setTextColor(ContextCompat.getColor(application, R.color.yellow))
                2 -> (parent?.getChildAt(0) as TextView).setTextColor(ContextCompat.getColor(application, R.color.green))
            }
        }

        override fun onNothingSelected(parent: AdapterView<*>?) {}

    }

     fun verifyDataIsNotEmpty(title: String, content: String): Boolean{
        return if(TextUtils.isEmpty(title) || TextUtils.isEmpty(content)){ //if title and content are empty, return false
            false
        } else !(title.isEmpty() || content.isEmpty()) //if title and content are NOT empty, return true
    }

     fun parseStringToPriority(priority: String): Priority {
        return when(priority){
            "High Priority" -> {
                Priority.HIGH}
            "Medium Priority" -> {
                Priority.MEDIUM}
            "Low Priority" -> {
                Priority.LOW}
            else -> Priority.HIGH
        }
    }

}
package com.example.notes.fragments.list.adapter

import androidx.recyclerview.widget.DiffUtil
import com.example.notes.data.models.NotesData

class NotesDiffUtil(private val oldList: List<NotesData>, private val newList: List<NotesData>): DiffUtil.Callback() {
    override fun getOldListSize(): Int {
       return oldList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].id == newList[newItemPosition].id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] == newList[newItemPosition]
    }
}
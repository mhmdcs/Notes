package com.example.notes.fragments.list.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.notes.data.models.NotesData
import com.example.notes.databinding.ItemLayoutBinding

class ListAdapter: RecyclerView.Adapter<CustomViewHolder>() {

     var dataList = emptyList<NotesData>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        return CustomViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        val notesData = dataList[position]
        holder.bind(notesData)
    }

    override fun getItemCount(): Int {
        return dataList.count()
    }

    fun setData(notesDataList: List<NotesData>){
        this.dataList = notesDataList
        notifyDataSetChanged()
    }

}

class CustomViewHolder(private val binding: ItemLayoutBinding): RecyclerView.ViewHolder(binding.root) {

    fun bind(notesData: NotesData){
        binding.notesData = notesData
        binding.executePendingBindings()
    }

    companion object {
        fun from(parent: ViewGroup): CustomViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = ItemLayoutBinding.inflate(layoutInflater, parent, false)
            return CustomViewHolder(binding)
        }
    }

}

package com.example.notes.fragments.list

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.notes.R
import com.example.notes.data.models.NotesData
import com.example.notes.data.models.Priority
import com.example.notes.databinding.ItemLayoutBinding

class ListAdapter: RecyclerView.Adapter<CustomViewHolder>() {

    private var dataList = emptyList<NotesData>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemLayoutBinding.inflate(layoutInflater, parent, false)
        return CustomViewHolder(binding)
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
        binding.titleTxt.text = notesData.title
        binding.contentTxt.text = notesData.content

        val priority = notesData.priority
        when(priority){
            Priority.HIGH -> binding.priorityIndicator.setCardBackgroundColor(ContextCompat.getColor(binding.root.context, R.color.red))
            Priority.MEDIUM -> binding.priorityIndicator.setCardBackgroundColor(ContextCompat.getColor(binding.root.context, R.color.yellow))
            Priority.LOW -> binding.priorityIndicator.setCardBackgroundColor(ContextCompat.getColor(binding.root.context, R.color.green))

        }

    }
}

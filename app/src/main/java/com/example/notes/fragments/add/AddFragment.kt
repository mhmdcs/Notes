package com.example.notes.fragments.add

import android.os.Bundle
import android.text.TextUtils
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.notes.R
import com.example.notes.data.models.NotesData
import com.example.notes.data.models.Priority
import com.example.notes.data.viewmodel.NotesViewModel
import com.example.notes.databinding.FragmentAddBinding
import com.example.notes.databinding.FragmentListBinding


class AddFragment : Fragment() {

    private lateinit var binding: FragmentAddBinding

    private val viewModel: NotesViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentAddBinding.inflate(inflater, container, false)

        setHasOptionsMenu(true)

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add, container, false)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.fragment_add_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.add_menu){
            insertDataToDb()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun insertDataToDb() {

        val mTitle = binding.addNotesTitleEditText.toString()
        val mPriority = binding.addSpinner.toString()
        val mContent = binding.addNotesContentEditText.toString()

        val validate = verifyDataIsNotEmpty(mTitle, mContent)
        if(validate){
            val newData = NotesData(
                0,
                mTitle,
                parsePriority(mPriority),
                mContent
            )
            viewModel.insertData(newData)
            Toast.makeText(context, "Note added", Toast.LENGTH_SHORT).show()
            //navigate back
            findNavController().navigate(R.id.action_addFragment_to_listFragment)
        } else Toast.makeText(context, "Please fill out all fields", Toast.LENGTH_SHORT).show()

    }

    private fun verifyDataIsNotEmpty(title: String, content: String): Boolean{
       return if(TextUtils.isEmpty(title) || TextUtils.isEmpty(content)){
             false //if title and content are empty, return false
        } else !(title.isEmpty() || content.isEmpty()) //else if title and content are NOT empty, return true
    }

    private fun parsePriority(priority: String): Priority{
        return when(priority){
            "Priority High" -> Priority.HIGH
            "Priority Medium" -> Priority.MEDIUM
            "Priority Low" -> Priority.LOW
            else -> Priority.LOW
        }
    }

}
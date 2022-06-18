package com.example.notes.fragments.add

import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.notes.R
import com.example.notes.data.models.NotesData
import com.example.notes.data.models.Priority
import com.example.notes.data.viewmodel.NotesViewModel
import com.example.notes.databinding.FragmentAddBinding
import com.example.notes.fragments.SharedViewModel


class AddFragment : Fragment() {

    private var _binding: FragmentAddBinding? = null
    private val binding get() = _binding!!

    private val notesViewModel: NotesViewModel by viewModels()
    private val sharedViewModel: SharedViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_add, container, false)

        binding.addSpinner.onItemSelectedListener = sharedViewModel.listener

        setHasOptionsMenu(true)

        // Inflate the layout for this fragment
        return binding.root
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

        val mTitle = binding.addNotesTitleEditText.text.toString()
        val mPriority = binding.addSpinner.selectedItem.toString()
        val mContent = binding.addNotesContentEditText.text.toString()

        val validate = sharedViewModel.verifyDataIsNotEmpty(mTitle, mContent)
        Log.i("AddFragment", "Validate value is $validate")
        if(validate){
            val newData = NotesData(
                0,
                mTitle,
                sharedViewModel.parseStringToPriority(mPriority),
                mContent
            )
            notesViewModel.insertData(newData)
            Toast.makeText(context, "Note added", Toast.LENGTH_SHORT).show()
            //navigate back
            findNavController().navigate(R.id.action_addFragment_to_listFragment)
        } else Toast.makeText(context, "Please fill out all fields", Toast.LENGTH_SHORT).show()
    }


    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}
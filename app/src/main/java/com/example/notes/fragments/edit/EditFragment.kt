package com.example.notes.fragments.edit

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.example.notes.R
import com.example.notes.data.models.Priority
import com.example.notes.databinding.FragmentEditBinding
import com.example.notes.fragments.SharedViewModel


class EditFragment : Fragment() {

    private lateinit var binding: FragmentEditBinding
    private val args by navArgs<EditFragmentArgs>()
    private val sharedViewModel: SharedViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentEditBinding.inflate(inflater, container, false)

        binding.editNotesTitleEditText.setText(args.currentNote.title)
        binding.editNotesContentEditText.setText(args.currentNote.content)
        binding.editSpinner.setSelection(parsePriority(args.currentNote.priority))
        binding.editSpinner.onItemSelectedListener = sharedViewModel.listener

        setHasOptionsMenu(true)
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.fragment_edit_menu, menu)
    }

    private fun parsePriority(priority: Priority): Int{
        return when(priority){
            Priority.HIGH -> 0
            Priority.MEDIUM -> 1
            Priority.LOW -> 2
        }
    }
}
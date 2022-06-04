package com.example.notes.fragments.edit

import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.notes.R
import com.example.notes.data.models.NotesData
import com.example.notes.data.models.Priority
import com.example.notes.data.viewmodel.NotesViewModel
import com.example.notes.databinding.FragmentEditBinding
import com.example.notes.fragments.SharedViewModel


class EditFragment : Fragment() {

    private lateinit var binding: FragmentEditBinding
    private val args by navArgs<EditFragmentArgs>()
    private val sharedViewModel: SharedViewModel by viewModels()
    private val notesViewModel: NotesViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentEditBinding.inflate(inflater, container, false)

        binding.args = args

        binding.editSpinner.onItemSelectedListener = sharedViewModel.listener

        setHasOptionsMenu(true)
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.fragment_edit_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.save_menu){
            updateData()
        } else if (item.itemId == R.id.delete_menu){
            confirmOptionAndDelete()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun deleteData() {
        notesViewModel.deleteData(args.currentNote.id)
        Toast.makeText(context, "Note Deleted", Toast.LENGTH_SHORT).show()
        findNavController().navigate(EditFragmentDirections.actionEditFragmentToListFragment())
    }

    private fun confirmOptionAndDelete(){
        val builder = AlertDialog.Builder(context)

        builder.setPositiveButton("Yes") { _, _ ->
            deleteData()
        }
        builder.setNegativeButton("No") { _, _ -> }
        builder.setTitle("Delete '${args.currentNote.title}?'")
        builder.setMessage("Are you sure you want to remove '${args.currentNote.title}'?")
        builder.create().show()
    }

    private fun updateData() {
        val mTitle = binding.editNotesTitleEditText.text.toString()
        val mContent = binding.editNotesContentEditText.text.toString()
        val mPriority = binding.editSpinner.selectedItem.toString()

        val validate = sharedViewModel.verifyDataIsNotEmpty(mTitle, mContent)

        if(validate){
        val updatedNote = NotesData(
            args.currentNote.id,
            mTitle,
            sharedViewModel.parseStringToPriority(mPriority),
            mContent
        )
        notesViewModel.updateData(updatedNote)
        Toast.makeText(context, "Changes saved!", Toast.LENGTH_SHORT).show()
        findNavController().navigate(R.id.action_editFragment_to_listFragment)
        } else {
            Toast.makeText(context, "Please fill out all fields", Toast.LENGTH_SHORT).show()
        }
    }
}
package com.example.notes.fragments.list

import android.app.AlertDialog
import android.os.Bundle
import android.transition.Visibility
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.notes.R
import com.example.notes.data.viewmodel.NotesViewModel
import com.example.notes.databinding.FragmentListBinding
import com.example.notes.fragments.SharedViewModel

class ListFragment : Fragment() {

    private lateinit var binding: FragmentListBinding

    private val listAdapter: ListAdapter by lazy { ListAdapter() }

    private val notesViewModel: NotesViewModel by viewModels()

    private val sharedViewModel: SharedViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentListBinding.inflate(inflater, container, false)

        binding.myRecyclerView.adapter = listAdapter
        binding.myRecyclerView.layoutManager = LinearLayoutManager(context)

        binding.lifecycleOwner = this
        binding.sharedViewModel = sharedViewModel

        notesViewModel.getAllData.observe(viewLifecycleOwner, Observer { notesData ->
            sharedViewModel.checkIfDatabaseEmpty(notesData)
            listAdapter.setData(notesData)
        })

        //we now observe if the database is empty in the layout via BindingAdapters
//        sharedViewModel.emptyDatabase.observe(viewLifecycleOwner, Observer {
//            displayNoDataImageViewAndTextView(it)
//        })



        //set the setHasOptionsMenu method to true to display menu
        setHasOptionsMenu(true)

        return binding.root
    }

    //we now observe if the database is empty in the layout via BindingAdapters
//    private fun displayNoDataImageViewAndTextView(emptyDatabase: Boolean){
//        if(emptyDatabase){
//            binding.noDataImageView.visibility = View.VISIBLE
//            binding.noDataTextView.visibility = View.VISIBLE
//        } else {
//            binding.noDataImageView.visibility = View.INVISIBLE
//            binding.noDataTextView.visibility = View.INVISIBLE
//        }
//    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        //inflate the menu xml layout to render in the app
        inflater.inflate(R.menu.fragment_list_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.clearAll_menu){
            confirmOptionAndDeleteAllData()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun confirmOptionAndDeleteAllData() {
        val builder = AlertDialog.Builder(context)
        builder.setPositiveButton("Yes") { _,_ ->
            deleteAllData()
        }
        builder.setNegativeButton("No") { _,_ ->}
        builder.setTitle("Are you sure you want to clear all notes?")
        builder.setMessage("This will permanently delete all of your notes.")
        builder.show()
    }

    private fun deleteAllData() {
        notesViewModel.deleteAllData()
        Toast.makeText(context, "All Notes Cleared!", Toast.LENGTH_SHORT).show()
    }
}
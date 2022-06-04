package com.example.notes.fragments.list

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.notes.R
import com.example.notes.data.viewmodel.NotesViewModel
import com.example.notes.databinding.FragmentListBinding

class ListFragment : Fragment() {

    private lateinit var binding: FragmentListBinding

    private val listAdapter: ListAdapter by lazy { ListAdapter() }

    private val notesViewModel: NotesViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentListBinding.inflate(inflater, container, false)

        binding.myRecyclerView.adapter = listAdapter
        binding.myRecyclerView.layoutManager = LinearLayoutManager(context)

        notesViewModel.getAllData.observe(viewLifecycleOwner, Observer { notesData ->
            listAdapter.setData(notesData)
        })

        binding.floatingActionButton.setOnClickListener{
            findNavController().navigate(R.id.action_listFragment_to_addFragment)
        }

        //set the setHasOptionsMenu method to true to display menu
        setHasOptionsMenu(true)

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        //inflate the menu xml layout to render in the app
        inflater.inflate(R.menu.fragment_list_menu, menu)
    }
}
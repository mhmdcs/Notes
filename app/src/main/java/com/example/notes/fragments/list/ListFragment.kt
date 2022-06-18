package com.example.notes.fragments.list

import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.*
import com.example.notes.R
import com.example.notes.data.models.NotesData
import com.example.notes.data.viewmodel.NotesViewModel
import com.example.notes.databinding.FragmentListBinding
import com.example.notes.fragments.SharedViewModel
import com.example.notes.fragments.list.adapter.ListAdapter
import com.example.notes.utiils.observeOnce
import com.google.android.material.snackbar.Snackbar

                                //make sure to  import the androidx.appcompat version of SearchView else it won'r work
class ListFragment : Fragment(), SearchView.OnQueryTextListener {

    private var _binding: FragmentListBinding? = null
    private val binding get() = _binding!!

    private val listAdapter: ListAdapter by lazy { ListAdapter() }

    private val notesViewModel: NotesViewModel by viewModels()

    private val sharedViewModel: SharedViewModel by viewModels()

    private lateinit var recyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentListBinding.inflate(inflater, container, false)

        binding.myRecyclerView.adapter = listAdapter
        binding.myRecyclerView.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)

        recyclerView = binding.myRecyclerView
        swipeToDelete(recyclerView)

        binding.lifecycleOwner = this
        binding.sharedViewModel = sharedViewModel


        notesViewModel.getAllData.observe(viewLifecycleOwner, Observer { notesData ->
            sharedViewModel.checkIfDatabaseEmpty(notesData)
            listAdapter.setData(notesData)
            binding.myRecyclerView.scheduleLayoutAnimation()
        })

        //we now observe if the database is empty in the layout via BindingAdapters, so this is commented out.
//        sharedViewModel.emptyDatabase.observe(viewLifecycleOwner, Observer {
//            displayNoDataImageViewAndTextView(it)
//        })



        //set the setHasOptionsMenu method to true to display menu
        setHasOptionsMenu(true)

        return binding.root
    }

    private fun swipeToDelete(recyclerView: RecyclerView) {

        val swipeToDeleteCallback = object: ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT){
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                //delete item
                val itemDeleted = listAdapter.dataList[viewHolder.adapterPosition]
                notesViewModel.deleteData(itemDeleted.id)

                listAdapter.notifyItemRemoved(viewHolder.adapterPosition)

                //restore deleted item
                restoreDeletedItem(viewHolder.itemView, itemDeleted, viewHolder.adapterPosition)
            }

        }

        val itemTouchHelper = ItemTouchHelper(swipeToDeleteCallback)
        itemTouchHelper.attachToRecyclerView(recyclerView)

    }

    private fun restoreDeletedItem(view: View, itemDeleted: NotesData, position: Int) {

        val snackbar = Snackbar.make(view, "Removed '${itemDeleted.title}'", Snackbar.LENGTH_LONG )
        snackbar.setAction("Undo"){
            notesViewModel.insertData(itemDeleted)
            if (position != -1 ){
                listAdapter.notifyItemChanged(position)
            }
        }
        snackbar.show()
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

        val searchMenuItem = menu.findItem(R.id.search_menu)
        val searchView = searchMenuItem.actionView as? SearchView
        searchView?.isSubmitButtonEnabled = true
        searchView?.setOnQueryTextListener(this) //we set it as `this` because we have implemented the SearchView.OnQueryTextListener interface inside our class
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){

                R.id.clearAll_menu -> confirmOptionAndDeleteAllData()

                R.id.highest_sort -> notesViewModel.sortByHighestPriority.observe(this, Observer{
                    listAdapter.setData(it)
                })

                R.id.lowest_sort -> notesViewModel.sortByLowestPriority.observe(this, Observer{
                listAdapter.setData(it)
                })

                R.id.oldest_sort -> notesViewModel.getAllData.observe(this, Observer{
                    listAdapter.setData(it)
                })
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


    override fun onQueryTextSubmit(query: String?): Boolean {
        if(query != null){
            searchThroughDatabase(query)
        }
        return true
    }


    override fun onQueryTextChange(query: String?): Boolean {
        if(query != null){
            searchThroughDatabase(query)
        }
        return true
    }


    private fun searchThroughDatabase(query: String) {
        val searchQuery = "%$query%"
        notesViewModel.searchDatabase(searchQuery).observeOnce(viewLifecycleOwner, Observer{
            listAdapter.setData(it)
        })
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }


}
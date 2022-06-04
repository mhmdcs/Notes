package com.example.notes

import android.os.Build
import android.view.View
import android.widget.ImageView
import android.widget.Spinner
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import androidx.lifecycle.MutableLiveData
import androidx.navigation.findNavController
import com.example.notes.data.models.NotesData
import com.example.notes.data.models.Priority
import com.example.notes.fragments.list.ListFragmentDirections
import com.google.android.material.floatingactionbutton.FloatingActionButton

class BindingAdapters {

    companion object {

        @BindingAdapter("android:navigateToAddFragment")
        @JvmStatic
        fun navigateToAddFragment(fab: FloatingActionButton, navigate: Boolean){
            fab.setOnClickListener {
                if(navigate){
                    fab.findNavController().navigate(R.id.action_listFragment_to_addFragment)
                }
            }
        }

        @JvmStatic
        @BindingAdapter("android:displayNoDataViews")
        fun displayNoDataViews(view: View, emptyDatabase: MutableLiveData<Boolean>){
            when(emptyDatabase.value){
                true -> view.visibility = View.VISIBLE
                false -> view.visibility = View.INVISIBLE
            }
        }

        @JvmStatic
        @BindingAdapter("android:parsePriorityToInt")
        fun parsePriorityToInt(spinner: Spinner, priority: Priority){
            when(priority){
                Priority.HIGH -> spinner.setSelection(0)
                Priority.MEDIUM -> spinner.setSelection(1)
                Priority.LOW -> spinner.setSelection(2)
            }
        }

        @RequiresApi(Build.VERSION_CODES.M)
        @JvmStatic
        @BindingAdapter("android:parsePriorityColor")
        fun parsePriorityColor(cardView: CardView, priority: Priority){
            when(priority){
                Priority.HIGH -> cardView.setCardBackgroundColor(ContextCompat.getColor(cardView.context, R.color.red)) //another way to get colors from system resource's that doesn't require min API 23
                Priority.MEDIUM -> cardView.setCardBackgroundColor(cardView.context.getColor(R.color.yellow)) //requires min API 23
                Priority.LOW -> cardView.setCardBackgroundColor(cardView.context.getColor(R.color.green)) //requires min API 23
            }
        }

        @BindingAdapter("android:navigateToEditFragment")
        @JvmStatic
        fun navigateToEditFragment(constraintLayout: ConstraintLayout, notesData: NotesData){
            constraintLayout.setOnClickListener {
                it.findNavController().navigate(ListFragmentDirections.actionListFragmentToEditFragment(notesData))
            }
        }

    }

}
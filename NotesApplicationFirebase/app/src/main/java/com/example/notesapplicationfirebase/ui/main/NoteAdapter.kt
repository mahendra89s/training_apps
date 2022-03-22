package com.example.notesapplicationfirebase.ui.main

import android.util.Log
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.view.ActionMode
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.notesapplicationfirebase.R
import com.example.notesapplicationfirebase.data.model.Note
import com.example.notesapplicationfirebase.databinding.NotesLayoutBinding
import java.io.File


class NoteAdapter(
    var notes : MutableList<Note>,
    val listener: Listener,
    val activity : AppCompatActivity
) : RecyclerView.Adapter<NoteAdapter.ViewHolder>(),androidx.appcompat.view.ActionMode.Callback {
    private var multiSelect : Boolean = false
    private val selectedItems = mutableListOf<Note>()

    inner class ViewHolder(val binding: NotesLayoutBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.notes_layout,parent,false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.note = notes[position]
//        Log.d("note",notes[position].toString())
        val currentSelected = notes[position]
        holder.binding.noteLayout.setOnClickListener {
            if(multiSelect){
                selectItem(holder,currentSelected)
            }
            else{
                multiSelect = false
                listener.onClick(notes[position])
            }

        }


        if(selectedItems.contains(currentSelected)){
            holder.binding.noteLayout.background = ContextCompat.getDrawable(activity,R.drawable.selected_note_drawable)
        }
        else{
            holder.binding.noteLayout.background = ContextCompat.getDrawable(activity,R.drawable.notes_drawable)
        }

        holder.binding.noteLayout.setOnLongClickListener {
            if(!multiSelect){
                multiSelect = true
                activity.startSupportActionMode(this@NoteAdapter)
                selectItem(holder,notes[holder.adapterPosition])
                true
            }
            else{
                false
            }
        }
    }
    fun selectItem(holder: ViewHolder,note: Note){
        if(selectedItems.contains(note)){
            selectedItems.remove(note)
            holder.binding.noteLayout.background = ContextCompat.getDrawable(activity,R.drawable.notes_drawable)
        }
        else{
            selectedItems.add(note)
            notifyDataSetChanged()
            holder.binding.noteLayout.background = ContextCompat.getDrawable(activity,R.drawable.selected_note_drawable)
        }
    }
    fun updateFilter(note : MutableList<Note>){
        notes = note
        notifyDataSetChanged()
    }
    override fun getItemCount(): Int {
        return notes.size
    }

    override fun onCreateActionMode(mode: ActionMode?, menu: Menu?): Boolean {
        val inflater : MenuInflater = mode?.menuInflater!!
        inflater.inflate(R.menu.action_menu,menu)
        return true
    }

    override fun onPrepareActionMode(mode: ActionMode?, menu: Menu?): Boolean {
        return false
    }

    override fun onActionItemClicked(mode: ActionMode?, item: MenuItem?): Boolean {
        if (item?.itemId == R.id.btnDelete){
            listener.onLongClick(selectedItems)
            mode?.finish()
        }
        return true
    }

    override fun onDestroyActionMode(mode: ActionMode?) {
        multiSelect = false
        selectedItems.clear()
        notifyDataSetChanged()
    }


}
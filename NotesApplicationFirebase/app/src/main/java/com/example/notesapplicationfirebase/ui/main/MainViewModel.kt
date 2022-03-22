package com.example.notesapplicationfirebase.ui.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.notesapplicationfirebase.data.model.Note
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class MainViewModel : ViewModel() {
    val databaseReference = FirebaseDatabase.getInstance().reference
    val notesMutableList = mutableListOf<Note>()
    var notesMutableLiveData  = MutableLiveData<MutableList<Note>>()

    val _notes : LiveData<MutableList<Note>>
        get() = notesMutableLiveData
    init {
        getNotesFromFirebase()
    }
    fun getNotesFromFirebase(){
        val postListener = object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                notesMutableList.clear()
                notesMutableLiveData.value = null
                for(i in snapshot.child("notes").children){
                    val id = i.child("id").value
                    val title = i.child("title").value
                    val description = i.child("description").value
                    notesMutableList.add(Note(id.toString(),title.toString(),description.toString()))
                }

                Log.d("list",notesMutableList.toString())
                notesMutableLiveData.value = notesMutableList
                Log.d("mutableLivedata",notesMutableLiveData.value.toString())
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        }
        databaseReference.addValueEventListener(postListener)
    }
    fun getNotes() : LiveData<MutableList<Note>> = _notes
}
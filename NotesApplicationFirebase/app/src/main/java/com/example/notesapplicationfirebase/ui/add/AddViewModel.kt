package com.example.notesapplicationfirebase.ui.add

import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.notesapplicationfirebase.data.model.Note
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class AddViewModel(
    val note: Note?
) : ViewModel() {
    var title : String? = null
    var desc : String? = null
    val shouldCloseLiveData = MutableLiveData<Void>()
    val databaseReference : DatabaseReference = FirebaseDatabase.getInstance().reference
    init {
        if(note != null){
            title = note.title
            desc = note.description
        }
    }
    fun addNotes(view : View){
//        Log.d("user","$title $desc")
        if(title.isNullOrEmpty() or desc.isNullOrEmpty()){
            return
        }
        if(note?.id==""){
            databaseReference.child("notes").push().also {
                val id = it.key
                it.setValue(Note(id.toString(),title.toString(),desc.toString()))
            }
            shouldCloseLiveData.postValue(null)

        }
        else{
            databaseReference.child("notes").child(note?.id!!).also {
                it.setValue(Note(note.id,title.toString(),desc.toString()))
            }
            shouldCloseLiveData.postValue(null)
        }


    }
}
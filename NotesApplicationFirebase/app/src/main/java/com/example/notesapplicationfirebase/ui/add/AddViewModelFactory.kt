package com.example.notesapplicationfirebase.ui.add

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.notesapplicationfirebase.data.model.Note

class AddViewModelFactory(
    val note : Note?
) : ViewModelProvider.NewInstanceFactory(){
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return AddViewModel(note) as T
    }
}
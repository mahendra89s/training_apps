package com.example.notesapplicationfirebase.ui.main

import com.example.notesapplicationfirebase.data.model.Note

interface Listener {
    fun onClick(note : Note)
    fun onLongClick(notes : List<Note>)
}
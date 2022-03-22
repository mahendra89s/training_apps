package com.example.notesapplicationfirebase.ui.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.core.widget.addTextChangedListener
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.notesapplicationfirebase.R
import com.example.notesapplicationfirebase.data.model.Note
import com.example.notesapplicationfirebase.databinding.ActivityMainBinding
import com.example.notesapplicationfirebase.ui.add.AddActivity
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), Listener {
    lateinit var notes : MutableList<Note>
    lateinit var adapter: NoteAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding : ActivityMainBinding = DataBindingUtil.setContentView(this,R.layout.activity_main)
        val viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        binding.viewModel = viewModel

//        viewModel.getNotesFromFirebase()
        viewModel.getNotes().observe(this,{notesData ->
//            Log.d("notes",notes.toString())
            rv_notes.also {

                it.layoutManager = StaggeredGridLayoutManager(2,LinearLayoutManager.VERTICAL)
                if(notesData!=null){
                    notes = notesData
                    adapter = NoteAdapter(notes,this,this)
                    it.adapter = adapter
                }
            }
        })

        btnAdd.setOnClickListener {
            Intent(this,AddActivity::class.java).also {
                startActivity(it)
            }
        }
        edtSearch.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun afterTextChanged(s: Editable?) {
                filter(s.toString())
            }

        })
    }
    fun filter(s : String){
        val temp = mutableListOf<Note>()
        for(i in notes){
            if(i.title?.contains(s)!! or i.description?.contains(s)!!){
                temp.add(i)
            }
        }
        adapter.updateFilter(temp)
    }

    override fun onClick(note: Note){
        Intent(this, AddActivity::class.java).also {
            it.putExtra("note",note)
            startActivity(it)
        }
    }

    override fun onLongClick(notes: List<Note>) {
        val database = FirebaseDatabase.getInstance().reference
        for(i in notes){
            database.child("notes").child(i.id!!).removeValue()
        }
    }
}
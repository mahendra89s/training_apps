package com.example.harrypotter.ui.main

import android.content.Context
import android.content.Intent
import android.view.View
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.ViewModel
import com.example.harrypotter.ui.students.StudentListActivity

class MainViewModel : ViewModel() {
    fun onGryffindorClick(view : View){
        val intent = Intent(view.context, StudentListActivity::class.java)
        intent.putExtra("house","gryffindor")
        view.context.startActivity(intent)
    }
    fun onHufflepuffClick(view : View){
        val intent = Intent(view.context, StudentListActivity::class.java)
        intent.putExtra("house","hufflepuff")
        view.context.startActivity(intent)
    }
    fun onRavenclawClick(view : View){
        val intent = Intent(view.context, StudentListActivity::class.java)
        intent.putExtra("house","ravenclaw")
        view.context.startActivity(intent)
    }
    fun onSlytherinClick(view : View){
        val intent = Intent(view.context, StudentListActivity::class.java)
        intent.putExtra("house","slytherin")
        view.context.startActivity(intent)
    }
}
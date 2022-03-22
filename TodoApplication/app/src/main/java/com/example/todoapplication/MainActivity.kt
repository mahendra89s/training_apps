package com.example.todoapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager

import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var data = mutableListOf<Todo>()
        val adapter = TodoAdapter(this,data,this@MainActivity)
        rvTask.adapter = adapter
        rvTask.layoutManager = LinearLayoutManager(this)

        btnAddTask.setOnClickListener {
            if(edtTask.text.toString().isNotEmpty()){
                data.add(Todo(edtTask.text.toString()))
                adapter.notifyItemInserted(data.size-1)
                edtTask.text.clear()
            }
        }
    }
}
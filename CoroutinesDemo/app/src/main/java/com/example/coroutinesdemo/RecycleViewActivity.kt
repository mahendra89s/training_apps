package com.example.coroutinesdemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_recycle_view.*

class RecycleViewActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recycle_view)

        var todoList = mutableListOf(
            Todo("HEllO",false),
            Todo("HEllO1",false),
            Todo("HEllO2",true),
            Todo("HEllO3",false),
            Todo("HEllO4",true)
        )
        val adapter = TodoAdapter(todoList)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        btnAdd.setOnClickListener {
            val title = editAdd.text.toString()
            todoList.add(Todo(title,false))
            editAdd.text.clear()
            adapter.notifyItemInserted(todoList.size-1)
        }
    }
}
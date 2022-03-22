package com.example.todoapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.todoapplication.database.AppDatabase
import com.example.todoapplication.database.TaskEntry
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(),ItemClickListener {
    lateinit var decoration: DividerItemDecoration
    lateinit var appDatabase: AppDatabase
    lateinit var taskEntries : MutableList<TaskEntry>
    lateinit var adapter: TaskAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        appDatabase = AppDatabase.invoke(applicationContext)


        adapter = TaskAdapter(this,this)
        rvTasks.adapter = adapter
        rvTasks.layoutManager = LinearLayoutManager(this)

        decoration= DividerItemDecoration(this,LinearLayoutManager.VERTICAL)
        rvTasks.addItemDecoration(decoration)
        val itemTouchHelperCallback = object : ItemTouchHelper.SimpleCallback(
            0,
            ItemTouchHelper.RIGHT or ItemTouchHelper.LEFT){
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val taskEntries = adapter.getTasks()
                appDatabase.getTaskDao().deleteTask(taskEntries[position])
            }

        }
        val itemTouchHelper = ItemTouchHelper(itemTouchHelperCallback)
        itemTouchHelper.attachToRecyclerView(rvTasks)


        floatingButton.setOnClickListener{
            Intent(this,AddActivity::class.java).also{
                startActivity(it)
            }
        }
        retrieveTasks()
    }


    private fun retrieveTasks(){
        val tasks : LiveData<MutableList<TaskEntry>> = appDatabase.getTaskDao().getTasks()
        tasks.observe(this,
            { t -> adapter.setTasks(t!!) })

    }
    override fun onItemClicked(itemId: Int) {
        Intent(this,AddActivity::class.java).also {
            it.putExtra("extraTaskId",itemId)
            startActivity(it)
        }
    }
}
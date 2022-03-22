package com.example.todoapplication

import android.content.Context
import android.view.*
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.view.ActionMode
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.text_row_item.view.*

class TodoAdapter(
    val context: Context,
    var tasks: MutableList<Todo>,
    val activity: AppCompatActivity
) : RecyclerView.Adapter<TodoAdapter.ViewHolder>(), ActionMode.Callback{
    // true if the user in selection mode, false otherwise
    private var multiSelect = false
    // Keeps track of all the selected images
    private val selectedItems = arrayListOf<Todo>()

    inner class ViewHolder(view:View):RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.text_row_item, parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.txtTask.text = tasks[position].data

        holder.itemView.btnDeleteTask.setOnClickListener {
            tasks.removeAt(position)
            notifyItemRemoved(position)
            notifyItemRangeChanged(position,tasks.size)
        }
        // Get the current image
        val currentTask = tasks[position]
        // for every item, check to see if it exists in the selected items array
        if (selectedItems.contains(currentTask)) {
            // if the item is selected, let the user know by adding a dark layer above it
            holder.itemView.ivGrid.alpha = 0.3f
        } else {
            // else, keep it as it is
            holder.itemView.ivGrid.alpha = 1.0f
        }

        // set handler to define what happens when an item is long pressed
        holder.itemView.ivGrid.setOnLongClickListener {
            // if multiSelect is false, set it to true and select the item
            if (!multiSelect) {
                // We have started multi selection, so set the flag to true
                multiSelect = true
                // As soon as the user starts multi-select process, show the contextual menu

                activity.startSupportActionMode(this@TodoAdapter)
                selectItem(holder, tasks[holder.adapterPosition])
                true
            } else
                false
        }

        // handler to define what happens when an item is tapped
        holder.itemView.ivGrid.setOnClickListener {
            // if the user is in multi-select mode, add it to the multi select list
            if (multiSelect)
                selectItem(holder, currentTask)
            else{
                multiSelect = false
            }
            // else, simply show the image to the user
//                showPopup(currentTask.file)
        }

    }
    // helper function that adds/removes an item to the list depending on the app's state
    private fun selectItem(holder: ViewHolder, tasks: Todo) {
        // If the "selectedItems" list contains the item, remove it and set it's state to normal

            if (selectedItems.contains(tasks)) {
                selectedItems.remove(tasks)
                holder.itemView.ivGrid.alpha = 1.0f
            } else {
                // Else, add it to the list and add a darker shade over the task, letting the user know that it was selected
                selectedItems.add(tasks)
                holder.itemView.ivGrid.alpha = 0.3f
            }


    }
    override fun getItemCount(): Int {
        return tasks.size
    }


    override fun onCreateActionMode(mode: ActionMode?, menu: Menu?): Boolean {
        val inflater: MenuInflater = mode?.menuInflater!!
        inflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onPrepareActionMode(mode: ActionMode?, menu: Menu?): Boolean {
        return false
    }

    override fun onActionItemClicked(mode: ActionMode?, item: MenuItem?): Boolean {
        if (item?.itemId == R.id.menuDelete) {
                for(item in selectedItems){
                    tasks.remove(item)
                    notifyDataSetChanged()
                }
                Toast.makeText(context, "Selected images deleted", Toast.LENGTH_SHORT).show()
            mode?.finish()
        }
        return true
    }

    override fun onDestroyActionMode(mode: ActionMode?) {
        // finished multi selection
        multiSelect = false
        selectedItems.clear()
        notifyDataSetChanged()
    }


}
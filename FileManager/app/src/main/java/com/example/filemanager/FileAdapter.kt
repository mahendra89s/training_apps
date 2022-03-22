package com.example.filemanager

import android.app.Activity
import android.content.Context
import android.net.Uri
import android.text.format.Formatter
import android.text.format.Formatter.formatShortFileSize
import android.util.Log
import android.view.*
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.FileProvider
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.gson.Gson
import kotlinx.android.synthetic.main.file_list_item.view.*
import java.io.File
import java.util.*
import java.util.zip.Inflater

class FileAdapter(
    val context : Context,
    val files : MutableList<File>,
    val listener : FileSelectedListener,
    val activity : AppCompatActivity
) : RecyclerView.Adapter<FileAdapter.ViewHolder>(), androidx.appcompat.view.ActionMode.Callback {
    inner class ViewHolder(view : View) : RecyclerView.ViewHolder(view)

    private var multiSelect = false
    private val selectedItems = mutableListOf<File>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.file_list_item,parent,false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.tvFileName.apply{
            text = files[position].name
        }
        var item = 0
        if(!files[position].isDirectory){
            holder.itemView.tvSize.text = Formatter.formatShortFileSize(context,files[position].length())
        }else{
            val files1: Array<out File>? = files[position].listFiles()
            if (files1 != null) {
                for(file in files1){
                    if(!file.isHidden){
                        item += 1
                    }

                }
            }
            holder.itemView.tvSize.text = "$item Files"
        }
        val uri : Uri = FileProvider.getUriForFile(context,"${context?.applicationContext?.packageName}.provider",files[position])
        if(files[position].name.lowercase(Locale.getDefault()).endsWith(".jpg") || files[position].name.lowercase(Locale.getDefault()).endsWith(".jpeg")){
            Glide.with(context).load(uri).into(holder.itemView.ivIcon)
        }
        else if(files[position].name.lowercase(Locale.getDefault()).endsWith(".apk")){
            holder.itemView.ivIcon.setImageResource(R.drawable.android)
        }
        else if(files[position].name.lowercase(Locale.getDefault()).endsWith(".doc") || files[position].name.lowercase(Locale.getDefault()).endsWith(".docx")){
            holder.itemView.ivIcon.setImageResource(R.drawable.doc)
        }
        else if(files[position].name.lowercase(Locale.getDefault()).endsWith(".doc") || files[position].name.lowercase(Locale.getDefault()).endsWith(".docx")){
            holder.itemView.ivIcon.setImageResource(R.drawable.doc)
        }
        else if(files[position].name.lowercase(Locale.getDefault()).endsWith(".xlsx")){
            holder.itemView.ivIcon.setImageResource(R.drawable.excel)
        }
        else if(files[position].name.lowercase(Locale.getDefault()).endsWith(".mp3")){
            holder.itemView.ivIcon.setImageResource(R.drawable.mp3)
        }
        else if(files[position].name.lowercase(Locale.getDefault()).endsWith(".mp4")){
            Glide.with(context).load(uri).into(holder.itemView.ivIcon)
        }
        else if(files[position].name.lowercase(Locale.getDefault()).endsWith(".wav")){
            holder.itemView.ivIcon.setImageResource(R.drawable.music)
        }
        else if(files[position].name.lowercase(Locale.getDefault()).endsWith(".pdf")){
            holder.itemView.ivIcon.setImageResource(R.drawable.pdf)
        }
        else if(files[position].name.lowercase(Locale.getDefault()).endsWith(".ppt") || files[position].name.lowercase(Locale.getDefault()).endsWith(".pptx")){
            holder.itemView.ivIcon.setImageResource(R.drawable.ppt)
        }
        else if(files[position].isDirectory){
            holder.itemView.ivIcon.setImageResource(R.drawable.ic_folder)
        }
        else{
            holder.itemView.ivIcon.setImageResource(R.drawable.unknown_file)
        }

//
//        holder.itemView.container.setOnClickListener{
//
//        }

        // Get the current image
        val currentTask = files[position]
        // for every item, check to see if it exists in the selected items array
        if (selectedItems.contains(currentTask)) {
            // if the item is selected, let the user know by adding a dark layer above it
            holder.itemView.container.alpha = 0.3f
        } else {
            // else, keep it as it is
            holder.itemView.container.alpha = 1.0f
        }

        // set handler to define what happens when an item is long pressed
        holder.itemView.container.setOnLongClickListener {
            // if multiSelect is false, set it to true and select the item
            if (!multiSelect) {
                // We have started multi selection, so set the flag to true
                multiSelect = true
                // As soon as the user starts multi-select process, show the contextual menu


                activity.startSupportActionMode(this@FileAdapter)
                selectItem(holder, files[holder.adapterPosition])
                true
            } else
                false
        }

        // handler to define what happens when an item is tapped
        holder.itemView.container.setOnClickListener {
            // if the user is in multi-select mode, add it to the multi select list
            if (multiSelect)
                selectItem(holder, currentTask)
            else{
                multiSelect = false
                listener.onFileClicked(files[position])
            }
            // else, simply show the image to the user
//                showPopup(currentTask.file)
        }
    }
    private fun selectItem(holder: ViewHolder, tasks: File) {
        // If the "selectedItems" list contains the item, remove it and set it's state to normal

        if (selectedItems.contains(tasks)) {
            selectedItems.remove(tasks)
            holder.itemView.container.alpha = 1.0f
        } else {
            // Else, add it to the list and add a darker shade over the task, letting the user know that it was selected
            selectedItems.add(tasks)
            notifyDataSetChanged()
            holder.itemView.container.alpha = 0.3f
        }


    }
    override fun getItemCount(): Int {
        return files.size
    }

    override fun onCreateActionMode(
        mode: androidx.appcompat.view.ActionMode?,
        menu: Menu?
    ): Boolean {
        val inflater: MenuInflater = mode?.menuInflater!!
        inflater.inflate(R.menu.delete_menu, menu)
        return true
    }

    override fun onPrepareActionMode(
        mode: androidx.appcompat.view.ActionMode?,
        menu: Menu?
    ): Boolean {
        return false
    }

    override fun onActionItemClicked(
        mode: androidx.appcompat.view.ActionMode?,
        item: MenuItem?
    ): Boolean {
        if (item?.itemId == R.id.menuDelete) {
            Log.d("TAG",selectedItems.isEmpty().toString())
            val gson = Gson()
            val json = gson.toJson(selectedItems)
            listener.onDeleteButtonClicked(files,json)
//            notifyDataSetChanged()
//            for(item1 in selectedItems) {
//                files.remove(item1)
//                if (item1.isDirectory) {
//                    item1.deleteRecursively()
//                } else {
//                    item1.delete()
//                }
//                notifyDataSetChanged()
//            }
            mode?.finish()
        }
        return true
    }

    override fun onDestroyActionMode(mode: androidx.appcompat.view.ActionMode?) {
        // finished multi selection
        multiSelect = false
        selectedItems.clear()
        notifyDataSetChanged()
    }

}
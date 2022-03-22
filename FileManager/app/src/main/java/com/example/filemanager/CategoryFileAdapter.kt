package com.example.filemanager

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.filemanager.Models.CategoryModel
import kotlinx.android.synthetic.main.file_list_item.view.*
import java.io.File

class CategoryFileAdapter(
    val context : Context,
    val data : MutableList<CategoryModel>,
    val fileType : String,
    val listener: FileSelectedListener
): RecyclerView.Adapter<CategoryFileAdapter.ViewHolder>() {
    inner class ViewHolder(view : View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.file_list_item,parent,false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.tvFileName.text = data[position].title
        var fileSize = (data[position].size.toLong().toFloat() / 1000).toLong()
        var suffix = "KB"
        if(fileSize>= 1024){
            fileSize /= 1000
            suffix = "MB"
            if(fileSize>= 1024){
                fileSize /= 1000
                suffix = "GB"
            }
        }
        holder.itemView.tvSize.text = "${String.format("%.2f",fileSize.toFloat())}$suffix"
        if(fileType == "Audio" || data[position].title.contains(".mp3") || data[position].title.contains(".wav")){
            Glide.with(context).load(R.drawable.mp3).into(holder.itemView.ivIcon)
        }
        else if(fileType == "Photos" || data[position].title.contains(".jpg") || data[position].title.contains(".jpeg") || data[position].title.contains(".png")){
            Glide.with(context).load(data[position].contentUri).into(holder.itemView.ivIcon)
        }
        else if(fileType == "Video" || data[position].title.contains(".mp4")){
            Glide.with(context).load(data[position].contentUri).into(holder.itemView.ivIcon)
        }
        else if(fileType == "Apk" || data[position].title.contains(".apk")){
            Glide.with(context).load(R.drawable.apk).into(holder.itemView.ivIcon)
        }
        else if(fileType == "Documents" || fileType == "Downloads"){
            if(data[position].title.contains(".pdf")){
                Glide.with(context).load(R.drawable.pdf).into(holder.itemView.ivIcon)
            }
            else if(data[position].title.contains(".doc") || data[position].title.contains(".docx") || data[position].title.contains(".txt") ){
                Glide.with(context).load(R.drawable.doc).into(holder.itemView.ivIcon)
            }
            else if(data[position].title.contains(".xlsx")){
                Glide.with(context).load(R.drawable.excel).into(holder.itemView.ivIcon)
            }
        }
        else{
            Glide.with(context).load(R.drawable.unknown_file).into(holder.itemView.ivIcon)
        }

        holder.itemView.container.setOnClickListener{
            listener.onFileClicked(File(data[position].data))
        }

    }

    override fun getItemCount(): Int {
        return data.size
    }

}
package com.example.weatherapp

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.forecast_item_list.view.*
import java.text.SimpleDateFormat
import java.util.*

class ForecastAdapter(
    var data : MutableList<Daily>
): RecyclerView.Adapter<ForecastAdapter.ViewHolder>() {
    lateinit var context: Context
    inner class ViewHolder(view : View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.forecast_item_list,parent,false)
        context = parent.context
        return ViewHolder(view)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.itemView.tvWeather.text = data[position].weather[0].main

        val icon = data[position].weather[0].icon
        val iconString = "http://openweathermap.org/img/w/$icon.png"
        Glide.with(context).load(iconString).into(holder.itemView.imageForecastWeather)
        var minTemp = data[position].temp.min.toInt()
        var maxTemp = data[position].temp.max.toInt()
        holder.itemView.tvMinTemp.text = "$minTemp\u2103"
        holder.itemView.tvMaxTemp.text = "$maxTemp\u2103"

        //Date Time library
        val sdf = SimpleDateFormat("EEEE")
        val sdf1 = SimpleDateFormat("yyyy-MM-dd")

        //To get current date and time
        val date = Date()

        val today : String = sdf1.format(date).toString()
        val weatherDate : String = sdf1.format(data[position].dt.times(1000L)).toString()
        if(today == weatherDate ){
            holder.itemView.tvDay.text = "Today"
        }
        else{
            holder.itemView.tvDay.text = sdf.format(data[position].dt.times(1000L))
        }


    }

    override fun getItemCount(): Int {
        return data.size
    }
}
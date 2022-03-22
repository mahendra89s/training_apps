package com.example.weatherapp

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.city_list.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.properties.Delegates

class CityListAdapter(
    val city: MutableList<CityInfoResponse>,
    val onBtnDeleteInteface: CityDeleteInteface
) : RecyclerView.Adapter<CityListAdapter.ViewHolder>() {
    lateinit var context : Context
    lateinit var sharedPreferences: SharedPreferences
    lateinit var arrayList : MutableList<CityInfoResponse>

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CityListAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.city_list,parent,false)
        context = parent.context
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: CityListAdapter.ViewHolder, position: Int) {

        holder.itemView.tvCity.text = city[position].name
        val lat = city[position].lat.toString()
        val lon = city[position].lon.toString()
        val call = ApiClient.getClient.getCurrentWeatherData(lat,lon,"minutely,hourly",API,"metric")
        call.enqueue(object : Callback<ForecastResponse> {
            override fun onResponse(
                call: Call<ForecastResponse>,
                response: Response<ForecastResponse>
            ) {
                if(response.code() == 200){
                    val weatherResponse : ForecastResponse = response.body()!!
                    holder.itemView.tvTempList.text = "${weatherResponse.current.temp.toInt()}\u2103"
                    holder.itemView.tvTempInfo.text = weatherResponse.current.weather[0].main
                }
            }

            override fun onFailure(call: Call<ForecastResponse>, t: Throwable) {
                Toast.makeText(context,"No Result",Toast.LENGTH_LONG).show()
            }

        })
        if(position != 0){
            holder.itemView.btnDelete.setOnClickListener {
                sharedPreferences = context.getSharedPreferences("CityList3", AppCompatActivity.MODE_PRIVATE)
                val gson = Gson()
                val json: String? = sharedPreferences.getString("CityList3","")
                val type = object : TypeToken<List<CityInfoResponse>?>() {}.type
                arrayList = gson.fromJson(json,type)
                arrayList.remove(city[position])
                val json1 = gson.toJson(arrayList)
                val editor : SharedPreferences.Editor = sharedPreferences.edit()
                editor.putString("CityList3", json1).apply()
                city.removeAt(position)
                notifyItemRemoved(position)
                notifyItemRangeChanged(position,city.size)
                onBtnDeleteInteface.getFlag(true)
            }
        }




    }

    override fun getItemCount(): Int {
        return city.size
    }


}
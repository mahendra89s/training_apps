package com.example.weatherapp

import android.annotation.SuppressLint
import android.app.ProgressDialog

import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.fragment_weather_screen.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat

class WeatherScreenFragment : Fragment(R.layout.fragment_weather_screen) {
    lateinit var sharedPreferences: SharedPreferences
    lateinit var arrayList : MutableList<CityInfoResponse>
    private var param1: String? = null
    lateinit var tvCityName : TextView
    lateinit var tvWeatherInfo : TextView
    lateinit var tvTemp : TextView
    lateinit var tvWind : TextView
    lateinit var tvHumidity : TextView
    lateinit var tvPressure : TextView
    lateinit var ivWeather : ImageView
    lateinit var imageViewSunrise : ImageView
    lateinit var imageViewWind : ImageView
    lateinit var imageViewSunset : ImageView
    lateinit var tvSunrise : TextView
    lateinit var tvSunset : TextView
    lateinit var recyclerViewForecast : RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        arguments?.let {
            param1 = it.getString("city")
        }

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tvCityName = view.findViewById(R.id.tvCityName)
        tvWeatherInfo = view.findViewById(R.id.tvWeatherInfo)
        tvTemp = view.findViewById(R.id.tvTemp)
        tvWind = view.findViewById(R.id.tvWind)
        tvHumidity = view.findViewById(R.id.tvHumidity)
        tvPressure = view.findViewById(R.id.tvPressure)
        ivWeather = view.findViewById(R.id.ivWeather)
        imageViewSunrise = view.findViewById(R.id.imageViewSunrise)
        imageViewWind = view.findViewById(R.id.imageViewWind)
        imageViewSunset = view.findViewById(R.id.imageViewSunset)
        tvSunrise = view.findViewById(R.id.tvSunrise)
        tvSunset = view.findViewById(R.id.tvSunset)
        recyclerViewForecast = view.findViewById(R.id.recyclerViewForecast)
        getCurrentWeatherData()
    }

    private fun getCurrentWeatherData(){

        val gson = Gson()
        val type = object : TypeToken<CityInfoResponse?>() {}.type
        val data : CityInfoResponse = gson.fromJson(param1,type)
//        Log.d("data",data.toString())
        val lat = data.lat.toString()
        val lon = data.lon.toString()
        val call : Call<ForecastResponse> = ApiClient.getClient.getCurrentWeatherData(lat,lon,"minutely,hourly",API,"metric")


//        val progressDialog = ProgressDialog(context)
//        progressDialog.max = 100
//        progressDialog.setMessage("Loading....")
//        progressDialog.setTitle("Fetching Weather Data")
//        progressDialog.show()

        call.enqueue(object : Callback<ForecastResponse> {
            @SuppressLint("SimpleDateFormat", "SetTextI18n")
            override fun onResponse(
                call: Call<ForecastResponse>,
                response: Response<ForecastResponse>
            ) {
//                progressDialog.dismiss()
                if(response.code() == 200){
                    val weatherResponse : ForecastResponse = response.body()!!
//                    Log.d("waetherResponse",weatherResponse.toString())
                    tvCityName.text = data.name


                    tvWeatherInfo.text = weatherResponse.current.weather[0].main
                    tvTemp.text = "${weatherResponse.current.temp.toInt()}\u2103"
                    tvWind.text = "${weatherResponse.current.wind_speed} m/s"
                    tvHumidity.text = "${weatherResponse.current.humidity} %"
                    tvPressure.text = "${weatherResponse.current.pressure} hPa"

                    val icon = weatherResponse.current.weather[0].icon
                    val iconString = "http://openweathermap.org/img/w/$icon.png"
                    Glide.with(ivWeather.context).load(iconString).into(ivWeather)

                    Glide.with(imageViewSunrise.context).load(R.drawable.sunrise_icon).into(imageViewSunrise)
                    Glide.with(imageViewWind.context).load(R.drawable.wind_icon).into(imageViewWind)
                    Glide.with(imageViewSunset.context).load(R.drawable.sunset_icon).into(imageViewSunset)


                    val sdf = SimpleDateFormat("hh:mm a")
                    tvSunrise.text = sdf.format(weatherResponse.current.sunrise.times(1000L))
                    tvSunset.text= sdf.format(weatherResponse.current.sunset.times(1000L))

                    val forecastData = mutableListOf<Daily>()
                    for(item in weatherResponse.daily){
                        forecastData.add(item)
                    }
                    val adapter = ForecastAdapter(forecastData)
                    recyclerViewForecast.adapter = adapter
                    recyclerViewForecast.layoutManager = LinearLayoutManager(context,
                        LinearLayoutManager.HORIZONTAL,false)
                }
            }

            override fun onFailure(call: Call<ForecastResponse>, t: Throwable) {
//                progressDialog.dismiss()
                Toast.makeText(context,"N${t.message}", Toast.LENGTH_LONG).show()
            }
        })

    }
    companion object {
        @JvmStatic
        fun newInstance(param1: String) =
            WeatherScreenFragment().apply {
                arguments = Bundle().apply {
                    putString("city",param1)
                }
            }
    }
}
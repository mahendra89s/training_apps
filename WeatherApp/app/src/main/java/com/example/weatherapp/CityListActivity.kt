package com.example.weatherapp

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.activity_city_list.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*


class CityListActivity : AppCompatActivity(){

    lateinit var sharedPreferences: SharedPreferences
    lateinit var arrayList : MutableList<CityInfoResponse>
    var flag : Boolean = false
    var flagDelete : Boolean = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_city_list)
        //Toolbar
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        sharedPreferences = getSharedPreferences("CityList3",MODE_PRIVATE)
        fetchCity()
        btnSearch.setOnClickListener {
            searchCity()
            flag = true
        }

    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun onBackPressed() {

        if(flag || flagDelete){
            Intent(this,MainActivity::class.java).also{
                setResult(Activity.RESULT_OK,it)
                super.onBackPressed()
            }
        }
        else{
            Intent(this,MainActivity::class.java).also{
                setResult(Activity.RESULT_CANCELED,it)
                super.onBackPressed()
            }
        }

    }

    private fun searchCity(){
        val cityName = edtSearch.text.toString()
        val call = ApiClient.getClient.getCityInfo(cityName,"1",API)
        call.enqueue(object : Callback<List<CityInfoResponse>> {
            override fun onResponse(
                call: Call<List<CityInfoResponse>>,
                response: Response<List<CityInfoResponse>>
            ) {
                if(response.code() == 200){
                    val cityResponse : List<CityInfoResponse> = response.body()!!
                    if(cityResponse.isNotEmpty()){
                        Log.d("TAG",cityResponse[0].toString())
                        val addedCity = sharedPreferences.getString("CityList3","")
                        val gson = Gson()
                        val editor : SharedPreferences.Editor = sharedPreferences.edit()
                        val type = object : TypeToken<List<CityInfoResponse?>?>() {}.type
                        arrayList = gson.fromJson(addedCity,type)
                        arrayList.add(cityResponse[0])
                        val json = gson.toJson(arrayList)
                        Log.d("TAG",json)
                        editor.putString("CityList3", json).apply()
                    }
                    else{
                        Toast.makeText(this@CityListActivity,"No City Found !!",Toast.LENGTH_LONG).show()
                    }
                    fetchCity()
                }
            }

            override fun onFailure(call: Call<List<CityInfoResponse>>, t: Throwable) {
                Toast.makeText(this@CityListActivity,"No Result",Toast.LENGTH_LONG).show()
            }

        })
    }
    private fun fetchCity(){
        edtSearch.text.clear()
        closeKeyboard()
        val gson = Gson()
        val json: String? = sharedPreferences.getString("CityList3","")
        val type = object : TypeToken<List<CityInfoResponse>?>() {}.type
        arrayList = gson.fromJson(json,type)
        val adapter = CityListAdapter(arrayList,object : CityDeleteInteface{
            override fun getFlag(flag1: Boolean) {
                flagDelete = flag1
            }

        })
        recyclerViewCityList.adapter = adapter
        recyclerViewCityList.layoutManager = LinearLayoutManager(this@CityListActivity)


    }

    private fun closeKeyboard() {
        val view: View? = this.currentFocus
        if (view != null) {
            val manager: InputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            manager.hideSoftInputFromWindow(view.windowToken, 0)
            edtSearch.clearFocus()
        }
    }

}
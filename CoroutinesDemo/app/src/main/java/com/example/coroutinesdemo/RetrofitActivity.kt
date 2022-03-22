package com.example.coroutinesdemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_retrofit.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

const val BASE_URL = "https://jsonplaceholder.typicode.com/"
class RetrofitActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_retrofit)
        rvComment.layoutManager = LinearLayoutManager(this)

        getMyData()
    }

    private fun getMyData() {
        val retrofitBuilder = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
            .create(CommentInterface::class.java)

        val retrofitData = retrofitBuilder.getComments()

        retrofitData.enqueue(object : Callback<List<Comments>?> {
            override fun onResponse(
                call: Call<List<Comments>?>,
                response: Response<List<Comments>?>
            ) {
                val responseBody = response.body()!!
                val adapter = CommentAdapter(responseBody)
                rvComment.adapter = adapter
                adapter.notifyDataSetChanged()


            }

            override fun onFailure(call: Call<List<Comments>?>, t: Throwable) {

            }
        })
    }
}
package com.example.harrypotter.data.network

import com.example.harrypotter.data.model.Student
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

interface StudentService {

    @GET("house/{house}")
    suspend fun getStudents(
        @Path("house") type : String
    ) : Response<List<Student>>

    companion object{
        operator fun invoke(
            networkConnectionIntercepter: NetworkConnectionIntercepter
        ):StudentService{
            val client = OkHttpClient.Builder()
                .addInterceptor(networkConnectionIntercepter)
                .build()
            return Retrofit.Builder()
                .client(client)
                .baseUrl("http://hp-api.herokuapp.com/api/characters/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(StudentService::class.java)
        }
    }
}
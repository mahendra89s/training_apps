package com.example.harrypotter.data.repository

import com.example.harrypotter.data.model.Student
import com.example.harrypotter.data.network.NetworkConnectionIntercepter
import com.example.harrypotter.data.network.StudentService
import retrofit2.Response
import javax.inject.Inject

class StudentRepository @Inject constructor(
    val networkConnectionIntercepter: NetworkConnectionIntercepter
) {

    suspend fun getStudent(
        house : String
    ) : Response<List<Student>>{
        return StudentService(networkConnectionIntercepter).getStudents(house)
    }
}
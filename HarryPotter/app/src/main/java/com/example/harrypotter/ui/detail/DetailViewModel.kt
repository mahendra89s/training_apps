package com.example.harrypotter.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.harrypotter.data.model.Student

class DetailViewModel(
    val student: Student
) : ViewModel() {
    var studentLiveData = MutableLiveData<Student>()
    val data : String = "No Data"

    init{
        studentLiveData.value = student
    }
    fun getStudentData() : MutableLiveData<Student> = studentLiveData

    fun checkStudentData() {
        if(student.name.isNullOrEmpty()){
            studentLiveData.value?.name = data
        }else{
            studentLiveData.value?.name = student.name.capitalize()
        }

        if(student.species.isNullOrEmpty()){
            studentLiveData.value?.species = data
        }else{
            studentLiveData.value?.species = student.species.capitalize()
        }

        if(student.gender.isNullOrEmpty()){
            studentLiveData.value?.gender = data
        }else{
            studentLiveData.value?.gender = student.gender.capitalize()
        }

        if(student.house.isNullOrEmpty()){
            studentLiveData.value?.house = data
        }else{
            studentLiveData.value?.house = student.house.capitalize()
        }

        if(student.dateOfBirth.isNullOrEmpty()){
            studentLiveData.value?.dateOfBirth = data
        }else{
            studentLiveData.value?.dateOfBirth = student.dateOfBirth.capitalize()
        }

        if(student.actor.isNullOrEmpty()){
            studentLiveData.value?.actor = data
        }else{
            studentLiveData.value?.actor = student.actor.capitalize()
        }

        if(student.alive.isNullOrEmpty()){
            studentLiveData.value?.alive = data
        }else{
            studentLiveData.value?.alive = student.alive.capitalize()
        }
    }
}
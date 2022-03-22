package com.example.harrypotter.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Student(
    var actor: String,
    var alive: String,
    val ancestry: String,
    var dateOfBirth: String,
    val eyeColour: String,
    var gender: String,
    val hairColour: String,
    val hogwartsStaff: Boolean,
    val hogwartsStudent: Boolean,
    var house: String,
    val image: String,
    var name: String,
    val patronus: String,
    var species: String,
    val wizard: Boolean,
    val yearOfBirth: String
) : Parcelable

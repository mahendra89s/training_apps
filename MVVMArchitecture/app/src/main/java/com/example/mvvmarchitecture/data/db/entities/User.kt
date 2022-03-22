package com.example.mvvmarchitecture.data.db.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

const val CURRENT_USER_ID = 0
@Entity
data class User(
    var access_token: String? = null,
    var country_id: String? = null,
    var created: String? = null,
    var dob: String? = null,
    var email: String? = null,
    var first_name: String? = null,
    var gender: String? = null,
    var id: Int? = null,
    var is_active: Boolean? = null,
    var last_name: String? = null,
    var modified: String? = null,
    var phone_no: String? = null,
    var profile_pic: String? = null,
    var role_id: Int? = null,
    var username: String? = null
){
    @PrimaryKey(autoGenerate = false)
    var uid : Int = CURRENT_USER_ID
}
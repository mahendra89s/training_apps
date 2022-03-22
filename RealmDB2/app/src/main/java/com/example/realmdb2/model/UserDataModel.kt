package com.example.realmdb2.model

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class UserDataModel(
    @PrimaryKey
    var id : String? = null,
    var name: String? = null,
    var email: String? = null,
    var mobile: String? = null,
    var added_to_firebase : Boolean = false
) : RealmObject()

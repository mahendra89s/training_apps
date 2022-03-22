package com.example.realmdb

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey


open class UserDataModel : RealmObject() {
    @PrimaryKey
    var id : String? = null
    var name: String? = null
    var email: String? = null
    var mobile: String? = null
}

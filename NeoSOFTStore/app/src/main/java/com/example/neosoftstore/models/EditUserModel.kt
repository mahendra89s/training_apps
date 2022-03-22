package com.example.neosoftstore.models

import android.os.Parcel
import android.os.Parcelable

data class EditUserModel(
    val first_name : String?,
    val last_name : String?,
    val email : String?,
    val phone_no : String?,
    val dob : String?,
    val profile_pic : String?
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(first_name)
        parcel.writeString(last_name)
        parcel.writeString(email)
        parcel.writeString(phone_no)
        parcel.writeString(dob)
        parcel.writeString(profile_pic)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<EditUserModel> {
        override fun createFromParcel(parcel: Parcel): EditUserModel {
            return EditUserModel(parcel)
        }

        override fun newArray(size: Int): Array<EditUserModel?> {
            return arrayOfNulls(size)
        }
    }
}

package com.example.neosoftstore.models

import android.os.Parcel
import android.os.Parcelable

data class QuantitySceenDataModel(
    val product_id : Int,
    val name : String?,
    val image : String?,
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(product_id)
        parcel.writeString(name)
        parcel.writeString(image)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<QuantitySceenDataModel> {
        override fun createFromParcel(parcel: Parcel): QuantitySceenDataModel {
            return QuantitySceenDataModel(parcel)
        }

        override fun newArray(size: Int): Array<QuantitySceenDataModel?> {
            return arrayOfNulls(size)
        }
    }
}

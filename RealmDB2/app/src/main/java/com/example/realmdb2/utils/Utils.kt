package com.example.realmdb2.utils

import android.content.Context
import android.widget.Toast

//object Utils {
//    fun showToast(context: Context, message : String){
//        Toast.makeText(context,message,Toast.LENGTH_LONG).show()
//    }
//}
fun Context.showToast(message: String){
    Toast.makeText(this,message,Toast.LENGTH_LONG).show()
}


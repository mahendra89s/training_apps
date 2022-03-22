package com.example.harrypotter.ui

import android.app.Activity
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import com.example.harrypotter.R
import java.util.zip.Inflater
import javax.inject.Inject

class LoadingDialog @Inject constructor(
    private val activity : Activity
) {
    lateinit var dialog: AlertDialog
    fun startDialog(){
        val builder : AlertDialog.Builder = AlertDialog.Builder(activity)
        val inflater : LayoutInflater = activity.layoutInflater
        builder.setView(inflater.inflate(R.layout.loader_layout,null))
        builder.setCancelable(false)
        dialog = builder.create()
        dialog.show()
    }

    fun dismissDialog(){
        dialog.dismiss()
    }
}
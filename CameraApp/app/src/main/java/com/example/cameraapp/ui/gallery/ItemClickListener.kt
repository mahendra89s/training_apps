package com.example.cameraapp.ui.gallery

import android.view.View

interface ItemClickListener {
    fun onLongClick(position : Int, view : View)
    fun onClick(position: Int,view: View)
}
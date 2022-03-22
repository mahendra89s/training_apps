package com.example.filemanager.Models

import android.net.Uri

data class CategoryModel(
    val title : String,
    val data : String,
    val size : String,
    val contentUri : Uri
)

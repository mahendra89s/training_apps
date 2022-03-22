package com.example.harrypotter.utils

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.example.harrypotter.R


@BindingAdapter("image")
fun loadImage(imageView: ImageView,url : String){
    if(url != ""){
        Glide.with(imageView)
            .load(url)
            .into(imageView)
    }
    else{
        Glide.with(imageView)
            .load(R.drawable.no_image)
            .into(imageView)
    }


}
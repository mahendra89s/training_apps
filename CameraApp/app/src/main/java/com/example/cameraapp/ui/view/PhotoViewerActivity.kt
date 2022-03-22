package com.example.cameraapp.ui.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.example.cameraapp.R

import com.example.cameraapp.databinding.ActivityPhotoViewerBinding
import com.example.cameraapp.model.ImagePath
import kotlinx.android.synthetic.main.activity_photo_viewer.*
import java.io.File

class PhotoViewerActivity : AppCompatActivity() {
    lateinit var binding: ActivityPhotoViewerBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_photo_viewer)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        val imagePath = intent.extras?.getString("path")
        val file = File(imagePath)
        val name = file.name
//        Log.d("filename",file.name)
//        Log.d("path",imagePath.toString())
        binding.image = ImagePath(imagePath,name)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
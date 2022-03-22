package com.example.harrypotter.ui.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.harrypotter.R
import com.example.harrypotter.data.model.Student
import com.example.harrypotter.databinding.ActivityDetailBinding

class DetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val student : Student? = intent.getParcelableExtra("student")
        val factory = DetailFactory(student!!)
        val binding : ActivityDetailBinding = DataBindingUtil.setContentView(this,R.layout.activity_detail)
        val viewModel = ViewModelProvider(this,factory)[DetailViewModel::class.java]
        binding.student = viewModel
        viewModel.getStudentData().observe(this, Observer {
            viewModel.checkStudentData()
        })
    }
}
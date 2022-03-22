package com.example.notesapplicationfirebase.ui.add

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.notesapplicationfirebase.R
import com.example.notesapplicationfirebase.data.model.Note
import com.example.notesapplicationfirebase.databinding.ActivityAddBinding
import kotlinx.android.synthetic.main.activity_add.*

class AddActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val intent = intent.extras
        val data = intent?.getParcelable("note") ?: Note("", "", "")
        val binding: ActivityAddBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_add)
        val viewModel = ViewModelProvider(this, AddViewModelFactory(data))[AddViewModel::class.java]
        binding.viewModel = viewModel

        btnBack.setOnClickListener {
            onBackPressed()
        }
        viewModel.shouldCloseLiveData.observe(this, {
            finish()
        })

    }
}
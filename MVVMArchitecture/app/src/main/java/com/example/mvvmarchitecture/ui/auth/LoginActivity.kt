package com.example.mvvmarchitecture.ui.auth

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.mvvmarchitecture.R
import com.example.mvvmarchitecture.data.db.AppDatabase
import com.example.mvvmarchitecture.data.db.entities.User
import com.example.mvvmarchitecture.data.network.MyAPI
import com.example.mvvmarchitecture.data.network.NetworkInterceptor
import com.example.mvvmarchitecture.data.repository.UserRepository
import com.example.mvvmarchitecture.databinding.ActivityLoginBinding
import com.example.mvvmarchitecture.ui.home.HomeActivity
import com.example.mvvmarchitecture.utils.hide
import com.example.mvvmarchitecture.utils.show
import com.example.mvvmarchitecture.utils.toast
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity(), AuthListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val networkInterceptor = NetworkInterceptor(this)
        val api = MyAPI(networkInterceptor)
        val db = AppDatabase(this)
        val repository = UserRepository(api,db)
        val factory = AuthViewModelFactory(repository,this)

        val binding : ActivityLoginBinding= DataBindingUtil.setContentView(this,R.layout.activity_login)
        val viewModel = ViewModelProviders.of(this,factory)[AuthViewModel::class.java]
        binding.viewmodel = viewModel
        viewModel.authListener = this
        viewModel.getUserLoggedIn().observe(this, Observer {user->
            if(user != null){
                Intent(this,HomeActivity::class.java).also {
                    it.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                    startActivity(it)
                }
            }
        })

        btnregister.setOnClickListener {
            Intent(this,SignupActivity::class.java).also {
                startActivity(it)
            }
        }
    }

    override fun onSuccess(user : User) {
        progressBar.hide()
        toast("${user.first_name} is Logged in")
    }

    override fun onStarted() {
        progressBar.show()
    }

    override fun onFailure(message : String) {
        progressBar.hide()
        toast(message)
    }
}
package com.example.neosoftstore

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.Toast
import com.example.neosoftstore.apis.ApiClient
import com.example.neosoftstore.apis.SharedPrefManagerForUserLogReg
import com.example.neosoftstore.models.LoginResponse
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_register.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setContentView(R.layout.activity_login)

        btnlogin.setOnClickListener {
            submitDetails()
        }
        btnregister.setOnClickListener {
            Intent(this,RegisterActivity::class.java).also{
                startActivity(it)
            }
        }
        btnForgetPassword.setOnClickListener {
            Intent(this,ForgetPasswordActivity::class.java).also{
                startActivity(it)
            }
        }
    }
    private fun submitDetails(){
        if(!emailValidator() || !passwordValidator()){
            return
        }
        val email = edtUsername.text.toString()
        val password = edtPassword.text.toString()
        Log.d("Email",email)
        Log.d("Password",password)

        val call = ApiClient.getLoginRegClient.loginUser(email,password)
        call.enqueue(object : Callback<LoginResponse>{
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                Log.d("rsp",response.toString())
                if(response.code() == 200){
                    SharedPrefManagerForUserLogReg.getInstance(applicationContext).saveAccessKey(
                        response.body()?.data?.access_token!!)
                    Intent(this@LoginActivity,HomeScreenActivity::class.java).also{
                        it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                        startActivity(it)
                    }
                }
                else{
                    Toast.makeText(applicationContext,response.message().toString(), Toast.LENGTH_LONG).show()
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                Log.d("TAG",t.message.toString())
                Toast.makeText(applicationContext,t.message,Toast.LENGTH_LONG).show()
            }

        })

    }
    private fun emailValidator():Boolean{
        val emailPattern = Regex("[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+")
        return when {
            edtUsername.text.toString() == "" -> {
                showError("This Field is required!!",edtUsername)
                false
            }
            !edtUsername.text.toString().matches(emailPattern) -> {
                showError("Invalid Email!!",edtUsername)
                false
            }
            else -> {
                true
            }
        }
    }

    private fun passwordValidator(): Boolean {
        return when {
            edtPassword.text.toString() == "" -> {
                showError("This Field is required!!",edtPassword)
                false
            }
            else -> {
                true
            }
        }
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private fun showError(msg : String, editText: EditText){
        editText.setError(
            msg,
            resources.getDrawable(R.drawable.ic_error_icon).apply {
                setBounds(-20,0,60,60)
            }
        )
    }
}
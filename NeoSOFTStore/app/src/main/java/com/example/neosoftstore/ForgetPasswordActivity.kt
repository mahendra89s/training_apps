package com.example.neosoftstore

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.Toast
import com.example.neosoftstore.apis.ApiClient
import com.example.neosoftstore.models.ForgetPasswordResponseModel
import kotlinx.android.synthetic.main.activity_forget_password.*
import kotlinx.android.synthetic.main.activity_register.*
import kotlinx.android.synthetic.main.activity_register.edtEmailReg
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ForgetPasswordActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forget_password)
        val toolbar = findViewById<androidx.appcompat.widget.Toolbar>(R.id.toolbar_forget_password)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        toolbar?.setNavigationIcon(R.drawable.ic_back_btn)


        toolbar?.setNavigationOnClickListener {
            finish()
        }
        btnForgetScreen.setOnClickListener {
            resetPassword()
        }
    }
    private fun resetPassword(){
        if(!emailValidator()){
            return
        }
        val email = edtEmailForgetScreen.text.toString()
        val call = ApiClient.getLoginRegClient.forgetPassword(email)
        call.enqueue(object : Callback<ForgetPasswordResponseModel>{
            override fun onResponse(
                call: Call<ForgetPasswordResponseModel>,
                response: Response<ForgetPasswordResponseModel>
            ) {
                if(response.body()!!.status == 200){
                    Toast.makeText(applicationContext,response.body()?.user_msg.toString(), Toast.LENGTH_LONG).show()
                    finish()
                }
                else{
                    Toast.makeText(applicationContext,response.body()?.user_msg.toString(), Toast.LENGTH_LONG).show()

                }
            }

            override fun onFailure(call: Call<ForgetPasswordResponseModel>, t: Throwable) {
                Toast.makeText(applicationContext,t.message, Toast.LENGTH_LONG).show()
            }

        })
    }
    private fun emailValidator():Boolean{
        val emailPattern = Regex("[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+")
        return when {
            edtEmailForgetScreen.text.toString() == "" -> {
                showError("This Field is required!!",edtEmailForgetScreen)
                false
            }
            !edtEmailForgetScreen.text.toString().matches(emailPattern) -> {
                showError("Invalid Email!!",edtEmailForgetScreen)
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
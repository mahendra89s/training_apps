package com.example.mvvmarchitecture.ui.auth

import android.content.Context


import android.view.View
import android.widget.ImageView

import androidx.core.content.ContextCompat.getDrawable

import androidx.lifecycle.ViewModel
import com.example.mvvmarchitecture.R
import com.example.mvvmarchitecture.data.repository.UserRepository
import com.example.mvvmarchitecture.utils.ApiException
import com.example.mvvmarchitecture.utils.Coroutines
import com.example.mvvmarchitecture.utils.InternetException

import kotlinx.android.synthetic.main.activity_signup.view.*

class AuthViewModel(
    private val repository: UserRepository,
    private val context: Context
) : ViewModel() {
    var email : String? = null
    var password : String? = null
    var firstName : String? = null
    var lastName : String? = null
    var confirmPassword : String? = null
    var phone : String? = null
    var gender : String? = "M"
    var isAgreed : Boolean = false

    var authListener : AuthListener? = null
    fun onLoginButtonClick(view : View){
        authListener?.onStarted()
        if(email.isNullOrEmpty() or password.isNullOrEmpty()){
            authListener?.onFailure("Invalid Username or password")
            return
        }
        Coroutines.main {
            try{
                val response =  repository.userLogin(email!!,password!!)
                response.data.let {
                    authListener?.onSuccess(it)
                    repository.saveUser(it)
                    return@main
                }
                authListener?.onFailure(response.user_msg)
            }catch (e : ApiException){
                authListener?.onFailure(e.message!!)
            }catch (e : InternetException){
                authListener?.onFailure(e.message!!)
            }
        }
    }
    fun onRadioButtonCheckChanged(view: View){
        when(view.id){
            R.id.rdMale ->{
                gender = "M"
            }
            R.id.rdFemale ->{
                gender = "F"
            }
        }
    }
    fun onAgreeClick(view: View){
        if(view is ImageView){
            if(view.drawable.constantState!! == getDrawable(context,R.drawable.uncheck_icon)?.constantState){
                view.setImageResource(R.drawable.checked_icon)
                isAgreed = true
            }
            else{
                view.setImageResource(R.drawable.uncheck_icon)
                isAgreed = false
            }
        }

    }


    fun onRegisterButtonClick(view: View){
        authListener?.onStarted()
        if(email.isNullOrEmpty() or firstName.isNullOrEmpty() or lastName.isNullOrEmpty()
            or password.isNullOrEmpty() or confirmPassword.isNullOrEmpty()
            or phone.isNullOrEmpty() or gender.isNullOrEmpty() or !isAgreed){
            authListener?.onFailure("Check Details !!!")
            return
        }
        Coroutines.main {
            try{
                val response = repository.userRegister(firstName!!,lastName!!,email!!,password!!,confirmPassword!!,gender!!,phone!!)
                response.data.let {
                    authListener?.onSuccess(it)
                    repository.saveUser(it)
                    return@main
                }
                authListener?.onFailure(response.user_msg)
            }catch (e : ApiException){
                authListener?.onFailure(e.message!!)
            }catch (e : InternetException){
                authListener?.onFailure(e.message!!)
            }

        }

    }
    fun getUserLoggedIn() = repository.getUser()
}
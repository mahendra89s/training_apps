package com.example.neosoftstore


import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.RadioButton
import android.widget.Toast
import com.example.neosoftstore.apis.ApiClient
import com.example.neosoftstore.apis.SharedPrefManagerForUserLogReg
import com.example.neosoftstore.models.RegisterResponse
import kotlinx.android.synthetic.main.activity_register.*
import kotlinx.android.synthetic.main.activity_register.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class RegisterActivity : AppCompatActivity() {
    @SuppressLint("UseCompatLoadingForDrawables")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        val toolbar = findViewById<androidx.appcompat.widget.Toolbar>(R.id.toolbar_reg)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        toolbar?.setNavigationIcon(R.drawable.ic_back_btn)


        toolbar?.setNavigationOnClickListener {
            finish()
        }
        regRadioGroup.check(R.id.rdMale)
        rdMale.setOnClickListener {
            if(rdMale.isChecked){
                regRadioGroup.check(R.id.rdMale)
                rdMale.isChecked = true
                rdFemale.isChecked = false
            }
        }
        rdFemale.setOnClickListener {
            if(rdFemale.isChecked){
                regRadioGroup.check(R.id.rdFemale)
                rdFemale.isChecked = true
                rdMale.isChecked = false
            }
        }
        checkAgreeReg.setOnClickListener {
            if(checkAgreeReg.drawable.constantState!! == resources.getDrawable(R.drawable.uncheck_icon).constantState){
                checkAgreeReg.setImageResource(R.drawable.checked_icon)
            }
            else{
                checkAgreeReg.setImageResource(R.drawable.uncheck_icon)
            }
        }


        regBtnRegister.setOnClickListener {
            submitDetails()
        }


    }

    private fun submitDetails(){
        if(!checkTerms() or !phoneValidator() or !confirmPassValidator() or
            !passwordValidator() or !emailValidator() or !lastNameValidator() or !firstNameValidator()){
            return
        }
        var first_name = edtFirstName.text.toString()
        var last_name = edtLastName.text.toString()
        var email = edtEmailReg.text.toString()
        var password = edtPassReg.text.toString()
        var confirm_password = edtConfPassReg.text.toString()
        var phone_no = edtPhoneReg.text.toString().toLong()
        val selectedRadio = findViewById<RadioButton>(regRadioGroup.checkedRadioButtonId)
        var gender = if(selectedRadio.text.toString() == "Male"){
            "M"
        }
        else{
            "F"
        }
        Log.d("First Name",first_name)
        Log.d("Last Name",last_name)
        Log.d("Email",email)
        Log.d("Password",password)
        Log.d("Confirm Password",confirm_password)
        Log.d("Phone",phone_no.toString())
        Log.d("Gender",gender)

        val call = ApiClient.getLoginRegClient.registerUser(first_name,last_name,email,password,confirm_password,gender,phone_no)
        call.enqueue(object: Callback<RegisterResponse>{
            override fun onResponse(
                call: Call<RegisterResponse>,
                response: Response<RegisterResponse>
            ) {
                Log.d("tag",response.toString())
                if(response.code() == 200){
                    SharedPrefManagerForUserLogReg.getInstance(applicationContext).saveAccessKey(response.body()?.data?.access_token!!)
                    Intent(this@RegisterActivity,HomeScreenActivity::class.java).also{
                        it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                        startActivity(it)
                    }
                }
                else{
                    Toast.makeText(applicationContext,"Registration unsuccesful",Toast.LENGTH_LONG).show()
                }
            }

            override fun onFailure(call: Call<RegisterResponse>, t: Throwable) {
                Toast.makeText(applicationContext,t.message,Toast.LENGTH_LONG).show()
            }

        })
    }

    private fun firstNameValidator(): Boolean{
        return if(edtFirstName.text.toString() == ""){
            showError("This Field is required!!",edtFirstName)
            false
        } else {
            true
        }
    }
    private fun lastNameValidator(): Boolean{
        return if(edtLastName.text.toString() == ""){
            showError("This Field is required!!",edtLastName)
            false
        } else {
            true
        }
    }
    private fun emailValidator():Boolean{
        val emailPattern = Regex("[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+")
        return when {
            edtEmailReg.text.toString() == "" -> {
                showError("This Field is required!!",edtEmailReg)
                false
            }
            !edtEmailReg.text.toString().matches(emailPattern) -> {
                showError("Invalid Email!!",edtEmailReg)
                false
            }
            else -> {
                true
            }
        }
    }

    private fun passwordValidator(): Boolean {
        return when {
            edtPassReg.text.toString() == "" -> {
                showError("This Field is required!!",edtPassReg)
                false
            }
            edtPassReg.text.toString().length <= 6 -> {
                showError("Password should be min 6 chars long",edtPassReg)
                false
            }
            else -> {
                true
            }
        }
    }
    private fun confirmPassValidator(): Boolean {
        return when{
            edtConfPassReg.text.toString() == "" -> {
                showError("This Field is required!!",edtConfPassReg)
                false
            }
            edtConfPassReg.text.toString() != edtPassReg.text.toString() -> {
                showError("Password not matches!!",edtConfPassReg)
                false
            }
            edtConfPassReg.text.toString() == edtPassReg.text.toString() -> {
                true
            }
            else -> false
        }
    }
    private fun phoneValidator() : Boolean {
        return when{
            edtPhoneReg.text.toString() == "" -> {
                showError("This Field is required!!",edtPhoneReg)
                false
            }
            edtPhoneReg.text.toString().length >10 || edtPhoneReg.text.toString().length < 10 -> {
                showError("Invalid Phone Number!!",edtPhoneReg)
                false
            }
            edtPhoneReg.text.toString().length == 10 -> {
                true
            }
            else -> false
        }
    }




    @SuppressLint("UseCompatLoadingForDrawables")
    private fun checkTerms(): Boolean{
        if(checkAgreeReg.drawable.constantState!! == resources.getDrawable(R.drawable.uncheck_icon).constantState){
            Toast.makeText(applicationContext,"Agree the terms and Conditions!!",Toast.LENGTH_LONG).show()
            return false
        }
        return true

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
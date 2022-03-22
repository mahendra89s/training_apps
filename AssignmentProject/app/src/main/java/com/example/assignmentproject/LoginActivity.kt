package com.example.assignmentproject


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        btnSubmit.setOnClickListener {
           confirmInput()
        }


    }
    private fun confirmInput(){
        if(!nameValidator() or !mobileNumberValidator() or !emailValidator() or !passwordValidator()){
            return
        }
        AlertDialog.Builder(this)
            .setTitle("Successful")
            .setPositiveButton("Yes"){dialogInterface, i ->
                layoutEdtName.editText?.apply {
                    text?.clear()
                    clearFocus()
                }
                layoutEdtNumber.editText?.apply {
                    text?.clear()
                    clearFocus()
                }
                layoutEdtEmail.editText?.apply {
                    text?.clear()
                    clearFocus()
                }
                layoutEdtPassword.editText?.apply {
                    text?.clear()
                    clearFocus()
                }
                dialogInterface.cancel()
            }
            .create()
            .show()

    }


    private fun nameValidator():Boolean{
        if(layoutEdtName.editText?.text.toString().isEmpty()){
            layoutEdtName.apply {
                error = "Fleid can't be Blank"
                isExpandedHintEnabled = false
                editText?.background = context.getDrawable(R.drawable.error_border_textfields)
                requestFocus()
            }
            return false
        }
        else{
            layoutEdtName.apply {
                error = null
                isExpandedHintEnabled = true
                editText?.background = context.getDrawable(R.drawable.circular_border_textfield)
            }
            layoutEdtNumber.requestFocus()
            return true
        }
    }

    private fun mobileNumberValidator():Boolean{
        if(layoutEdtNumber.editText?.text.toString().isEmpty()){
            layoutEdtNumber.apply {
                error = "Fleid Can't be Blank"
                isExpandedHintEnabled = false
                editText?.background = context.getDrawable(R.drawable.error_border_textfields)
            }
            return false
        }
        else{
            layoutEdtNumber.apply {
                error = null
                isExpandedHintEnabled = true
                editText?.background = context.getDrawable(R.drawable.circular_border_textfield)
            }
            layoutEdtEmail.requestFocus()
            return true
        }
    }

    private fun emailValidator():Boolean{
        if(layoutEdtEmail.editText?.text.toString().isEmpty()){
            layoutEdtEmail.apply {
                error = "Fleid Can't be Blank"
                isExpandedHintEnabled = false
                editText?.background = context.getDrawable(R.drawable.error_border_textfields)
            }
            return false
        }
        else{
            layoutEdtEmail.apply {
                error = null
                isExpandedHintEnabled = true
                editText?.background = context.getDrawable(R.drawable.circular_border_textfield)
            }
            layoutEdtPassword.requestFocus()
            return true
        }
    }

    private fun passwordValidator():Boolean{
        if(layoutEdtPassword.editText?.text.toString().isEmpty()){
            layoutEdtPassword.apply {
                error = "Fleid Can't be Blank"
                isExpandedHintEnabled = false
                editText?.background = context.getDrawable(R.drawable.error_border_textfields)
            }
            return false
        }
        else{
            layoutEdtPassword.apply {
                error = null
                isExpandedHintEnabled = true
                editText?.background = context.getDrawable(R.drawable.circular_border_textfield)
            }
            return true
        }
    }
}
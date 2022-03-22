package com.example.neosoftstore.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.EditText
import androidx.fragment.app.Fragment
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.res.ResourcesCompat
import androidx.navigation.fragment.findNavController
import com.example.neosoftstore.R
import com.example.neosoftstore.apis.ApiClient
import com.example.neosoftstore.apis.SharedPrefManagerForUserLogReg
import com.example.neosoftstore.models.EditProfileResponse
import kotlinx.android.synthetic.main.activity_register.*
import kotlinx.android.synthetic.main.activity_register.edtPassReg
import kotlinx.android.synthetic.main.fragment_reset_password.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ResetPasswordFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_reset_password, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val toolbar = activity?.findViewById<androidx.appcompat.widget.Toolbar>(R.id.homeScreenToolbar)
        val textViewToolbar = toolbar?.findViewById<TextView>(R.id.toolbarTitle)
        textViewToolbar!!.apply {
            text = "Reset Password"
            typeface = ResourcesCompat.getFont(context, R.font.gotham_medium)
            textSize = 25F
        }
        btnResetPasswordScreen.setOnClickListener {
            resetPassword()
        }
    }
    override fun onPrepareOptionsMenu(menu: Menu) {
        menu.findItem(R.id.searchBar).isVisible = false
        super.onPrepareOptionsMenu(menu)

    }
    private fun resetPassword(){
        if(!newPasswordConfValidator() or !newPasswordValidator() or !oldPassword()){
            return
        }
        val oldPassword = edtCurrentPasswordReset.text.toString()
        val newPassword = edtNewPassword.text.toString()
        val confirmNewPassword = edtConfPasswordReset.text.toString()

        Log.d("Passwords", "$oldPassword $newPassword $confirmNewPassword")
        val accessKey = SharedPrefManagerForUserLogReg.getInstance(requireContext()).accessKey
        val call = ApiClient.getUserInfoClient.resetPassword(accessKey!!,oldPassword,newPassword,confirmNewPassword)
        call.enqueue(object : Callback<EditProfileResponse>{
            override fun onResponse(
                call: Call<EditProfileResponse>,
                response: Response<EditProfileResponse>
            ) {

                if(response.body()!!.status == 200){
                    Toast.makeText(requireContext(),response.body()?.user_msg, Toast.LENGTH_LONG).show()
                    findNavController().navigateUp()
                }
                else{
                    Toast.makeText(requireContext(),response.body()?.user_msg, Toast.LENGTH_LONG).show()
                }
            }

            override fun onFailure(call: Call<EditProfileResponse>, t: Throwable) {
                Toast.makeText(context,t.message, Toast.LENGTH_LONG).show()
            }

        })
    }
    private fun oldPassword() : Boolean{
        return when {
            edtCurrentPasswordReset.text.toString() == "" -> {
                showError("This Field is required!!",edtCurrentPasswordReset)
                false
            }
            else -> {
                true
            }
        }
    }
    private fun newPasswordValidator(): Boolean {
        return when {
            edtNewPassword.text.toString() == "" -> {
                showError("This Field is required!!",edtNewPassword)
                false
            }
            edtNewPassword.text.toString().length <= 6 -> {
                showError("Password should be min 6 chars long",edtNewPassword)
                false
            }
            else -> {
                true
            }
        }
    }
    private fun newPasswordConfValidator(): Boolean {
        return when {
            edtConfPasswordReset.text.toString() == "" -> {
                showError("This Field is required!!",edtConfPasswordReset)
                false
            }
            edtConfPasswordReset.text.toString() != edtNewPassword.text.toString()-> {
                showError("Password Not matches!!!",edtConfPasswordReset)
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
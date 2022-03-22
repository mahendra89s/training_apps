package com.example.neosoftstore.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.res.ResourcesCompat
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.neosoftstore.R
import com.example.neosoftstore.apis.ApiClient
import com.example.neosoftstore.apis.SharedPrefManagerForUserLogReg
import com.example.neosoftstore.models.EditUserModel
import com.example.neosoftstore.models.UserInfoModel
import kotlinx.android.synthetic.main.fragment_my_account.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MyAccountFragment : Fragment() {
    var accessKey: String? = null
    lateinit var userEdit : EditUserModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_my_account, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val toolbar = activity?.findViewById<androidx.appcompat.widget.Toolbar>(R.id.homeScreenToolbar)
        val textViewToolbar = toolbar?.findViewById<TextView>(R.id.toolbarTitle)
        textViewToolbar!!.apply {
            text = "My Account"
            typeface = ResourcesCompat.getFont(context, R.font.gotham_medium)
            textSize = 25F
        }

        accessKey = SharedPrefManagerForUserLogReg.getInstance(requireContext()).accessKey
        val call = ApiClient.getUserInfoClient.getUserDetails(accessKey!!,accessKey!!)
        call.enqueue(object : Callback<UserInfoModel>{
            override fun onResponse(call: Call<UserInfoModel>, response: Response<UserInfoModel>) {
                if(response.body()?.status == 200){
                    val userDetailsResponse = response.body()
                    if(userDetailsResponse?.data?.user_data?.profile_pic.isNullOrEmpty()){
                        Glide.with(context!!).load(R.drawable.user_male).into(ivProfileMyAcc)
                    }
                    else{
                        Glide.with(context!!).load(userDetailsResponse?.data?.user_data?.profile_pic).into(ivProfileMyAcc)
                    }
                    tvFirstNameMyAcc.text = userDetailsResponse?.data?.user_data?.first_name
                    tvLastNameMyAcc.text = userDetailsResponse?.data?.user_data?.last_name
                    tvEmailMyAcc.text = userDetailsResponse?.data?.user_data?.email
                    tvPhoneMyAcc.text = userDetailsResponse?.data?.user_data?.phone_no
                    if(userDetailsResponse?.data?.user_data?.dob == null){
                        tvDobMyAcc.text = "No Data"
                    }
                    else{
                        tvDobMyAcc.text = userDetailsResponse.data.user_data.dob
                    }
                    if(userDetailsResponse?.data?.user_data?.profile_pic != null){
                        Glide.with(requireContext()).load(userDetailsResponse.data.user_data.profile_pic).into(ivProfileMyAcc)
                    }

                    userDetailsResponse?.data?.user_data?.apply {
                        userEdit = EditUserModel(first_name,last_name,email,phone_no,dob ,profile_pic)
                    }

                }else{
                    Toast.makeText(requireContext(),response.body()?.message.toString(), Toast.LENGTH_LONG).show()
                }
            }

            override fun onFailure(call: Call<UserInfoModel>, t: Throwable) {
                Toast.makeText(context,t.message, Toast.LENGTH_LONG).show()
            }

        })

        btnEditProfile.setOnClickListener {
            val bundle = Bundle()
            bundle.putParcelable("userData",userEdit)
            findNavController().navigate(R.id.action_myAccountFragment_to_editProfileFragment,bundle)
        }
        btnResetPassword.setOnClickListener {
            findNavController().navigate(R.id.action_myAccountFragment_to_resetPasswordFragment)
        }
    }

}
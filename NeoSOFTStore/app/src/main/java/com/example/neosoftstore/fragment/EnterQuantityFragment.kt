package com.example.neosoftstore.fragment

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.InsetDrawable
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.DialogFragment
import androidx.navigation.fragment.DialogFragmentNavigator
import com.bumptech.glide.Glide
import com.example.neosoftstore.HomeScreenActivity
import com.example.neosoftstore.MainActivity
import com.example.neosoftstore.R
import com.example.neosoftstore.apis.ApiClient
import com.example.neosoftstore.apis.SharedPrefManagerForUserLogReg
import com.example.neosoftstore.models.AddDelEditCartResponseModel
import com.example.neosoftstore.models.QuantitySceenDataModel
import kotlinx.android.synthetic.main.fragment_enter_quantity.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*


class EnterQuantityFragment : DialogFragment(){

    private var data : QuantitySceenDataModel? = null
    private var accessKey : String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_enter_quantity, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        data = arguments?.getParcelable("PassDatatoQuantityScreen")
        tvProductNameQuantScreen.text = data?.name?.replaceFirstChar {
            if (it.isLowerCase()) it.titlecase(
                Locale.getDefault()
            ) else it.toString()
        }
        Glide.with(requireContext()).load(data?.image).into(imgProductQuantScreen)

        btnSubmitQuantity.setOnClickListener {
            if(edtEnterQuantity.text.toString() != "" && edtEnterQuantity.text.toString().toInt()>0){
                val quantity = edtEnterQuantity.text.toString().toInt()
//                Log.d("quant",quantity.toString())
                accessKey = SharedPrefManagerForUserLogReg.getInstance(requireContext()).accessKey
                val call = ApiClient.cartClient.addCart(accessKey!!,data?.product_id!!,quantity)
                call.enqueue(object : Callback<AddDelEditCartResponseModel>{
                    override fun onResponse(
                        call: Call<AddDelEditCartResponseModel>,
                        response: Response<AddDelEditCartResponseModel>
                    ) {
                        if(response.body()?.status == 200){
                            Toast.makeText(requireContext(), response.body()!!.user_msg, Toast.LENGTH_LONG).show()
                            dialog?.dismiss()
                            (activity as HomeScreenActivity).refreshData()

                        }
                        else{
                            Toast.makeText(requireContext(), response.body()!!.user_msg, Toast.LENGTH_LONG).show()
                            dialog?.dismiss()
                        }
                    }

                    override fun onFailure(call: Call<AddDelEditCartResponseModel>, t: Throwable) {
                        Toast.makeText(requireContext(), t.message,Toast.LENGTH_LONG).show()
                        dialog?.dismiss()
                    }

                })
            }
            else{
                Toast.makeText(requireContext(), "Quantity Should not be 0 or less!!!",Toast.LENGTH_LONG).show()
            }
        }

    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(ConstraintLayout.LayoutParams.MATCH_PARENT,ConstraintLayout.LayoutParams.WRAP_CONTENT)
        val back = ColorDrawable(Color.TRANSPARENT)
        val margin = 50
        val inset = InsetDrawable(back, margin)
        dialog?.window?.setBackgroundDrawable(inset)

    }
}
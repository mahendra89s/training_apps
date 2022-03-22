package com.example.neosoftstore.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.neosoftstore.R
import com.example.neosoftstore.adapters.OrderDetailAdapter
import com.example.neosoftstore.apis.ApiClient
import com.example.neosoftstore.apis.SharedPrefManagerForUserLogReg
import com.example.neosoftstore.models.OrderDetail
import com.example.neosoftstore.models.OrderDetailResponseModel
import kotlinx.android.synthetic.main.fragment_order_details_screen.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class OrderDetailFragment : Fragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_order_details_screen, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val toolbar = activity?.findViewById<androidx.appcompat.widget.Toolbar>(R.id.homeScreenToolbar)
        val textViewToolbar = toolbar?.findViewById<TextView>(R.id.toolbarTitle)
        val data = arguments?.getInt("Order_id")
        textViewToolbar!!.apply {
            text = String.format(requireContext().getString(R.string.orderId),data)
            typeface = ResourcesCompat.getFont(context, R.font.gotham_medium)
            textSize = 25F
        }
        val accessKey = SharedPrefManagerForUserLogReg.getInstance(requireContext()).accessKey
        val call = ApiClient.orderClient.getOrderDetails(accessKey!!,data!!)
        call.enqueue(object : Callback<OrderDetailResponseModel>{
            override fun onResponse(
                call: Call<OrderDetailResponseModel>,
                response: Response<OrderDetailResponseModel>
            ) {
                if(response.body()!!.status == 200){
                    tvTotalPriceOrderDetail.text = String.format(
                        requireContext().getString(R.string.priceMyCart),
                        response.body()!!.data.cost
                    )
                    val itemList = mutableListOf<OrderDetail>()
                    for(item in response.body()!!.data.order_details){
                        itemList.add(item)
                    }
                    val adapter = OrderDetailAdapter(itemList,requireContext())
                    rvOrderDetail.adapter = adapter
                    rvOrderDetail.layoutManager = LinearLayoutManager(requireContext())
                }
                else{
                    Toast.makeText(requireContext(), response.body()!!.user_msg, Toast.LENGTH_LONG).show()
                }
            }

            override fun onFailure(call: Call<OrderDetailResponseModel>, t: Throwable) {
                Toast.makeText(requireContext(), t.message, Toast.LENGTH_LONG).show()
            }

        })
    }
}
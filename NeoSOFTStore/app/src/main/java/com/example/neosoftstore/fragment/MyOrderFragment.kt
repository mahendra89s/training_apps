package com.example.neosoftstore.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.res.ResourcesCompat
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.neosoftstore.R
import com.example.neosoftstore.adapters.MyOrderAdapter
import com.example.neosoftstore.adapters.MyOrderListener
import com.example.neosoftstore.apis.ApiClient
import com.example.neosoftstore.apis.SharedPrefManagerForUserLogReg
import com.example.neosoftstore.models.MyOrderData
import com.example.neosoftstore.models.MyOrderResponseModel
import kotlinx.android.synthetic.main.fragment_my_order.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MyOrderFragment : Fragment(),MyOrderListener {
    lateinit var orderList : MutableList<MyOrderData>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_my_order, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val toolbar = activity?.findViewById<androidx.appcompat.widget.Toolbar>(R.id.homeScreenToolbar)
        val textViewToolbar = toolbar?.findViewById<TextView>(R.id.toolbarTitle)
        textViewToolbar!!.apply {
            text = "My Orders"
            typeface = ResourcesCompat.getFont(context, R.font.gotham_medium)
            textSize = 25F
        }

    }

    override fun onStart() {
        super.onStart()
        fetchOrders()
    }
    private fun fetchOrders(){
        val accessKey = SharedPrefManagerForUserLogReg.getInstance(requireContext()).accessKey
        val call = ApiClient.orderClient.getOrderList(accessKey!!)
        call.enqueue(object : Callback<MyOrderResponseModel>{
            override fun onResponse(
                call: Call<MyOrderResponseModel>,
                response: Response<MyOrderResponseModel>
            ) {
                if(response.body()!!.status == 200){
                    orderList = mutableListOf()
                    for(item in response.body()!!.data.indices.reversed()){
                        orderList.add(response.body()!!.data[item])
                    }
                    if(orderList.isEmpty()){
                        tvNoOrder.visibility = View.VISIBLE
                    }
                    val adapter = MyOrderAdapter(requireContext(),orderList,this@MyOrderFragment)
                    rvMyOrder.adapter = adapter
                    rvMyOrder.layoutManager = LinearLayoutManager(requireContext())
                }
                else{
                    Toast.makeText(requireContext(), response.body()!!.user_msg, Toast.LENGTH_LONG).show()
                }
            }

            override fun onFailure(call: Call<MyOrderResponseModel>, t: Throwable) {
                Toast.makeText(requireContext(), t.message, Toast.LENGTH_LONG).show()
            }

        })
    }

    override fun orderItemClicked(order_id: Int) {
        val bundle = Bundle()
        bundle.putInt("Order_id",order_id)
        findNavController().navigate(R.id.action_myOrderFragment_to_orderDetailsScreen,bundle)

    }

}
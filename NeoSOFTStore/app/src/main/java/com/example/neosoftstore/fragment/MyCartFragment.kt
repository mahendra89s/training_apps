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
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.neosoftstore.HomeScreenActivity
import com.example.neosoftstore.R
import com.example.neosoftstore.adapters.CartListAdapter
import com.example.neosoftstore.adapters.CartListener
import com.example.neosoftstore.adapters.SwipeToDeleteCallback
import com.example.neosoftstore.apis.ApiClient
import com.example.neosoftstore.apis.SharedPrefManagerForUserLogReg
import com.example.neosoftstore.models.AddDelEditCartResponseModel
import com.example.neosoftstore.models.ListCartData
import com.example.neosoftstore.models.ListCartResponseModel
import kotlinx.android.synthetic.main.fragment_my_cart.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MyCartFragment : Fragment(), CartListener {
    private var accessKey : String? = null
    lateinit var adapter : CartListAdapter
    lateinit var cartItemList : MutableList<ListCartData>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_my_cart, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val toolbar = activity?.findViewById<androidx.appcompat.widget.Toolbar>(R.id.homeScreenToolbar)
        val textViewToolbar = toolbar?.findViewById<TextView>(R.id.toolbarTitle)
        textViewToolbar!!.apply {
            text = "My Cart"
            typeface = ResourcesCompat.getFont(context, R.font.gotham_medium)
            textSize = 25F
        }

        btnOrderNow.setOnClickListener {
            if(::cartItemList.isInitialized){
                findNavController().navigate(R.id.action_myCartFragment_to_listAddressFragment)
            }
            else{
                Toast.makeText(requireContext(),"No Items in Cart!!",Toast.LENGTH_LONG).show()
            }
        }
    }

    override fun onStart() {
        super.onStart()

        fetchCart()
    }
    private fun fetchCart(){
        accessKey = SharedPrefManagerForUserLogReg.getInstance(requireContext()).accessKey
        val call = ApiClient.cartClient.getCartList(accessKey!!)
        call.enqueue(object : Callback<ListCartResponseModel>{
            override fun onResponse(
                call: Call<ListCartResponseModel>,
                response: Response<ListCartResponseModel>
            ) {
                if(response.body()?.status == 200 && !response.body()!!.data.isNullOrEmpty()){
                    val responseData = response.body()
                    tvTotalPriceMyCart.text = String.format(requireContext().getString(R.string.priceMyCart),responseData?.total.toString())
                    cartItemList = mutableListOf()
                    for(item in responseData?.data!!){
                        cartItemList.add(item)
                    }

                    adapter = CartListAdapter(cartItemList,requireContext(),this@MyCartFragment)
                    rvMyCart.adapter = adapter
                    rvMyCart.layoutManager = LinearLayoutManager(requireContext())
                    val swipeDelete = object : SwipeToDeleteCallback(requireContext()){
                        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                            adapter.deleteItem(viewHolder.adapterPosition)
                        }
                    }
                    val touchHelper = ItemTouchHelper(swipeDelete)
                    touchHelper.attachToRecyclerView(rvMyCart)
                }
            }

            override fun onFailure(call: Call<ListCartResponseModel>, t: Throwable) {
                Toast.makeText(requireContext(), t.message, Toast.LENGTH_LONG).show()
            }

        })
    }
    override fun deleteItem(productId: Int) {
        accessKey = SharedPrefManagerForUserLogReg.getInstance(requireContext()).accessKey
        val call = ApiClient.cartClient.deleteCart(accessKey!!,productId)
        call.enqueue(object : Callback<AddDelEditCartResponseModel>{
            override fun onResponse(
                call: Call<AddDelEditCartResponseModel>,
                response: Response<AddDelEditCartResponseModel>
            ) {
                if(response.body()?.status == 200){
                    Toast.makeText(requireContext(),response.body()!!.message, Toast.LENGTH_LONG).show()
                    fetchCart()
                    (activity as HomeScreenActivity).refreshData()
                }
                else {
                    Toast.makeText(requireContext(),response.body()!!.message, Toast.LENGTH_LONG).show()
                }
            }

            override fun onFailure(call: Call<AddDelEditCartResponseModel>, t: Throwable) {
                Toast.makeText(requireContext(),t.message, Toast.LENGTH_LONG).show()
            }

        })
    }

    override fun editItem(productId: Int,quantity:Int) {
        accessKey = SharedPrefManagerForUserLogReg.getInstance(requireContext()).accessKey
        val call = ApiClient.cartClient.editCart(accessKey!!,productId,quantity)
        call.enqueue(object : Callback<AddDelEditCartResponseModel>{
            override fun onResponse(
                call: Call<AddDelEditCartResponseModel>,
                response: Response<AddDelEditCartResponseModel>
            ) {
                if(response.body()?.status == 200){
                    Toast.makeText(requireContext(),response.body()!!.message, Toast.LENGTH_LONG).show()
                    fetchCart()
                }
                else {
                    Toast.makeText(requireContext(),response.body()!!.message, Toast.LENGTH_LONG).show()
                }
            }

            override fun onFailure(call: Call<AddDelEditCartResponseModel>, t: Throwable) {
                Toast.makeText(requireContext(),t.message, Toast.LENGTH_LONG).show()
            }

        })
    }
}
package com.example.neosoftstore.fragment

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.res.ResourcesCompat
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.neosoftstore.R
import com.example.neosoftstore.adapters.AddressListAdapter
import com.example.neosoftstore.adapters.AddressListener
import com.example.neosoftstore.apis.ApiClient
import com.example.neosoftstore.apis.SharedPrefManagerForUserLogReg
import com.example.neosoftstore.models.AddressModel
import com.example.neosoftstore.models.PlaceOrderResponseModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.fragment_list_address.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ListAddressFragment : Fragment(),AddressListener {
    lateinit var sharedPref : SharedPreferences
    lateinit var editor : SharedPreferences.Editor
    lateinit var addressList: MutableList<AddressModel>
    var selectedAddress : String? = null
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_list_address, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val toolbar = activity?.findViewById<androidx.appcompat.widget.Toolbar>(R.id.homeScreenToolbar)
        val textViewToolbar = toolbar?.findViewById<TextView>(R.id.toolbarTitle)
        textViewToolbar!!.apply {
            text = "Address List"
            typeface = ResourcesCompat.getFont(context, R.font.gotham_medium)
            textSize = 25F
        }
        val gson = Gson()
        sharedPref = requireContext().getSharedPreferences("address_list", Context.MODE_PRIVATE)

        val json: String? = sharedPref.getString("address","")
        if(!json.isNullOrEmpty()){
            val type = object : TypeToken<List<AddressModel>?>() {}.type
            addressList = gson.fromJson(json,type)
        }else{
            addressList = mutableListOf()
        }
        val userName = sharedPref.getString("userName","")
        val adapter = AddressListAdapter(addressList,userName!!,requireContext(),this@ListAddressFragment)
        rvAddressList.adapter = adapter
        rvAddressList.layoutManager = LinearLayoutManager(requireContext())

        btnPlaceOrder.setOnClickListener {

            if(!selectedAddress.isNullOrEmpty()){
                placeOrder()
            }
            else{
                Toast.makeText(requireContext(),"Please Select Address!!",Toast.LENGTH_LONG).show()
            }

        }
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        menu.findItem(R.id.searchBar).isVisible = false
        super.onPrepareOptionsMenu(menu)

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.list_address_menu,menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun deleteAddress(position: Int) {
        val gson = Gson()
        sharedPref = requireContext().getSharedPreferences("address_list", Context.MODE_PRIVATE)
        val json: String? = sharedPref.getString("address","")
        val type = object : TypeToken<List<AddressModel>?>() {}.type
        val addressList1 : MutableList<AddressModel> = gson.fromJson(json,type)
        addressList1.removeAt(position)
        val json1 = gson.toJson(addressList1)
        editor = sharedPref.edit()
        editor.putString("address", json1).apply()

    }

    override fun getAddress(address : String) {
        selectedAddress = address
    }

    private fun placeOrder(){
        val accessKey = SharedPrefManagerForUserLogReg.getInstance(requireContext()).accessKey
        val call = ApiClient.orderClient.placeOrder(accessKey!!,selectedAddress!!)
        call.enqueue(object : Callback<PlaceOrderResponseModel>{
            override fun onResponse(
                call: Call<PlaceOrderResponseModel>,
                response: Response<PlaceOrderResponseModel>
            ) {
                if(response.body()!!.status == 200){
                    Toast.makeText(requireContext(),response.body()!!.user_msg,Toast.LENGTH_LONG).show()
                    findNavController().navigate(R.id.action_listAddressFragment_to_myOrderFragment)
                }
                else{
                    Toast.makeText(requireContext(),response.body()!!.user_msg,Toast.LENGTH_LONG).show()
                }
            }

            override fun onFailure(call: Call<PlaceOrderResponseModel>, t: Throwable) {
                Toast.makeText(requireContext(),t.message,Toast.LENGTH_LONG).show()
            }

        })
    }

}
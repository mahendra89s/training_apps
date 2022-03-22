package com.example.neosoftstore.fragment

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.EditText
import androidx.fragment.app.Fragment
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.navigation.fragment.findNavController
import com.example.neosoftstore.R
import com.example.neosoftstore.models.AddressModel
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.fragment_add_address.*


class AddAddressFragment : Fragment() {
    lateinit var sharedPref : SharedPreferences
    lateinit var editor : SharedPreferences.Editor
    lateinit var addressList: MutableList<AddressModel>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_address, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val toolbar = activity?.findViewById<androidx.appcompat.widget.Toolbar>(R.id.homeScreenToolbar)
        val textViewToolbar = toolbar?.findViewById<TextView>(R.id.toolbarTitle)
        textViewToolbar!!.apply {
            text = "Add Address"
            typeface = ResourcesCompat.getFont(context, R.font.gotham_medium)
            textSize = 25F
        }
        sharedPref = requireContext().getSharedPreferences("address_list",Context.MODE_PRIVATE)
        val addedAddress = sharedPref.getString("address","")
        Log.d("address",addedAddress.toString())


        btnAddAddress.setOnClickListener {
            submitAddress()
        }
    }
    private fun submitAddress(){
        if(!countryValidator() or !stateValidator() or !zipCodeValidator() or !cityValidator()
            or !landmarkValidator() or ! addressValidator()){
            return
        }
        val address : String = edtAddressAddScreen.text.toString()
        val landmark : String = edtLandmarkAddScreen.text.toString()
        val city : String = edtCityAddScreen.text.toString()
        val zipcode : String = edtZipAddScreen.text.toString()
        val state : String = edtStateAddScreen.text.toString()
        val country : String = edtCountryAddScreen.text.toString()

//        val addressList : AddressModel = AddressModel(address,landmark, city, zipcode, state, country)
        val addedAddress = sharedPref.getString("address","")
        Log.d("address",addedAddress.toString())
        val gson = Gson()
        editor = sharedPref.edit()
        val type = object : TypeToken<List<AddressModel?>?>() {}.type
        if(addedAddress == ""){
            addressList = mutableListOf()
            addressList.add(AddressModel(address,landmark, city, zipcode, state, country))
        }
        else{
            addressList = gson.fromJson(addedAddress,type)
            addressList.add(AddressModel(address,landmark, city, zipcode, state, country))
        }
        val json = gson.toJson(addressList)
        editor.putString("address", json).apply()
        Toast.makeText(requireContext(),"Address Added Sucessfully!!",Toast.LENGTH_LONG).show()
        findNavController().navigateUp()
    }
    private fun addressValidator():Boolean{
        if(edtAddressAddScreen.text.toString().isNullOrEmpty()){
            showError("This field is required!!",edtAddressAddScreen)
            return false
        }

        return true
    }
    private fun landmarkValidator():Boolean{
        if(edtLandmarkAddScreen.text.toString().isNullOrEmpty()){
            showError("This field is required!!",edtLandmarkAddScreen)
            return false
        }
        return true
    }
    private fun cityValidator():Boolean{
        if(edtCityAddScreen.text.toString().isNullOrEmpty()){
            showError("This field is required!!",edtCityAddScreen)
            return false
        }
        return true
    }
    private fun zipCodeValidator():Boolean{
        if(edtZipAddScreen.text.toString().isNullOrEmpty()){
            showError("This field is required!!",edtZipAddScreen)
            return false
        }
        return true
    }
    private fun stateValidator():Boolean{
        if(edtStateAddScreen.text.toString().isNullOrEmpty()){
            showError("This field is required!!",edtStateAddScreen)
            return false
        }
        return true
    }
    private fun countryValidator():Boolean{
        if(edtCountryAddScreen.text.toString().isNullOrEmpty()){
            showError("This field is required!!",edtCountryAddScreen)
            return false
        }
        return true
    }
    @SuppressLint("UseCompatLoadingForDrawables")
    private fun showError(msg : String, editText: EditText){
        editText.setError(
            msg,
            resources.getDrawable(R.drawable.ic_error_icon).apply {
                setBounds(-10,0,60,60)
            }
        )
    }
}
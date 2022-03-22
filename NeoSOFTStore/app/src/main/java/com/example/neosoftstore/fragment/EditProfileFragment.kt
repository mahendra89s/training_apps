package com.example.neosoftstore.fragment

import android.annotation.SuppressLint
import android.app.Activity
import android.app.DatePickerDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.text.Editable
import android.util.Base64
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.res.ResourcesCompat
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.neosoftstore.HomeScreenActivity

import com.example.neosoftstore.R
import com.example.neosoftstore.apis.ApiClient
import com.example.neosoftstore.apis.SharedPrefManagerForUserLogReg
import com.example.neosoftstore.models.EditProfileResponse
import com.example.neosoftstore.models.EditUserModel
import com.theartofdev.edmodo.cropper.CropImage

import kotlinx.android.synthetic.main.fragment_edit_profile.*

import java.util.*
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

import java.io.ByteArrayOutputStream
import java.io.IOException
import java.io.InputStream







const val REQUEST_CODE_FOR_IMAGE_PICKER = 1
const val STORAGE_PERMISSION_REQUEST_CODE = 2
class EditProfileFragment : Fragment() {

    private var date : Int? = null
    private var month : Int? = null
    private var year : Int? = null
    lateinit var calendar: Calendar
    lateinit var builder : MultipartBody.Builder
    lateinit var requestBody : RequestBody
    private var base64Image : String? = null
    var accessKey: String? = null
    private var imageString :String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_edit_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val toolbar = activity?.findViewById<androidx.appcompat.widget.Toolbar>(R.id.homeScreenToolbar)
        val textViewToolbar = toolbar?.findViewById<TextView>(R.id.toolbarTitle)
        textViewToolbar!!.apply {
            text = "Edit Account"
            typeface = ResourcesCompat.getFont(context, R.font.gotham_medium)
            textSize = 25F
        }
        val userdata = arguments?.getParcelable<EditUserModel>("userData")
        Log.d("TAG",userdata.toString())


        if(userdata != null){
            edtFirstNameEdtProf.text = Editable.Factory.getInstance().newEditable(userdata.first_name)
            edtLastNameEdtProf.text = Editable.Factory.getInstance().newEditable(userdata.last_name)
            edtEmailEdtProf.text = Editable.Factory.getInstance().newEditable(userdata.email)
            edtPhoneEdtProf.text = Editable.Factory.getInstance().newEditable(userdata.phone_no)
            if(userdata.dob != null){
                edtDobEdtProf.text = userdata.dob
            }
            if(userdata.profile_pic != null){
                Glide.with(requireContext()).load(userdata.profile_pic).into(ivProfileEdtProf)
//                base64PhotoForUpload(Uri.parse(userdata.profile_pic))
            }
            calendar = Calendar.getInstance()
            date = calendar.get(Calendar.DAY_OF_MONTH)
            month = calendar.get(Calendar.MONTH)
            year = calendar.get(Calendar.YEAR)

            edtDobEdtProf.setOnClickListener {
                DatePickerDialog(requireContext(),
                    R.style.datePickerTheme,
                    { view, year, monthOfYear, dayOfMonth ->
                        calendar.set(Calendar.YEAR, year)
                        calendar.set(Calendar.MONTH, monthOfYear)
                        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                        val month = if(monthOfYear < 9){
                            "0${monthOfYear+1}"
                        }else{
                            monthOfYear+1
                        }
                        val day = if(dayOfMonth<10){
                            "0${dayOfMonth}"
                        }
                        else{
                            dayOfMonth
                        }
                        edtDobEdtProf.text = "$day-${month}-$year"
                    },year!!,month!!,date!!).show()
            }

            btnSubmitDetails.setOnClickListener {
                submitEditInfo()
            }
            ivProfileEdtProf.setOnClickListener {
                pickImageFromStorage()
            }
        }
    }
    private fun submitEditInfo(){
        if(!dobValidator() or !phoneValidator() or !emailValidator() or !lastNameValidator() or !firstNameValidator() or !profileValidator()){
            return
        }
        val firstName = edtFirstNameEdtProf.text.toString()
        val lastName = edtLastNameEdtProf.text.toString()
        val email = edtEmailEdtProf.text.toString()
        val phone_no = edtPhoneEdtProf.text.toString()
        val dob = edtDobEdtProf.text.toString()
        val profile : String = imageString!!
        Log.d("First Name",firstName)
        Log.d("Last Name",lastName)
        Log.d("Email",email)
        Log.d("phone no",phone_no)
        Log.d("dob",dob)
        Log.d("profile",imageString!!)

        accessKey = SharedPrefManagerForUserLogReg.getInstance(requireContext()).accessKey
        val call = ApiClient.getUserInfoClient.editUserDetails(accessKey!!,firstName,lastName,email,dob,profile,phone_no)
        call.enqueue(object : Callback<EditProfileResponse>{
            override fun onResponse(
                call: Call<EditProfileResponse>,
                response: Response<EditProfileResponse>
            ) {
                if(response.body()!!.status == 200){
                   Toast.makeText(requireContext(),response.body()?.message,Toast.LENGTH_LONG).show()
                    findNavController().navigateUp()
                    (activity as HomeScreenActivity).refreshData()
                }
                else{
                    Toast.makeText(requireContext(),response.body()?.message.toString(), Toast.LENGTH_LONG).show()
                }
            }

            override fun onFailure(call: Call<EditProfileResponse>, t: Throwable) {
                Toast.makeText(requireContext(),t.message,Toast.LENGTH_LONG).show()
            }

        })
    }
    private fun firstNameValidator(): Boolean{
        return if(edtFirstNameEdtProf.text.toString() == ""){
            showError("This Field is required!!",edtFirstNameEdtProf)
            false
        } else {
            true
        }
    }
    private fun lastNameValidator(): Boolean{
        return if(edtLastNameEdtProf.text.toString() == ""){
            showError("This Field is required!!",edtLastNameEdtProf)
            false
        } else {
            true
        }
    }
    private fun emailValidator():Boolean{
        val emailPattern = Regex("[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+")
        return when {
            edtEmailEdtProf.text.toString() == "" -> {
                showError("This Field is required!!",edtEmailEdtProf)
                false
            }
            !edtEmailEdtProf.text.toString().matches(emailPattern) -> {
                showError("Invalid Email!!",edtEmailEdtProf)
                false
            }
            else -> {
                true
            }
        }
    }

    private fun phoneValidator() : Boolean {
        return when{
            edtPhoneEdtProf.text.toString() == "" -> {
                showError("This Field is required!!",edtPhoneEdtProf)
                false
            }
            edtPhoneEdtProf.text.toString().length >10 || edtPhoneEdtProf.text.toString().length < 10 -> {
                showError("Invalid Phone Number!!",edtPhoneEdtProf)
                false
            }
            edtPhoneEdtProf.text.toString().length == 10 -> {
                true
            }
            else -> false
        }
    }
    private fun dobValidator() : Boolean{
        return if(edtDobEdtProf.text.toString() == ""){
            Toast.makeText(context,"DOB is required",Toast.LENGTH_LONG).show()
            false
        }
        else{
            true
        }
    }
    private fun profileValidator() : Boolean{
        return base64Image != null
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
    private fun pickImageFromStorage(){
        if(ActivityCompat.checkSelfPermission(requireContext(),android.Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
            && ActivityCompat.checkSelfPermission(requireContext(),android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
        ){
            askPermission()
        }
        else{
            Intent(Intent.ACTION_PICK).also{
                it.type = "image/*"
                startActivityForResult(it,REQUEST_CODE_FOR_IMAGE_PICKER)
            }

        }
    }
    private fun base64PhotoForUpload(data: Uri?) : Boolean {
        val iStream: InputStream? = requireActivity().contentResolver.openInputStream(data!!)
        val inputData = getBytes(iStream!!)
        base64Image = Base64.encodeToString(inputData,Base64.DEFAULT)
        imageString = "data:"+ requireActivity().contentResolver.getType(data)+";base64,"+base64Image
        Glide.with(requireContext()).load(data).into(ivProfileEdtProf)
        return true
    }

    @Throws(IOException::class)
    fun getBytes(inputStream: InputStream): ByteArray? {
        val byteBuffer = ByteArrayOutputStream()
        val bufferSize = 1024
        val buffer = ByteArray(bufferSize)
        var len = 0
        while (inputStream.read(buffer).also { len = it } != -1) {
            byteBuffer.write(buffer, 0, len)
        }
        return byteBuffer.toByteArray()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == REQUEST_CODE_FOR_IMAGE_PICKER && resultCode == Activity.RESULT_OK){
            base64PhotoForUpload(data?.data)
        }
    }


    private fun askPermission(){
        if(ActivityCompat.checkSelfPermission(requireContext(),android.Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
            && ActivityCompat.checkSelfPermission(requireContext(),android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
        ){
            if(ActivityCompat.shouldShowRequestPermissionRationale(requireActivity(), android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                && ActivityCompat.shouldShowRequestPermissionRationale(requireActivity(), android.Manifest.permission.READ_EXTERNAL_STORAGE)
            ){
                ActivityCompat.requestPermissions(
                    requireActivity(),
                    mutableListOf(
                        android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        android.Manifest.permission.READ_EXTERNAL_STORAGE
                    ).toTypedArray(),
                    STORAGE_PERMISSION_REQUEST_CODE
                )
            }else{
                ActivityCompat.requestPermissions(
                    requireActivity(),
                    mutableListOf(
                        android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        android.Manifest.permission.READ_EXTERNAL_STORAGE
                    ).toTypedArray(),
                    STORAGE_PERMISSION_REQUEST_CODE
                )
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(grantResults.isNotEmpty() && requestCode == STORAGE_PERMISSION_REQUEST_CODE){
            for( i in grantResults.indices){
                if(grantResults[i] != PackageManager.PERMISSION_GRANTED){
                    askPermission()
                }
            }
        }

    }
}
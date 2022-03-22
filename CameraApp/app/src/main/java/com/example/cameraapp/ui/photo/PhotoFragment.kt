package com.example.cameraapp.ui.photo

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.hardware.camera2.CameraManager
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil.setContentView
import com.example.cameraapp.R
import com.example.cameraapp.databinding.ActivityMainBinding
import com.example.cameraapp.databinding.FragmentPhotoBinding
import com.example.cameraapp.ui.gallery.ViewPhotosActivity
import com.example.cameraapp.ui.main.CAMERA_REQUEST_CODE
import kotlinx.android.synthetic.main.fragment_photo.*
import java.io.File
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*



const val CAMERA_REQUEST_CODE = 101
class PhotoFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var binding : FragmentPhotoBinding
    private var imageCapture : ImageCapture? = null
    private lateinit var outputDirectory : File
    lateinit var cameraSelector: CameraSelector
    var flashMode : Int = ImageCapture.FLASH_MODE_OFF
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        arguments?.let {
//
//        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentPhotoBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        outputDirectory = getOutputDirectory()
        cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA
        btnFlashOff.background = ContextCompat.getDrawable(requireContext(),R.drawable.flash_icon_click)
        if(ContextCompat.checkSelfPermission(requireContext(),Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED
            && ContextCompat.checkSelfPermission(requireContext(),Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED
        ){
            checkPermission()
        }
        else{
            startCamera()
        }
        binding.btnCapture.setOnClickListener {
            takePhoto()
        }
        binding.btnSwitch.setOnClickListener {
            switchCamera()
        }
        binding.btnView.setOnClickListener {
            Intent(context, ViewPhotosActivity::class.java).also {
                startActivity(it)
            }
        }
        binding.btnFlashAuto.setOnClickListener {
            flash(ImageCapture.FLASH_MODE_AUTO)

        }
        binding.btnFlashOff.setOnClickListener {
            flash(ImageCapture.FLASH_MODE_OFF)
        }
        binding.btnFlashOn.setOnClickListener {
            flash(ImageCapture.FLASH_MODE_ON)
        }
//        val cameraManager = context.getSystemService(Context.CAMERA_SERVICE) as CameraManager
//        val cameras = cameraManager.cameraIdList
//        Log.d("camera",cameras.toString())

    }
    private fun flash(flashmode : Int){
        btnFlashOff.background = ContextCompat.getDrawable(requireContext(), R.color.white)
        btnFlashOn.background = ContextCompat.getDrawable(requireContext(),R.color.white)
        btnFlashAuto.background = ContextCompat.getDrawable(requireContext(),R.color.white)
        when(flashmode){
            ImageCapture.FLASH_MODE_OFF ->{
                flashMode = ImageCapture.FLASH_MODE_OFF
                btnFlashOff.background = ContextCompat.getDrawable(requireContext(),R.drawable.flash_icon_click)
            }
            ImageCapture.FLASH_MODE_ON ->{
                flashMode = ImageCapture.FLASH_MODE_ON
                btnFlashOn.background = ContextCompat.getDrawable(requireContext(),R.drawable.flash_icon_click)
            }
            ImageCapture.FLASH_MODE_AUTO ->{
                flashMode = ImageCapture.FLASH_MODE_AUTO
                btnFlashAuto.background = ContextCompat.getDrawable(requireContext(),R.drawable.flash_icon_click)
            }
        }
        startCamera()
    }

    private fun getOutputDirectory() : File {
        val mediaDir = activity?.externalMediaDirs?.firstOrNull()?.let{
            File(it,resources.getString(R.string.app_name)).apply {
                mkdirs()
            }
        }
        return if(mediaDir != null && mediaDir.exists())
            mediaDir else activity?.filesDir!!
    }
    private fun startCamera(){
        val cameraProviderFuture = ProcessCameraProvider.getInstance(requireContext())
        cameraProviderFuture.addListener({
            val cameraProvider = cameraProviderFuture.get()
            val preview = Preview.Builder()
                .build()
                .also {
                    it.setSurfaceProvider(binding.cameraPreview.surfaceProvider)

                }
            imageCapture = ImageCapture.Builder().setFlashMode(flashMode).build()


            try{
                cameraProvider.unbindAll()
                val cam = cameraProvider.bindToLifecycle(this,cameraSelector,preview,imageCapture)
//                if(flashMode == ImageCapture.FLASH_MODE_ON){
//                    cam.cameraControl.enableTorch(true)
//                }
            }catch (e : Exception){
                Log.d("Start Faied" , e.message.toString())
            }
        }, ContextCompat.getMainExecutor(requireContext()))
    }

    private fun takePhoto(){
        val imageCapture = imageCapture ?: return
        val photoFile = File(
            outputDirectory,
            SimpleDateFormat("yy-MM-dd-HH-mm-ss-SSS",
                Locale.getDefault()).format(System.currentTimeMillis())+ ".jpg")

        val outputOption = ImageCapture.OutputFileOptions.Builder(photoFile).build()
        imageCapture.takePicture(
            outputOption, ContextCompat.getMainExecutor(requireContext()),
            object : ImageCapture.OnImageSavedCallback{
                override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
                    val savedUri = Uri.fromFile(photoFile)
                    val msg = "Photo Saved"
                    Toast.makeText(context,"$msg $photoFile", Toast.LENGTH_LONG).show()
                }

                override fun onError(exception: ImageCaptureException) {
                    Log.e("Error",exception.message.toString())
                }

            }
        )
    }
    private fun switchCamera(){
        cameraSelector = if(cameraSelector == CameraSelector.DEFAULT_BACK_CAMERA)
            CameraSelector.DEFAULT_FRONT_CAMERA
        else CameraSelector.DEFAULT_BACK_CAMERA
        startCamera()
    }

    private fun checkPermission(){
        if(ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.CAMERA) !=
            PackageManager.PERMISSION_GRANTED
            &&
            ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.RECORD_AUDIO) !=
            PackageManager.PERMISSION_GRANTED
        ){
            if(ActivityCompat.shouldShowRequestPermissionRationale(requireActivity(), Manifest.permission.CAMERA)
                &&
                ActivityCompat.shouldShowRequestPermissionRationale(requireActivity(), Manifest.permission.RECORD_AUDIO)){
                ActivityCompat.requestPermissions(
                    requireActivity(),
                    mutableListOf(Manifest.permission.CAMERA,Manifest.permission.RECORD_AUDIO).toTypedArray(),
                    CAMERA_REQUEST_CODE
                )
            }
            else{
                ActivityCompat.requestPermissions(
                    requireActivity(),
                    mutableListOf(Manifest.permission.CAMERA,Manifest.permission.RECORD_AUDIO).toTypedArray(),
                    CAMERA_REQUEST_CODE
                )
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(grantResults.isNotEmpty() && requestCode == CAMERA_REQUEST_CODE){
            for(i in grantResults){
                if(grantResults[i] != PackageManager.PERMISSION_GRANTED){
                    checkPermission()
                }
                else{
                    startCamera()
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        btnFlashOn.background = ContextCompat.getDrawable(requireContext(),R.color.white)
        btnFlashAuto.background = ContextCompat.getDrawable(requireContext(),R.color.white)
        btnFlashOff.background = ContextCompat.getDrawable(requireContext(),R.drawable.flash_icon_click)
        flashMode = ImageCapture.FLASH_MODE_OFF
        startCamera()
    }


//    companion object {
//
//        @JvmStatic
//        fun newInstance(param1: String, param2: String) =
//            PhotoFragment().apply {
//                arguments = Bundle().apply {
//
//                }
//            }
//    }
}
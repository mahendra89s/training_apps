package com.example.cameraapp.ui.main

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.hardware.camera2.CameraManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.TextView
import android.widget.Toast
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.add
import androidx.fragment.app.commit
import com.example.cameraapp.R
import com.example.cameraapp.databinding.ActivityMainBinding
import com.example.cameraapp.ui.gallery.ViewPhotosActivity
import com.example.cameraapp.ui.photo.PhotoFragment
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.activity_main.*
import java.io.File
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*

const val CAMERA_REQUEST_CODE = 101
class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding
//    private var imageCapture : ImageCapture? = null
//    private lateinit var outputDirectory : File
//    lateinit var cameraSelector: CameraSelector
//    var flashMode : Int = ImageCapture.FLASH_MODE_OFF
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        supportFragmentManager.commit {
            setReorderingAllowed(true)
            add<MainFragment>(R.id.fragment_container)
        }
        setContentView(binding.root)


//        outputDirectory = getOutputDirectory()
//        cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA
//        btnFlashOff.background = ContextCompat.getDrawable(this,R.drawable.flash_icon_click)
//        if(ContextCompat.checkSelfPermission(this,Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){
//            checkPermission()
//        }
//        else{
//            startCamera()
//        }
//        binding.btnCapture.setOnClickListener {
//            takePhoto()
//        }
//        binding.btnSwitch.setOnClickListener {
//            switchCamera()
//        }
//        binding.btnView.setOnClickListener {
//            Intent(this, ViewPhotosActivity::class.java).also {
//                startActivity(it)
//            }
//        }
//        binding.btnFlashAuto.setOnClickListener {
//            flash(ImageCapture.FLASH_MODE_AUTO)
//
//        }
//        binding.btnFlashOff.setOnClickListener {
//            flash(ImageCapture.FLASH_MODE_OFF)
//        }
//        binding.btnFlashOn.setOnClickListener {
//            flash(ImageCapture.FLASH_MODE_ON)
//        }
//        val cameraManager = this.getSystemService(Context.CAMERA_SERVICE) as CameraManager
//        val cameras = cameraManager.cameraIdList
//        Log.d("camera",cameras.toString())

    }


//    private fun flash(flashmode : Int){
//        btnFlashOff.background = ContextCompat.getDrawable(this, R.color.white)
//        btnFlashOn.background = ContextCompat.getDrawable(this,R.color.white)
//        btnFlashAuto.background = ContextCompat.getDrawable(this,R.color.white)
//        when(flashmode){
//            ImageCapture.FLASH_MODE_OFF ->{
//                flashMode = ImageCapture.FLASH_MODE_OFF
//                btnFlashOff.background = ContextCompat.getDrawable(this,R.drawable.flash_icon_click)
//            }
//            ImageCapture.FLASH_MODE_ON ->{
//                flashMode = ImageCapture.FLASH_MODE_ON
//                btnFlashOn.background = ContextCompat.getDrawable(this,R.drawable.flash_icon_click)
//            }
//            ImageCapture.FLASH_MODE_AUTO ->{
//                flashMode = ImageCapture.FLASH_MODE_AUTO
//                btnFlashAuto.background = ContextCompat.getDrawable(this,R.drawable.flash_icon_click)
//            }
//        }
//        startCamera()
//    }
//
//    private fun getOutputDirectory() : File{
//        val mediaDir = externalMediaDirs.firstOrNull()?.let{
//            File(it,resources.getString(R.string.app_name)).apply {
//                mkdirs()
//            }
//        }
//        return if(mediaDir != null && mediaDir.exists())
//            mediaDir else filesDir
//    }
//    private fun startCamera(){
//        val cameraProviderFuture = ProcessCameraProvider.getInstance(this)
//        cameraProviderFuture.addListener({
//            val cameraProvider = cameraProviderFuture.get()
//            val preview = Preview.Builder()
//                .build()
//                .also {
//                    it.setSurfaceProvider(binding.cameraPreview.surfaceProvider)
//
//                }
//            imageCapture = ImageCapture.Builder().setFlashMode(flashMode).build()
//
//
//            try{
//                cameraProvider.unbindAll()
//                val cam = cameraProvider.bindToLifecycle(this,cameraSelector,preview,imageCapture)
////                if(flashMode == ImageCapture.FLASH_MODE_ON){
////                    cam.cameraControl.enableTorch(true)
////                }
//            }catch (e : Exception){
//                Log.d("Start Faied" , e.message.toString())
//            }
//        },ContextCompat.getMainExecutor(this))
//    }
//
//    private fun takePhoto(){
//        val imageCapture = imageCapture ?: return
//        val photoFile = File(
//            outputDirectory,
//            SimpleDateFormat("yy-MM-dd-HH-mm-ss-SSS",
//            Locale.getDefault()).format(System.currentTimeMillis())+ ".jpg")
//
//        val outputOption = ImageCapture.OutputFileOptions.Builder(photoFile).build()
//        imageCapture.takePicture(
//            outputOption,ContextCompat.getMainExecutor(this),
//            object : ImageCapture.OnImageSavedCallback{
//                override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
//                    val savedUri = Uri.fromFile(photoFile)
//                    val msg = "Photo Saved"
//                    Toast.makeText(this@MainActivity,"$msg $photoFile",Toast.LENGTH_LONG).show()
//                }
//
//                override fun onError(exception: ImageCaptureException) {
//                    Log.e("Error",exception.message.toString())
//                }
//
//            }
//        )
//    }
//    private fun switchCamera(){
//        cameraSelector = if(cameraSelector == CameraSelector.DEFAULT_BACK_CAMERA)
//            CameraSelector.DEFAULT_FRONT_CAMERA
//        else CameraSelector.DEFAULT_BACK_CAMERA
//        startCamera()
//    }
//
//    private fun checkPermission(){
//        if(ActivityCompat.checkSelfPermission(this,Manifest.permission.CAMERA) !=
//                PackageManager.PERMISSION_GRANTED){
//            if(ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.CAMERA)){
//                ActivityCompat.requestPermissions(
//                    this,
//                    mutableListOf(Manifest.permission.CAMERA).toTypedArray(),
//                    CAMERA_REQUEST_CODE
//                )
//            }
//            else{
//                ActivityCompat.requestPermissions(
//                    this,
//                    mutableListOf(Manifest.permission.CAMERA).toTypedArray(),
//                    CAMERA_REQUEST_CODE
//                )
//            }
//        }
//    }
//
//    override fun onRequestPermissionsResult(
//        requestCode: Int,
//        permissions: Array<String>,
//        grantResults: IntArray
//    ) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
//        if(grantResults.isNotEmpty() && requestCode == CAMERA_REQUEST_CODE){
//            if(grantResults[0] != PackageManager.PERMISSION_GRANTED){
//                checkPermission()
//            }
//            else{
//                startCamera()
//            }
//        }
//    }
//
//    override fun onResume() {
//        super.onResume()
//        btnFlashOn.background = ContextCompat.getDrawable(this,R.color.white)
//        btnFlashAuto.background = ContextCompat.getDrawable(this,R.color.white)
//        btnFlashOff.background = ContextCompat.getDrawable(this,R.drawable.flash_icon_click)
//        flashMode = ImageCapture.FLASH_MODE_OFF
//        startCamera()
//    }


}
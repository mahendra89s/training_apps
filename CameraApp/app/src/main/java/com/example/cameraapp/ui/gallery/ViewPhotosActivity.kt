package com.example.cameraapp.ui.gallery

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.cameraapp.ui.view.PhotoViewerActivity
import com.example.cameraapp.R
import com.example.cameraapp.databinding.ActivityViewPhotosBinding
import com.google.android.material.bottomsheet.BottomSheetDialog
import kotlinx.android.synthetic.main.activity_view_photos.*
import java.io.File
import kotlinx.android.synthetic.main.popup_layout.view.*






const val READ_EXTERNAL_STORAGE_REQUEST_CODE = 102
class ViewPhotosActivity : AppCompatActivity() , ItemClickListener {
//    lateinit var allPhotos : MutableList<Photo>
    lateinit var directory : File
    lateinit var photos : MutableList<String>
    lateinit var binding : ActivityViewPhotosBinding
    lateinit var adapter : ViewPhotosAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityViewPhotosBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        photos = mutableListOf()
        if(ContextCompat.checkSelfPermission(this,Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            checkPermission()
        }
        else{

            getPhotos()
            adapter = ViewPhotosAdapter(photos,this,this)
            rvPhotos.adapter = adapter
            rvPhotos.layoutManager = StaggeredGridLayoutManager(3,LinearLayoutManager.VERTICAL)
        }

    }


    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
    private fun getOutputDirectory() : File {
        val mediaDir = externalMediaDirs.firstOrNull()?.let{
            File(it,resources.getString(R.string.app_name)).apply {
                mkdirs()
            }
        }
        return if(mediaDir != null && mediaDir.exists())
            mediaDir else filesDir
    }
    private fun getPhotos(){
        directory = getOutputDirectory()
        val files = directory.listFiles()
        for(file in files){
            photos.add(file.toString())
        }
        photos = photos.asReversed()
    }
//    private fun getPhotos(){
//        val projection =  arrayOf(
//            MediaStore.Images.Media._ID,
//            MediaStore.Images.Media.DISPLAY_NAME,
//            MediaStore.Images.Media.DATE_ADDED,
//            MediaStore.Images.Media.SIZE,
//            MediaStore.Images.Media.DATA
//        )
////        val selection = "${MediaStore.Images.Media.DISPLAY_NAME} LIKE"
////        val selection: String = DocumentsContract.Document.COLUMN_MIME_TYPE + " == ?" +
////                " OR " + DocumentsContract.Document.COLUMN_MIME_TYPE + " == ?" +
////                " OR " + DocumentsContract.Document.COLUMN_MIME_TYPE + " == ?" +
////                " OR " + DocumentsContract.Document.COLUMN_MIME_TYPE + " == ?"
////        val selectionArgs = arrayOf(
////                          DocumentsContract.Document.MIME_TYPE_DIR,
////                           MimeTypeMap.getSingleton().getMimeTypeFromExtension ("jpg"),
////                          MimeTypeMap.getSingleton().getMimeTypeFromExtension ("jpeg"),
////                           MimeTypeMap.getSingleton().getMimeTypeFromExtension ("png")
////        )
//        val sortOrder = "${MediaStore.Images.Media.DATE_ADDED} DESC"
//        contentResolver.query(
//            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
//            projection,
//            null,
//            null,
//            sortOrder
//        )?.use {
//            val id = it.getColumnIndexOrThrow(MediaStore.Images.Media._ID)
//            val displayName = it.getColumnIndexOrThrow(MediaStore.Images.Media.DISPLAY_NAME)
//            val date = it.getColumnIndexOrThrow(MediaStore.Images.Media.DATE_ADDED)
//            val size = it.getColumnIndexOrThrow(MediaStore.Images.Media.SIZE)
//            val data = it.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
//            val contentUri: Uri = ContentUris.withAppendedId(
//                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
//                id.toLong()
//            )
//            while(it.moveToNext()){
//                if(it.getString(data).contains("Camera App")){
//                    allPhotos.add(
//                        Photo(
//                            it.getLong(id),
//                            it.getString(displayName),
//                            it.getString(date),
//                            it.getString(size) ?: "0",
//                            it.getString(data),
//                            contentUri
//                        )
//                    )
//                }
//
//            }
//        }
////        Log.d("photos",allPhotos.toString())
//    }
    private fun checkPermission(){
        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) !=
            PackageManager.PERMISSION_GRANTED){
            if(ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE)){
                ActivityCompat.requestPermissions(
                    this,
                    mutableListOf(Manifest.permission.READ_EXTERNAL_STORAGE).toTypedArray(),
                    READ_EXTERNAL_STORAGE_REQUEST_CODE
                )
            }
            else{
                ActivityCompat.requestPermissions(
                    this,
                    mutableListOf(Manifest.permission.READ_EXTERNAL_STORAGE).toTypedArray(),
                    READ_EXTERNAL_STORAGE_REQUEST_CODE
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
        if(grantResults.isNotEmpty() && requestCode == READ_EXTERNAL_STORAGE_REQUEST_CODE){
            if(grantResults[0] != PackageManager.PERMISSION_GRANTED){
                checkPermission()
            }
            else{
                getPhotos()
            }
        }
    }


    @SuppressLint("InflateParams")
    override fun onLongClick(position: Int, view : View) {
        val bottomSheetDialog = BottomSheetDialog(this)
        val view = layoutInflater.inflate(R.layout.popup_layout,null)
        bottomSheetDialog.setContentView(view)

        bottomSheetDialog.setCancelable(true)
        view.open_layout.setOnClickListener {
            android.content.Intent(this, PhotoViewerActivity::class.java).also {
                it.putExtra("path",photos[position])
//                Log.d("path",photos[position])
                startActivity(it)
                bottomSheetDialog.dismiss()
            }
        }
        view.delete_layout.setOnClickListener {
            var file : File = File(photos[position])

            file.delete()
//            Log.d("path",photos.asReversed()[position].toString())
            photos.removeAt(position)
            adapter.notifyDataSetChanged()
            bottomSheetDialog.dismiss()
        }

        bottomSheetDialog.show()
//        val popupMenu = PopupMenu(this,binding.rvPhotos[position])
//
//        popupMenu.inflate(R.menu.option_menu)
//        popupMenu.setOnMenuItemClickListener {
//            when(it.itemId){
//                R.id.open_layout -> {
//                    Toast.makeText(this,"OPEN",Toast.LENGTH_LONG).show()
//                    return@setOnMenuItemClickListener true
//                }
//                R.id.delete_layout -> {
//                    photos.removeAt(position)
//                    adapter.notifyDataSetChanged()
//                    Toast.makeText(this,"DELETE", Toast.LENGTH_LONG).show()
//                    return@setOnMenuItemClickListener true
//                }
//                else -> {
//                    return@setOnMenuItemClickListener false
//                }
//            }
//        }
//        popupMenu.show()
    }

    override fun onClick(position: Int, view: View) {
        android.content.Intent(this, PhotoViewerActivity::class.java).also {
            it.putExtra("path",photos[position])
            startActivity(it)
        }
    }

}
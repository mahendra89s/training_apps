package com.example.filemanager

import android.content.ContentUris
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import java.lang.Exception
import android.provider.MediaStore
import android.util.Log
import android.webkit.MimeTypeMap
import androidx.core.content.FileProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.filemanager.Models.CategoryModel
import java.io.File


class CategoryFilesFragment : Fragment(),FileSelectedListener {
    private var category : String? = null
    private lateinit var rvCategoryList : RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            category = it.getString("category")
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_category_files, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rvCategoryList = view.findViewById(R.id.rvCategoryList)
        when(category){
            "Audio" -> parseAudio()
            "Photos" -> parsePhotos()
            "Video" -> parseVideos()
            "Documents" -> parseDocuments()
            "Downloads" -> parseDownloads()
            "Apk" -> parseApk()
        }
    }
    private fun loadDataToRecyclerView(dataList : MutableList<CategoryModel>,fileType : String){
        val adapter = CategoryFileAdapter(requireContext(), dataList,fileType,this)
        rvCategoryList.adapter = adapter
        rvCategoryList.layoutManager = LinearLayoutManager(requireContext())
    }



    private fun parseAudio(){
        try{
            val audioList : MutableList<CategoryModel> = mutableListOf()
            val selection = MediaStore.Audio.Media.IS_MUSIC + " != 0"

            val projection = arrayOf(
                MediaStore.Audio.Media.DISPLAY_NAME,
//                MediaStore.Audio.Media.ARTIST,
//                MediaStore.Audio.Media.ALBUM,
//                MediaStore.Audio.Media.DURATION,
                MediaStore.Audio.Media.SIZE,
                MediaStore.Audio.Media.DATA,  // filepath of the audio file
                MediaStore.Audio.Media._ID
            )
            val sortOrder = MediaStore.Audio.Media.DATE_ADDED+" DESC"
            requireContext().contentResolver.query(
                MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                projection,
                selection,
                null,
                sortOrder
            )?.use { cursor ->
                    val idColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media._ID)
                    val titleColumn  = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DISPLAY_NAME)
//                    val artistColumn  = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ARTIST)
//                    val albumColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ALBUM)
//                    val durationColumn  = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DURATION)
                    val sizeColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.SIZE)
                    val dataColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA)
                    while (cursor.moveToNext()){
                        val id = cursor.getLong(idColumn)
                        val title = cursor.getString(titleColumn)
//                        val artist = cursor.getString(artistColumn)
//                        val album = cursor.getString(albumColumn)
//                        val duration = cursor.getString(durationColumn)
                        val data = cursor.getString(dataColumn)
                        val size = cursor.getString(sizeColumn)
                        val contentUri: Uri = ContentUris.withAppendedId(
                            MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                            id
                        )
                        audioList.add(CategoryModel(title,data,size,contentUri))
                    }
            }
            loadDataToRecyclerView(audioList, "Audio")
        }
        catch (e : Exception){
            e.printStackTrace()
        }
    }



    private fun parsePhotos(){
        try{
            val imageList : MutableList<CategoryModel> = mutableListOf()
            val projection = arrayOf(
                MediaStore.Images.Media.DISPLAY_NAME,
                MediaStore.Images.Media.SIZE,
                MediaStore.Images.Media.DATA,  // filepath of the audio file
                MediaStore.Images.Media._ID
            )
            val sortOrder = MediaStore.Images.Media.DATE_ADDED+" DESC"
            requireContext().contentResolver.query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                projection,
                null,
                null,
                sortOrder
            )?.use { cursor ->
                val idColumn = cursor.getColumnIndexOrThrow(MediaStore.Images.Media._ID)
                val titleColumn  = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DISPLAY_NAME)
                val sizeColumn = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.SIZE)
                val dataColumn = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
                while (cursor.moveToNext()){
                    val id = cursor.getLong(idColumn)
                    val title = cursor.getString(titleColumn)
                    val data = cursor.getString(dataColumn)
                    val size = cursor.getString(sizeColumn)
                    val contentUri: Uri = ContentUris.withAppendedId(
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                        id
                    )
                    if(size != null){
                        imageList.add(CategoryModel(title,data,size,contentUri))
                    }

                }
            }
            loadDataToRecyclerView(imageList, "Photos")
        }
        catch (e : Exception){
            e.printStackTrace()
        }
    }



    private fun parseVideos(){
        try {
            val videoList : MutableList<CategoryModel> = mutableListOf()
            val projection = arrayOf(
                MediaStore.Video.Media.DISPLAY_NAME,
                MediaStore.Video.Media.SIZE,
                MediaStore.Video.Media.DATA,  // filepath of the audio file
                MediaStore.Video.Media._ID
            )
            val sortOrder = MediaStore.Video.Media.DATE_ADDED+" DESC"
            requireContext().contentResolver.query(
                MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
                projection,
                null,
                null,
                sortOrder
            )?.use { cursor ->
                val idColumn = cursor.getColumnIndexOrThrow(MediaStore.Video.Media._ID)
                val titleColumn  = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DISPLAY_NAME)
                val sizeColumn = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.SIZE)
                val dataColumn = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DATA)
                while (cursor.moveToNext()){
                    val id = cursor.getLong(idColumn)
                    val title = cursor.getString(titleColumn)
                    val data = cursor.getString(dataColumn)
                    val size = cursor.getString(sizeColumn)
                    val contentUri: Uri = ContentUris.withAppendedId(
                        MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
                        id
                    )
                    videoList.add(CategoryModel(title,data,size,contentUri))
                }
            }
            loadDataToRecyclerView(videoList, "Video")
        }
        catch (e:Exception){
            e.printStackTrace()
        }
    }



    private fun parseDownloads(){
        try {
            val downloadList : MutableList<CategoryModel> = mutableListOf()
            val projection = arrayOf(
                MediaStore.Downloads.DISPLAY_NAME,
                MediaStore.Downloads.SIZE,
                MediaStore.Downloads.DATA,  // filepath of the audio file
                MediaStore.Downloads._ID
            )
            val sortOrder = MediaStore.Downloads.DATE_ADDED+" DESC"

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                requireContext().contentResolver.query(
                    MediaStore.Downloads.EXTERNAL_CONTENT_URI,
                    projection,
                    null,
                    null,
                    sortOrder
                )?.use { cursor ->
                    val idColumn = cursor.getColumnIndexOrThrow(MediaStore.Downloads._ID)
                    val titleColumn  = cursor.getColumnIndexOrThrow(MediaStore.Downloads.DISPLAY_NAME)
                    val sizeColumn = cursor.getColumnIndexOrThrow(MediaStore.Downloads.SIZE)
                    val dataColumn = cursor.getColumnIndexOrThrow(MediaStore.Downloads.DATA)
                    while (cursor.moveToNext()){
                        val id = cursor.getLong(idColumn)
                        val title = cursor.getString(titleColumn)
                        val data = cursor.getString(dataColumn)
                        val size = cursor.getString(sizeColumn)
                        val contentUri: Uri = ContentUris.withAppendedId(
                            MediaStore.Downloads.EXTERNAL_CONTENT_URI,
                            id
                        )
                        downloadList.add(CategoryModel(title,data,size,contentUri))
                    }
                }
            }
            else {
                val path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
                val files: Array<out File>? = path.listFiles()
                if (files != null) {
                    for (singleFile in files) {
                        if(!singleFile.isDirectory){
                            Log.d("TAG",singleFile.toString())
                            val uri : Uri = FileProvider.getUriForFile(requireContext(),"${context?.applicationContext?.packageName}.provider",singleFile)
                            downloadList.add(CategoryModel(singleFile.name,singleFile.toString(),singleFile.length().toString(),uri))
                        }
                    }
                }
            }
            loadDataToRecyclerView(downloadList, "Downloads")
        }
        catch (e:Exception){
            e.printStackTrace()
        }
    }

    private fun parseDocuments(){
        try {
            val documentList : MutableList<CategoryModel> = mutableListOf()

            val projection = arrayOf(
                MediaStore.Files.FileColumns.DISPLAY_NAME,
                MediaStore.Files.FileColumns.SIZE,
                MediaStore.Files.FileColumns.DATA,  // filepath of the audio file
                MediaStore.Files.FileColumns._ID
            )
            val selection = MediaStore.Files.FileColumns.MIME_TYPE + "=?" + " " + "OR" + " " +
                    MediaStore.Files.FileColumns.MIME_TYPE + "=?" + " " + "OR" + " " +
                    MediaStore.Files.FileColumns.MIME_TYPE + "=?" + " " + "OR" + " " +
                    MediaStore.Files.FileColumns.MIME_TYPE + "=?" + " " + "OR" + " " +
                    MediaStore.Files.FileColumns.MIME_TYPE + "=?" + " "

            val selectionArgs = arrayOf(
                MimeTypeMap.getSingleton().getMimeTypeFromExtension("pdf"),
                MimeTypeMap.getSingleton().getMimeTypeFromExtension("txt"),
                MimeTypeMap.getSingleton().getMimeTypeFromExtension("doc"),
                MimeTypeMap.getSingleton().getMimeTypeFromExtension("docx"),
                MimeTypeMap.getSingleton().getMimeTypeFromExtension("xlsx")
            )
            val sortOrder = MediaStore.Files.FileColumns.DATE_ADDED+" DESC"
            requireContext().contentResolver.query(
                MediaStore.Files.getContentUri("external"),
                projection,
                selection,
                selectionArgs,
                sortOrder
            )?.use { cursor ->
                val idColumn = cursor.getColumnIndexOrThrow(MediaStore.Files.FileColumns._ID)
                val titleColumn  = cursor.getColumnIndexOrThrow(MediaStore.Files.FileColumns.DISPLAY_NAME)
                val sizeColumn = cursor.getColumnIndexOrThrow(MediaStore.Files.FileColumns.SIZE)
                val dataColumn = cursor.getColumnIndexOrThrow(MediaStore.Files.FileColumns.DATA)
                while (cursor.moveToNext()){
                    val id = cursor.getLong(idColumn)
                    val title = cursor.getString(titleColumn)
                    val data = cursor.getString(dataColumn)
                    val size = cursor.getString(sizeColumn)
                    val contentUri: Uri = ContentUris.withAppendedId(
                        MediaStore.Files.getContentUri("external"),
                        id
                    )
                    if(title != null && data != null && size != null && contentUri != null){
                        documentList.add(CategoryModel(title,data,size,contentUri))
                    }

                }
            }
            loadDataToRecyclerView(documentList, "Documents")
        }
        catch (e:Exception){
            e.printStackTrace()
        }
    }

    private fun parseApk(){
        try {
            val apkList : MutableList<CategoryModel> = mutableListOf()
            val projection = arrayOf(
                MediaStore.Files.FileColumns.DISPLAY_NAME,
                MediaStore.Files.FileColumns.SIZE,
                MediaStore.Files.FileColumns.DATA,  // filepath of the audio file
                MediaStore.Files.FileColumns._ID
            )
            val selection = MediaStore.Files.FileColumns.MIME_TYPE + "=?" + " "


            val selectionArgs = arrayOf(
                MimeTypeMap.getSingleton().getMimeTypeFromExtension("apk"),
            )
            val sortOrder = MediaStore.Files.FileColumns.DATE_ADDED+" DESC"
            requireContext().contentResolver.query(
                MediaStore.Files.getContentUri("external"),
                projection,
                selection,
                selectionArgs,
                sortOrder
            )?.use { cursor ->
                val idColumn = cursor.getColumnIndexOrThrow(MediaStore.Files.FileColumns._ID)
                val titleColumn  = cursor.getColumnIndexOrThrow(MediaStore.Files.FileColumns.DISPLAY_NAME)
                val sizeColumn = cursor.getColumnIndexOrThrow(MediaStore.Files.FileColumns.SIZE)
                val dataColumn = cursor.getColumnIndexOrThrow(MediaStore.Files.FileColumns.DATA)
                while (cursor.moveToNext()){
                    val id = cursor.getLong(idColumn)
                    val title = cursor.getString(titleColumn)
                    val data = cursor.getString(dataColumn)
                    val size = cursor.getString(sizeColumn)
                    val contentUri: Uri = ContentUris.withAppendedId(
                        MediaStore.Files.getContentUri("external"),
                        id
                    )
                    if(title != null && data != null && size != null && contentUri != null){
                        apkList.add(CategoryModel(title,data,size,contentUri))
                    }
                }
            }
            loadDataToRecyclerView(apkList, "Apk")
        }
        catch (e:Exception){
            e.printStackTrace()
        }
    }
    override fun onFileClicked(file: File) {
        try {
            FileOpener().openFile(requireContext(),file)
        }
        catch (e:Exception){
            e.printStackTrace()
        }
    }

    override fun onDeleteButtonClicked(files: MutableList<File>, selectedFiles: String) {

    }

}
package com.example.filemanager

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log

import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.marginStart
import androidx.navigation.NavOptions

import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

import kotlinx.android.synthetic.main.fragment_internal_storage.*
import kotlinx.android.synthetic.main.sort_dialog.*
import java.io.File
import java.lang.Exception
import java.nio.file.Files
import java.nio.file.attribute.BasicFileAttributes


class InternalStorageFragment : Fragment(),FileSelectedListener {

    lateinit var rvInternalStorage : RecyclerView
    lateinit var tvPath : TextView
    lateinit var fileList : MutableList<File>
    lateinit var storage : File
    lateinit var fileAdapter: FileAdapter
    private var data : String? = null
    lateinit var inputEditText : EditText
    private var checkedRadioButton : RadioButton? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_internal_storage, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        inputEditText = EditText(context)


        rvInternalStorage = view.findViewById(R.id.rvInternalStorage)
        tvPath = view.findViewById(R.id.tvPath)



//        val internalStorage : String = System.getenv("EXTERNAL_STORAGE")
//        storage = File(internalStorage)
//        Log.d("TAG2",arguments?.getString("path").toString())

        data = arguments?.getString("path")
        val file = File(data!!)
        storage = file




        val path = storage.absolutePath.replace("/",">").replaceFirst(">","")
        tvPath.text = path
        displayFiles()

        btnCreateNewfolder.setOnClickListener {
            showAlertDialogForCreateNewFolder()
        }
        btnSortFiles.setOnClickListener {
            showAlertDialogForSorting()
        }
    }

    private fun findFiles (file : File): MutableList<File>{
        val fileArray : MutableList<File> = mutableListOf()
        val files : Array<out File>? = file.listFiles()
        if (files != null) {
            for(singleFile in files){
                if(!singleFile.isHidden && singleFile.isDirectory){
                    fileArray.add(singleFile)
                }
            }
            for(singleFile in files){
                if(!singleFile.isHidden && !singleFile.isDirectory){
                    fileArray.add(singleFile)
                }
            }
        }
        return fileArray
    }

    private fun displayFiles(){
        rvInternalStorage.layoutManager = LinearLayoutManager(context)
        fileList = mutableListOf()
        fileList.addAll(findFiles(storage))
        if(fileList.isNotEmpty()){
            fileAdapter = FileAdapter(requireContext(),fileList,this,
                requireActivity() as AppCompatActivity
            )
            rvInternalStorage.adapter = fileAdapter
        }
        else{
            val bundle = Bundle()
            bundle.putString("path",data)
            val navOptions = NavOptions.Builder()
                .setPopUpTo(R.id.internalStorageFragment,
                    true).build()
            findNavController().navigate(R.id.action_internalStorageFragment_to_folderEmptyFragment,bundle,navOptions)
        }
    }

    override fun onFileClicked(file: File) {
        if(file.isDirectory){
            val bundle = Bundle()
            bundle.putString("path",file.absolutePath)
            findNavController().navigate(R.id.internalStorageFragment,bundle)
//            val internalStorageFragment : InternalStorageFragment = InternalStorageFragment()
//            internalStorageFragment.arguments = bundle
//            Log.d("TAG",bundle.toString())
//            fragmentManager?.commit {
//                setReorderingAllowed(true)
//                add(R.id.fragment_container,InternalStorageFragment())
//                addToBackStack(null)
//            }

        }
        else{
            try {
                FileOpener().openFile(activity?.applicationContext!!,file)
            }
            catch (e:Exception){
                e.printStackTrace()
            }
        }
    }

    override fun onDeleteButtonClicked(files: MutableList<File>, selectedItems: String) {
        val bottomSheetDialog = BottomSheetDialog(requireActivity() as AppCompatActivity,R.style.BottomSheetDialogTheme)
        val bottomSheetView = LayoutInflater.from(activity?.applicationContext).inflate(
            R.layout.fragment_delete_dialog,bottomSheetDialog.findViewById(R.id.bottomSheetContainer)
        )
        bottomSheetView.findViewById<TextView>(R.id.btnDeleteDialog).setOnClickListener {
            Log.d("TAG",selectedItems.isEmpty().toString())

            val gson = Gson()
            val type = object : TypeToken<MutableList<File>?>() {}.type
            val items : MutableList<File> = gson.fromJson(selectedItems,type)
            for(x in items){
                files.remove(x)
                if(x.isDirectory){
                    x.deleteRecursively()
                }
                else{
                    x.delete()
                }
            }
//            for(x in selectedItems){
//                Log.d("Tag",x.toString())
//            }
            Toast.makeText(context, "Selected Items Deleted!!!", Toast.LENGTH_SHORT).show()
            bottomSheetDialog.dismiss()
            fileAdapter.notifyDataSetChanged()
        }
        bottomSheetView.findViewById<TextView>(R.id.btnCancelDialog).setOnClickListener {
            Toast.makeText(context, "Dismiss!!!", Toast.LENGTH_SHORT).show()
            bottomSheetDialog.dismiss()
        }
        if(bottomSheetView.parent != null){
            (bottomSheetView.parent as? ViewGroup)?.removeView(bottomSheetView)
        }
        bottomSheetDialog.setContentView(bottomSheetView)
        bottomSheetDialog.show()
    }

    private fun showAlertDialogForCreateNewFolder(){
        if(inputEditText.parent != null){
            (inputEditText.parent as? ViewGroup)?.removeView(inputEditText)
        }

        AlertDialog.Builder(context)
            .setTitle("Create New Folder")
            .setView(inputEditText)
            .setPositiveButton("Create"){ _,_ ->
                createNewFolder()
            }
            .setNegativeButton("Cancel"){dialogInterface:DialogInterface,_ ->
                dialogInterface.dismiss()
            }
            .create()
            .show()
    }
    private fun createNewFolder(){
        if(inputEditText.text.isNotEmpty()){
            val folder = File(data+"/"+inputEditText.text.toString())
            if(folder.exists()){
                Toast.makeText(context,"Folder already exists!!",Toast.LENGTH_LONG).show()
            }
            else{
                folder.mkdir()
                val bundle = Bundle()
                bundle.putString("path",storage.absolutePath)
                findNavController().navigate(R.id.internalStorageFragment,bundle)
            }
        }
        else{
            Toast.makeText(context,"Please Enter folder name!!!",Toast.LENGTH_LONG).show()
        }

    }
    private fun showAlertDialogForSorting(){
        val bottomSheetDialog = BottomSheetDialog(requireActivity() as AppCompatActivity,R.style.BottomSheetDialogTheme)
        val bottomSheetView = LayoutInflater.from(activity?.applicationContext).inflate(
            R.layout.sort_dialog,bottomSheetDialog.findViewById(R.id.bottomSheetSortContainer)
        )

        bottomSheetView.findViewById<RadioGroup>(R.id.radioGroupSort).setOnCheckedChangeListener { radioGroup, i ->
            when(i){
                R.id.byDateSort -> {
                    radioGroup.check(i)
                    checkedRadioButton = radioGroup.findViewById(R.id.byDateSort)
                    sortFiles("date")
                    bottomSheetDialog.dismiss()
                }
                R.id.byNameSort -> {
                    radioGroup.check(i)
                    checkedRadioButton = radioGroup.findViewById(R.id.byNameSort)
                    sortFiles("name")
                    bottomSheetDialog.dismiss()
                }
                R.id.bySizeSort -> {
                    radioGroup.check(i)
                    checkedRadioButton = radioGroup.findViewById(R.id.bySizeSort)
                    sortFiles("size")
                    bottomSheetDialog.dismiss()
                }
            }
        }
        if(checkedRadioButton != null){
            bottomSheetView.findViewById<RadioGroup>(R.id.radioGroupSort).check(checkedRadioButton!!.id)
        }
        if(bottomSheetView.parent != null){
            (bottomSheetView.parent as? ViewGroup)?.removeView(bottomSheetView)
        }
        bottomSheetDialog.setContentView(bottomSheetView)
        bottomSheetDialog.show()
    }
    private fun sortFiles(sortType : String){
        if(sortType == "name") {

            val fileList2  = fileList.filter {
                it.isDirectory
            }.sortedBy {
                it.name
            }
            val fileList3  = fileList.filter {
                it.isFile
            }.sortedBy {
                it.name
            }
            fileList.clear()
            fileList.addAll(fileList2)
            fileList.addAll(fileList3)

        }
        else if(sortType == "size"){
            var count = 0
            val fileList2  = fileList.filter {
                it.isDirectory
            }.sortedBy {
                it.list()!!.size
            }
            val fileList3  = fileList.filter {
                it.isFile
            }.sortedBy {
                it.length()
            }
            fileList.clear()
            fileList.addAll(fileList2)
            fileList.addAll(fileList3)

        }
        else if(sortType == "date"){
            val fileList2  = fileList.filter {
                it.isDirectory
            }.sortedBy {
                it.lastModified()
            }
            val fileList3  = fileList.filter {
                it.isFile
            }.sortedBy {
                it.lastModified()
            }
            fileList.clear()
            fileList.addAll(fileList2)
            fileList.addAll(fileList3)
        }
        fileAdapter.notifyDataSetChanged()

    }
}
package com.example.filemanager

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.fragment_folder_empty.*
import kotlinx.android.synthetic.main.fragment_folder_empty.tvPath
import kotlinx.android.synthetic.main.fragment_internal_storage.*
import java.io.File

class FolderEmptyFragment : Fragment() {

    private var data : String? = null
    lateinit var inputEditText : EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_folder_empty, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        inputEditText = EditText(context)
        data = arguments?.getString("path")
        val path = data?.replace("/",">")?.replaceFirst(">","")
        tvPath.text = path
        btnCreateNewfolder2.setOnClickListener {
            showAlertDialog()
        }
    }
    private fun showAlertDialog(){
        if(inputEditText.parent != null){
            (inputEditText.parent as? ViewGroup)?.removeView(inputEditText)
        }
        AlertDialog.Builder(context)
            .setTitle("Create New Folder")
            .setView(inputEditText)
            .setPositiveButton("Create"){ _,_ ->
                createNewFolder()
            }
            .setNegativeButton("Cancel"){ dialogInterface: DialogInterface, _ ->
                dialogInterface.dismiss()
            }
            .create()
            .show()
    }
    private fun createNewFolder(){
        if(inputEditText.text.isNotEmpty()){
            val folder = File(data+"/"+inputEditText.text.toString())
            if(folder.exists()){
                Toast.makeText(context,"Folder already exists!!", Toast.LENGTH_LONG).show()
            }
            else{
                folder.mkdir()
                val bundle = Bundle()
                val storage = File(data!!)
                bundle.putString("path",storage.absolutePath)
                val navOptions = NavOptions.Builder()
                    .setPopUpTo(R.id.internalStorageFragment,
                        false).build()
                findNavController().navigate(R.id.internalStorageFragment,bundle,navOptions)
            }
        }
        else{
            Toast.makeText(context,"Please Enter folder name!!!", Toast.LENGTH_LONG).show()
        }

    }
}
package com.example.filemanager

import android.os.Bundle
import android.os.StatFs
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.fragment_home.*


class HomeFragment : Fragment() {
    private var freeSpaceInternal : Double? = null
    private var totalSpaceInternal : Double? = null
    private var suffixSize : String? = "MB"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val path : String = System.getenv("EXTERNAL_STORAGE")
        val stat : StatFs = StatFs(path)
        freeSpaceInternal = ((stat.blockSizeLong * stat.availableBlocksLong)/(1024 * 1024)).toDouble()
        if(freeSpaceInternal!! >= 1024){
            freeSpaceInternal = freeSpaceInternal!! / 1024
            suffixSize = "GB"
        }

        totalSpaceInternal = ((stat.blockSizeLong * stat.blockCountLong)/(1024 * 1024)).toDouble()
        if(totalSpaceInternal!! >= 1024){
            totalSpaceInternal = totalSpaceInternal!! / 1024
            suffixSize = "GB"
        }

//        tvFree.text = freeSpaceInternal.toString()
//        tvTotal.text = totalSpaceInternal.toString()
        tvFree.text = "${String.format("%.2f",freeSpaceInternal)}$suffixSize / ${String.format("%.2f",totalSpaceInternal)}$suffixSize"

        val progressValue = ((freeSpaceInternal!! / totalSpaceInternal!!)*100).toInt()
        progressbarInternal.progress = progressValue
        progressPercentageInternal.text = "${progressValue.toString()}%"
        internalStorageContainer.setOnClickListener{
            val bundle = Bundle()
            bundle.putString("path",System.getenv("EXTERNAL_STORAGE"))

            findNavController().navigate(R.id.action_homeFragment_to_internalStorageFragment,bundle)
        }

        btnApk.setOnClickListener {
            val bundle = Bundle()
            bundle.putString("category","Apk")
            findNavController().navigate(R.id.action_homeFragment_to_categoryFilesFragment,bundle)

        }
        btnAudio.setOnClickListener {
            val bundle = Bundle()
            bundle.putString("category","Audio")
            findNavController().navigate(R.id.action_homeFragment_to_categoryFilesFragment,bundle)
        }
        btnDocuments.setOnClickListener {
            val bundle = Bundle()
            bundle.putString("category","Documents")
            findNavController().navigate(R.id.action_homeFragment_to_categoryFilesFragment,bundle)
        }
        btnDownloads.setOnClickListener {
            val bundle = Bundle()
            bundle.putString("category","Downloads")
            findNavController().navigate(R.id.action_homeFragment_to_categoryFilesFragment,bundle)
        }
        btnPhotos.setOnClickListener {
            val bundle = Bundle()
            bundle.putString("category","Photos")
            findNavController().navigate(R.id.action_homeFragment_to_categoryFilesFragment,bundle)
        }
        btnVideo.setOnClickListener {
            val bundle = Bundle()
            bundle.putString("category","Video")
            findNavController().navigate(R.id.action_homeFragment_to_categoryFilesFragment,bundle)
        }

    }
}
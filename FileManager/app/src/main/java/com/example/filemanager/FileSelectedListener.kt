package com.example.filemanager

import java.io.File

interface FileSelectedListener {
    fun onFileClicked(file : File)
    fun onDeleteButtonClicked(files: MutableList<File>,selectedItems : String)
}
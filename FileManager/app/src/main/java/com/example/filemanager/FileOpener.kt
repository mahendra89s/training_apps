package com.example.filemanager

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import androidx.core.content.FileProvider
import java.io.File
import java.io.IOException
import java.net.URI


class FileOpener  {
    @Throws(IOException::class)
    fun openFile(context: Context,file: File){
        val selectedFile : File = file
        val uri : Uri? = FileProvider.getUriForFile(context,"${context.applicationContext.packageName}.provider",file)

        val intent : Intent = Intent(Intent.ACTION_VIEW)
        if(uri.toString().contains(".doc") || uri.toString().contains(".docx")){
            intent.setDataAndType(uri,"application/msword")
//            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
//            context.startActivity(intent)
        }
        else if(uri.toString().contains(".pdf")){
            intent.setDataAndType(uri,"application/pdf")
//            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
//            context.startActivity(intent)
        }
        else if(uri.toString().contains(".mp3") || uri.toString().contains(".wav")){
            intent.setDataAndType(uri,"audio/x-wav")
//            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
//            context.startActivity(intent)
        }
        else if(uri.toString().toLowerCase().contains(".jpeg") || uri.toString().toLowerCase().contains(".jpg")){
            intent.setDataAndType(uri,"image/jpeg")
//            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
//            context.startActivity(intent)
        }
        else if(uri.toString().contains(".mp4")){
            intent.setDataAndType(uri,"video/*")
//            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
//            context.startActivity(intent)
        }
        else if(uri.toString().contains(".apk")){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
                val intent1 = Intent(Intent.ACTION_INSTALL_PACKAGE)
                intent1.setData(uri)
                intent1.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                intent1.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                context.startActivity(intent1)
            }
            else{
                intent.setDataAndType(uri,"application/vnd.android.package-archive")
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                context.startActivity(intent)
            }
        }
        else{
            intent.setDataAndType(uri,"*/*")
//            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
//            context.startActivity(intent)
        }
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)

        context.startActivity(intent)

    }
}
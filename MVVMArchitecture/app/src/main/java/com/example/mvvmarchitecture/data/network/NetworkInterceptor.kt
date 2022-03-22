package com.example.mvvmarchitecture.data.network

import android.content.Context
import android.net.ConnectivityManager
import com.example.mvvmarchitecture.utils.InternetException
import okhttp3.Interceptor
import okhttp3.Response

class NetworkInterceptor(
    context: Context
): Interceptor {
    private val applicationContext = context.applicationContext
    override fun intercept(chain: Interceptor.Chain): Response {
        if(!isInternetAvailable()){
            throw InternetException("Make Sure Internet Working!!!")
        }
        return chain.proceed(chain.request())
    }

    private fun isInternetAvailable() : Boolean{
        val connectivityManager =
            applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        connectivityManager.activeNetworkInfo.also {
            return it != null && it.isConnected
        }
    }
}
package com.example.connecttointernet.utilities

import android.content.Context
import android.net.Uri
import android.widget.Toast
import java.io.IOException
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.URL

class NetworkUtils(
    private val context: Context
) {
    private val githubUrl = "https://api.github.com/search/repositories"
    var url: URL? = null

    fun buildUrl(githubSearchQuery : String): URL{
        val builtUri : Uri = Uri.parse(githubUrl).buildUpon()
            .appendQueryParameter("q",githubSearchQuery)
            .appendQueryParameter("sort","stars")
            .build()
        try {
            url = URL(builtUri.toString())
        }
        catch(e : MalformedURLException){
            Toast.makeText(context,e.message,Toast.LENGTH_LONG).show()
        }
        return url!!
    }

    @Throws(IOException::class)
    fun getResponseFromHttp(url: URL) : String? {
        val urlConnection : HttpURLConnection = url.openConnection() as HttpURLConnection
        try{
            urlConnection.inputStream.bufferedReader().use {
                return it.readLine()
            }

        }
        finally {
            urlConnection.disconnect()
        }
        return null
    }
}
package com.example.connecttointernet

import android.os.AsyncTask
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.example.connecttointernet.utilities.NetworkUtils
import kotlinx.android.synthetic.main.activity_main.*
import java.io.IOException
import java.net.URL

class MainActivity : AppCompatActivity() {
    var search : String? = null
    var githubUrl : URL? = null
    var gitSearchResult : String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu,menu)
        return true
    }
    private fun makeGithubSearchQuery(){
        search = edtSearch.text.toString()
        githubUrl = NetworkUtils(this).buildUrl(search!!)
        txtGithub.text = githubUrl.toString()
        MyAsync().execute(githubUrl)
    }
    private fun showJsonData(){
        tv_error_message_display.visibility = View.GONE
        tv_github_search_results_json.visibility = View.VISIBLE
    }
    private fun showError(){
        tv_error_message_display.visibility = View.VISIBLE
        tv_github_search_results_json.visibility = View.GONE
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.search_bar -> {
                makeGithubSearchQuery()
            }
        }
        return true
    }
    inner class MyAsync : AsyncTask<URL, Void, String>() {
        override fun onPreExecute() {
            super.onPreExecute()
            pb_loading_indicator.visibility = View.VISIBLE
        }

        override fun doInBackground(vararg params: URL?): String? {
            val searchUrl: URL? = params[0]
            try{
                gitSearchResult = NetworkUtils(this@MainActivity).getResponseFromHttp(searchUrl!!)
            }catch(e: IOException){
                Toast.makeText(this@MainActivity,"Try Again",Toast.LENGTH_LONG).show()
            }
            return gitSearchResult
        }

        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)
            pb_loading_indicator.visibility = View.GONE
            if(gitSearchResult != null && !gitSearchResult.equals("")){
                showJsonData()
                tv_github_search_results_json.text = gitSearchResult
            }
            else{
                showError()
            }
        }

    }
}
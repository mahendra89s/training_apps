package com.example.assignmentproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        button.setOnClickListener {
            Intent(this,MainActivity2::class.java).also{
                startActivityForResult(it,1)
            }
        }
        btnLogin.setOnClickListener {
            Intent(this,LoginActivity::class.java).also{
                startActivity(it)
            }
        }
        btnNavDemo.setOnClickListener {
            Intent(this,BottomNav::class.java).also{
                startActivity(it)
            }
        }
        btnContentProvider.setOnClickListener {
            Intent(this,ContentProviderDemo::class.java).also{
                startActivity(it)
            }
        }
        btnWebview.setOnClickListener {
            Intent(this,WebviewActivity::class.java).also{
                startActivity(it)
            }
        }
        btnMap.setOnClickListener {
            Intent(this,MapsActivity::class.java).also{
                startActivity(it)
            }
        }
        btnParcelable.setOnClickListener {
            val user = User("Mahendra",20)
            val intent : Intent = Intent(this,ParcelableActivity::class.java)
            intent.putExtra("User",user)
            startActivity(intent)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode == requestCode){
            var txtMsg = data?.getStringExtra("Name")
            textView.text = txtMsg
        }
    }

}
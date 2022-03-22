package com.example.coroutinesdemo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_second.*

class SecondActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)
//        val firstName = intent.getStringExtra("EXTRA_FirstName")
//        val lasttName = intent.getStringExtra("EXTRA_LastName")
//        val dob = intent.getStringExtra("EXTRA_DOB")
//        val country = intent.getStringExtra("EXTRA_Country")
        val person = intent.getSerializableExtra("EXTRA_PERSON") as Person
        var str = "My Name is ${person.firstName} ${person.lastName} \n"+"My Dob is ${person.dob} \n"+"I'm from ${person.country}"
        textView.text = str
        btnBack.setOnClickListener {
            Intent(this,MainActivity::class.java).also{
                startActivity(it)
            }
        }
    }
}
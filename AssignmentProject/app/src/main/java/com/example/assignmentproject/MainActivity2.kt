package com.example.assignmentproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.TextView

class MainActivity2 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        val textView = findViewById<EditText>(R.id.editTextTextPersonName)
        val button = findViewById<TextView>(R.id.button2)
        button.setOnClickListener {
            Intent(this,MainActivity::class.java).also{
                val name = textView.text.toString()
                it.putExtra("Name",name)
                setResult(1,it)
                finish()
            }
        }
    }
}
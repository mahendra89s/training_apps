package com.example.coroutinesdemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import kotlinx.android.synthetic.main.activity_dialog_box.*

class DialogBox : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dialog_box)
        val alertDialog = AlertDialog.Builder(this)
            .setTitle("Add Contact")
            .setMessage("Do you want to Add Contact")
            .setIcon(R.drawable.ic_add_contact)
            .setPositiveButton("Yes"){ _,_->
                Toast.makeText(this,"You Added the Contact",Toast.LENGTH_LONG).show()
            }
            .setNegativeButton("No"){_,_ ->
                Toast.makeText(this,"You Didn't Added the Contact",Toast.LENGTH_LONG).show()
            }.create()
        btn1.setOnClickListener {
            alertDialog.show()
        }

        val options = arrayOf("Apple","Samsung","OnePlus")
        val alertDialog2 = AlertDialog.Builder(this)
            .setTitle("Select One from Options ")
            .setSingleChoiceItems(options,0){dialogInterface,i ->
                Toast.makeText(this,"You Selected ${options[i]}",Toast.LENGTH_LONG).show()
            }
            .setPositiveButton("Accept"){dialogInterface,i ->
                Toast.makeText(this,"You Accepted",Toast.LENGTH_LONG).show()
            }
            .setNegativeButton("Decline"){dialogInterface,i ->
                Toast.makeText(this,"You Declined",Toast.LENGTH_LONG).show()
            }.create()
        btn2.setOnClickListener {
            alertDialog2.show()
        }

        val multialertDialog2 = AlertDialog.Builder(this)
            .setTitle("Select One from Options ")
            .setMultiChoiceItems(options, booleanArrayOf(false,false,false)){ _, i,isChecked ->
                if(isChecked){
                    Toast.makeText(this,"You Checked ${options[i]}",Toast.LENGTH_LONG).show()
                }
                else{
                    Toast.makeText(this,"You UnChecked ${options[i]}",Toast.LENGTH_LONG).show()
                }
            }
            .setPositiveButton("Accept"){dialogInterface,i ->
                Toast.makeText(this,"You Accepted",Toast.LENGTH_LONG).show()
            }
            .setNegativeButton("Decline"){dialogInterface,i ->
                Toast.makeText(this,"You Declined",Toast.LENGTH_LONG).show()
            }.create()
        btn3.setOnClickListener {
            multialertDialog2.show()
        }
    }
}
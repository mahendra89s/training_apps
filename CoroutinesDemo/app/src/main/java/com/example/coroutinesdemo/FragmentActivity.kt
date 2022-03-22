package com.example.coroutinesdemo

import android.graphics.Insets.add
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import androidx.fragment.app.add
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import kotlinx.android.synthetic.main.activity_fragment.*

class FragmentActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fragment)
        val firstFragment = FirstFragment()
        val secondFragment = SecondFragment()

        btnfragment1.setOnClickListener {
            supportFragmentManager.commit {
                setReorderingAllowed(true)
                replace<FirstFragment>(R.id.fragment)
            }
        }
        btnfragment2.setOnClickListener {
            supportFragmentManager.commit {
                setReorderingAllowed(true)
                replace<SecondFragment>(R.id.fragment)
            }
        }
    }
}
package com.example.assignmentproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import kotlinx.android.synthetic.main.activity_bottom_nav.*

class BottomNav : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bottom_nav)
        val home = HomeFragment()
        val message = MessageFragment()
        val profile = ProfileFragment()
        setCurrentFragment(HomeFragment())
        bottomNav.setOnItemSelectedListener {
            when(it.itemId){
                R.id.home -> setCurrentFragment(home)
                R.id.message -> setCurrentFragment(message)
                R.id.profile -> setCurrentFragment(profile)
            }
            true
        }
    }
    private fun setCurrentFragment(fragment : Fragment) {
        supportFragmentManager.commit {
            setReorderingAllowed(true)
            replace(R.id.fragView,fragment)
        }
    }
}
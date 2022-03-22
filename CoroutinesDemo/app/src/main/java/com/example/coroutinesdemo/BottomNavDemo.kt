package com.example.coroutinesdemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import kotlinx.android.synthetic.main.activity_bottom_nav_demo.*

class BottomNavDemo : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bottom_nav_demo)

        val firstFrag = FirstFragment()
        val secondFrag = SecondFragment()
        setCurrentFragment(firstFrag)
        bottomNavBar.setOnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.home -> setCurrentFragment(firstFrag)
                R.id.message -> setCurrentFragment(secondFrag)
                R.id.profile -> setCurrentFragment(firstFrag)
            }
            true
        }
        bottomNavBar.getOrCreateBadge(R.id.message).apply {
            number = 10
            isVisible = true
        }
    }
    private fun setCurrentFragment(fragment : Fragment){
        supportFragmentManager.commit {
            setReorderingAllowed(true)
            replace(R.id.fragView,fragment)
        }
    }
}
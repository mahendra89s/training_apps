package com.example.backstackexample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    lateinit var navController: NavController
    lateinit var fragmentManager: FragmentManager
    var addedToBackStack = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val appBarConfiguration = AppBarConfiguration.Builder(
            R.id.home, R.id.market, R.id.profile)
            .build()

        //Initialize NavController.
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.navHost) as NavHostFragment
        navController = navHostFragment.navController
        NavigationUI.setupWithNavController(bottomNav,navHostFragment.navController)
        fragmentManager = supportFragmentManager
        bottomNav.setOnNavigationItemSelectedListener {

            navController.popBackStack(it.itemId,true)
            when(it.itemId){
                R.id.home -> {
                    fragmentManager.commit {
                        val navOptions = NavOptions.Builder().setPopUpTo(R.id.homeFragment,true).build()
                        navController.navigate(R.id.homeFragment,null,navOptions)
                    }
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.market -> {
                    fragmentManager.commit {
                        val navOptions = NavOptions.Builder().setPopUpTo(R.id.marketFragment,true).build()
                        navController.navigate(R.id.marketFragment,null,navOptions)
                    }
                    return@setOnNavigationItemSelectedListener true
                }
                else -> {return@setOnNavigationItemSelectedListener false}
            }
        }
    }
}

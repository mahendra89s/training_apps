package com.example.cameraapp.ui.main

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.cameraapp.ui.blank.BlankFragment
import com.example.cameraapp.ui.photo.PhotoFragment
import com.example.cameraapp.ui.video.VideoFragment

class ViewPagerAdapter(
    val fragment: Fragment,
    val totalTabs : Int
):FragmentStateAdapter(fragment){

    override fun getItemCount(): Int {
        return totalTabs
    }

    override fun createFragment(position: Int): Fragment {
        return when(position){
            0 -> PhotoFragment()
            1 -> VideoFragment()
            else -> BlankFragment()
        }
    }

}
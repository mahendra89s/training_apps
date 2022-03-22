package com.example.cameraapp.ui.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.cameraapp.R
import com.example.cameraapp.databinding.FragmentMainBinding
import com.google.android.material.tabs.TabLayoutMediator

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


class MainFragment : Fragment() {

    lateinit var binding : FragmentMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentMainBinding.inflate(layoutInflater)
        val tabTitles = listOf<String>("Photo","Video")
        setupTabLayout(tabTitles)
        return binding.root
    }
    private fun setupTabLayout(tabTitles : List<String>){
        val adapter = ViewPagerAdapter(this, tabTitles.size)
        binding.viewPager.adapter = adapter
        TabLayoutMediator(binding.tabLayout,binding.viewPager) { tab, position ->
            tab.text = tabTitles[position]
        }.attach()
        for(i in tabTitles.indices){
            val textView = LayoutInflater.from(requireContext()).inflate(R.layout.tab_tile,null) as TextView
            binding.tabLayout.getTabAt(i)?.customView = textView
        }
    }

}
package com.example.weatherapp

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.google.gson.Gson


class FragmentPagerAdapter(
    fragmentManager : FragmentManager,
    val context : Context,
    val arrayList : MutableList<CityInfoResponse>
) : FragmentStatePagerAdapter(fragmentManager){
    override fun getCount(): Int {
        return arrayList.size
    }

    override fun getItem(position: Int): Fragment {
        return WeatherScreenFragment.newInstance(Gson().toJson(arrayList[position]))
    }
}
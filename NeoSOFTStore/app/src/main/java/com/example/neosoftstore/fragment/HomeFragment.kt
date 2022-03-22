package com.example.neosoftstore.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.fragment.findNavController
import com.example.neosoftstore.R
import com.example.neosoftstore.adapters.ViewPagerAdapter
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.fragment_home.*



class HomeFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val toolbar = activity?.findViewById<androidx.appcompat.widget.Toolbar>(R.id.homeScreenToolbar)
        toolbar?.findViewById<TextView>(R.id.toolbarTitle)?.text = "NeoSTORE"
        //Image Slider
        val images = listOf(
            R.drawable.slider_img1,
            R.drawable.slider_img2,
            R.drawable.slider_img3,
            R.drawable.slider_img4,
        )
        val adapter = ViewPagerAdapter(images,requireContext())
        imgSliderViewPager.adapter = adapter
        TabLayoutMediator(imageSliderIndicatorHome,imgSliderViewPager){
                tab,position ->
        }.attach()

        btnTablesHome.setOnClickListener {
            val bundle = Bundle()
            bundle.putInt("product_category_id",1)
            bundle.putString("catName","Tables")
            findNavController().navigate(R.id.action_homeFragment_to_productListingFragment,bundle)
        }
        btnSofasHome.setOnClickListener {
            val bundle = Bundle()
            bundle.putInt("product_category_id",3)
            bundle.putString("catName","Sofas")
            findNavController().navigate(R.id.action_homeFragment_to_productListingFragment,bundle)
        }
        btnChairsHome.setOnClickListener {
            val bundle = Bundle()
            bundle.putInt("product_category_id",2)
            bundle.putString("catName","Chairs")
            findNavController().navigate(R.id.action_homeFragment_to_productListingFragment,bundle)
        }
        btnCupboardsHome.setOnClickListener {
            val bundle = Bundle()
            bundle.putInt("product_category_id",4)
            bundle.putString("catName","Cupboards")
            findNavController().navigate(R.id.action_homeFragment_to_productListingFragment,bundle)
        }
    }

}
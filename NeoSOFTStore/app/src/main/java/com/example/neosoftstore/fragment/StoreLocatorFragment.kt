package com.example.neosoftstore.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.neosoftstore.R
import com.example.neosoftstore.adapters.StoreLocatorAdapter
import com.example.neosoftstore.adapters.StoreLocatorListener
import com.example.neosoftstore.models.StoreLocatorModel
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.*
import kotlinx.android.synthetic.main.fragment_store_locator.*


class StoreLocatorFragment : Fragment(), OnMapReadyCallback,StoreLocatorListener {
    lateinit var storeLocatorList: MutableList<StoreLocatorModel>
    lateinit var map : GoogleMap
    lateinit var builder : LatLngBounds.Builder
    lateinit var bounds : LatLngBounds
    lateinit var markerList : MutableList<Marker>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_store_locator, container, false)
        val supportMapFragment :SupportMapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        supportMapFragment.getMapAsync(this)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val toolbar = activity?.findViewById<androidx.appcompat.widget.Toolbar>(R.id.homeScreenToolbar)
        val textViewToolbar = toolbar?.findViewById<TextView>(R.id.toolbarTitle)
        textViewToolbar!!.apply {
            text = "Store Locator"
            typeface = ResourcesCompat.getFont(context, R.font.gotham_medium)
            textSize = 25F
        }
        storeLocatorList = mutableListOf(
            StoreLocatorModel("NeoSOFT Technologies Rabale",
                "Unit No 501, Sigma IT Park, Plot No R-203,204, Midc TTC Industrial Area. Rabale, Navi Mumbai, Maharashtra 400701",
                19.1410544,
                73.0088433),
            StoreLocatorModel("NeoSOFT Technologies Parel",
                "4th Floor, The Ruby, 29, Senapati Bapat Marg, Dadar West, Mumbai, Maharashtra 400028",
                19.0174469,
                72.8275572),
            StoreLocatorModel("NeoSOFT Technologies Pune",
            "NTPL SEZ (Blueridge), IT6, 1st Floor, Rajiv Gandhi - Infotech Park, Phase-I, Hinjewadi, Pune, Maharashtra 411057",
                18.5607534,
                73.7314271
            ),
            StoreLocatorModel("NeoSOFT Technologies Prabhadevi",
                "8th, 9th & 10th Floor, Business Arcade Tower, Plot no- 584, Sayani Rd, Opp Parel Bus Depot, Dighe Nagar, Prabhadevi, Mumbai - 400 025, INDIA",
                19.01803,
                72.828343
            ),
            StoreLocatorModel("NeoSOFT Technologies Bengaluru",
                "91 Spring board,4th Floor, No. 22, 5th Block, Salarpuria Towers - I, Hosur Road, Koramangala Bengaluru -560095, INDIA",
                12.9248671,
                77.6318783
            ),
            StoreLocatorModel("NeoSOFT Technologies Ahmedabad",
                "21st floor, D block, Westgate, Sarkhej Gandhinagar Hwy, Near YMCA Club, Makarba, Ahmedabad, Gujarat - 380015, INDIA",
                23.0032836,
                72.4982642
            ),
        )
        val adapter = StoreLocatorAdapter(storeLocatorList,requireContext(),this@StoreLocatorFragment)
        rvStoreLocator.adapter = adapter
        rvStoreLocator.layoutManager = LinearLayoutManager(requireContext())
    }

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap
        builder = LatLngBounds.Builder()
        markerList = mutableListOf()
        for(items in storeLocatorList){
            val marker = map.addMarker(MarkerOptions().position(LatLng(items.lat,items.long))
                .title(items.name)
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_map_marker))
                .snippet(items.address)
            )
            builder.include(marker!!.position)
            markerList.add(marker)
        }
        bounds = builder.build()
        val width : Int = resources.displayMetrics.widthPixels
        val padding : Int = (width * 0.1).toInt()

//        map.moveCamera(CameraUpdateFactory.newLatLng(LatLng(storeLocatorList[0].lat,storeLocatorList[0].long)))
        map.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds,padding))
    }

    override fun onStoreListClick(lat: Double, long: Double,position : Int) {
        val cameraPosition = CameraPosition.Builder()
            .target(LatLng(lat,long))
            .zoom(17f)
            .build()
        map.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition))
        markerList[position].showInfoWindow()
    }

}
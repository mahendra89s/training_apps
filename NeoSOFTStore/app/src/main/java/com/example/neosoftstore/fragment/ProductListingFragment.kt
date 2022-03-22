package com.example.neosoftstore.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.res.ResourcesCompat
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.neosoftstore.apis.ApiClient
import com.example.neosoftstore.models.Data
import com.example.neosoftstore.models.ProductModel
import com.example.neosoftstore.ProductClickListener
import com.example.neosoftstore.adapters.ProductListingAdapter
import com.example.neosoftstore.R
import kotlinx.android.synthetic.main.activity_home_screen.*
import kotlinx.android.synthetic.main.fragment_product_listing.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class ProductListingFragment : Fragment(),ProductClickListener {

    private var product_category_id: Int? = null
    private var title :String? = null
    lateinit var adapter: ProductListingAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_product_listing, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        product_category_id = arguments?.getInt("product_category_id",0)
        title = arguments?.getString("catName","")
        val toolbar = activity?.findViewById<androidx.appcompat.widget.Toolbar>(R.id.homeScreenToolbar)
        val textViewToolbar = toolbar?.findViewById<TextView>(R.id.toolbarTitle)
        textViewToolbar!!.apply {
            text = title
            typeface = ResourcesCompat.getFont(context, R.font.gotham_medium)
            textSize = 25F
        }
        val call = ApiClient.getProductClient.getProductList(product_category_id!!)
        call.enqueue(object : Callback<ProductModel>{
            override fun onResponse(call: Call<ProductModel>, response: Response<ProductModel>) {
                if(response.code() == 200){
                    val productResponse = response.body()
                    val data = productResponse!!.data
                    val productList = mutableListOf<Data>()
                    for (item in data){
                        productList.add(item)
                    }
                    adapter = ProductListingAdapter(productList,requireContext(),this@ProductListingFragment)
                    rvProductListing.adapter = adapter
                    rvProductListing.layoutManager = LinearLayoutManager(requireContext())
                }
            }

            override fun onFailure(call: Call<ProductModel>, t: Throwable) {
                Toast.makeText(context,"Something went wrong!!",Toast.LENGTH_LONG).show()
            }

        })
    }

    override fun onProductClick(productId: Int) {
        val bundle = Bundle()
        bundle.putInt("productId",productId)
        findNavController().navigate(R.id.action_productListingFragment_to_productDetailsFragment,bundle)
    }
}
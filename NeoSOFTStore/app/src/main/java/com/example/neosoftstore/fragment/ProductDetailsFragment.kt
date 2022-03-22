package com.example.neosoftstore.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.res.ResourcesCompat
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.neosoftstore.apis.ApiClient
import com.example.neosoftstore.ImageClickListener
import com.example.neosoftstore.models.GetProductInfoModel
import com.example.neosoftstore.adapters.ProductImagesAdapter
import com.example.neosoftstore.R
import com.example.neosoftstore.models.QuantitySceenDataModel
import kotlinx.android.synthetic.main.fragment_product_details.*
import kotlinx.android.synthetic.main.item_product_list.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class ProductDetailsFragment : Fragment(), ImageClickListener {

    private var data : Int? = null
    lateinit var quantitySceenDataModel : QuantitySceenDataModel
    var productId : Int? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_product_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        data = arguments?.getInt("productId")
        productId = data

        val call = ApiClient.getProductClient.getProductInfo(data!!)
        call.enqueue(object : Callback<GetProductInfoModel>{
            override fun onResponse(
                call: Call<GetProductInfoModel>,
                response: Response<GetProductInfoModel>
            ) {
                if(response.code() == 200){
                    val productInfo = response.body()
                    Log.d("TAG1",productInfo.toString())
                    productNameDetailsScreen.text = productInfo!!.data.name.capitalize()
                    val toolbar = activity?.findViewById<androidx.appcompat.widget.Toolbar>(R.id.homeScreenToolbar)
                    val textViewToolbar = toolbar?.findViewById<TextView>(R.id.toolbarTitle)
                    textViewToolbar!!.apply {
                        text = productInfo.data.name.capitalize()
                        typeface = ResourcesCompat.getFont(context, R.font.gotham_book)
                        textSize = 20F
                    }
                    when(productInfo.data.product_category_id){
                        1 -> productCategoryDetailsScreen.text = String.format(context!!.getString(R.string.category),"Tables")
                        2 -> productCategoryDetailsScreen.text = String.format(context!!.getString(R.string.category),"Chairs")
                        3 -> productCategoryDetailsScreen.text = String.format(context!!.getString(R.string.category),"Sofas")
                        4 -> productCategoryDetailsScreen.text = String.format(context!!.getString(R.string.category),"Cupboards")
                    }
                    productProducerDetailsScreen.text = productInfo.data.producer.capitalize()
                    val rating = productInfo.data.rating
                    val starArray  = listOf(productStar1,
                        productStar2,
                        productStar3,
                        productStar4,
                        productStar5,
                    )

                    for(i in 0 until rating){
                        Glide.with(requireContext()).load(R.drawable.star_check).into(starArray[i])
                    }
                    val imagesArray = mutableListOf<String>()
                    for(i in productInfo.data.product_images){
                        imagesArray.add(i.image)
                    }
                    productPriceDetailsScreen.text = String.format(context!!.getString(R.string.price),productInfo.data.cost)
                    descDetailsScreen.text = productInfo.data.description

                    val adapter = ProductImagesAdapter(imagesArray,context!!,this@ProductDetailsFragment)
                    rvImagesDetailScreen.adapter = adapter
                    rvImagesDetailScreen.layoutManager = LinearLayoutManager(context!!,LinearLayoutManager.HORIZONTAL,false)

                    Glide.with(context!!).load(productInfo.data.product_images[0].image).into(currentImageDetailsScreen)
                    productInfo.data.apply {
                        quantitySceenDataModel = QuantitySceenDataModel(data!!,name,product_images[0].image)
                    }

                }
            }

            override fun onFailure(call: Call<GetProductInfoModel>, t: Throwable) {
                Toast.makeText(context,"Something went wrong!!",Toast.LENGTH_LONG).show()
            }

        })
        btnBuyProductDetails.setOnClickListener {
            val bundle = Bundle()
            bundle.putParcelable("PassDatatoQuantityScreen",quantitySceenDataModel)
            findNavController().navigate(R.id.action_productDetailsFragment_to_enterQuantityFragment,bundle)
        }
        btnRateProductDetails.setOnClickListener {
            val bundle = Bundle()
            bundle.putParcelable("PassDataToRatingScreen",quantitySceenDataModel)
            findNavController().navigate(R.id.action_productDetailsFragment_to_ratingFragment,bundle)
        }
        btnShareDetailsScreen.setOnClickListener {
            val sendIntent: Intent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, "www.neostore.com/products/$productId")
                type = "text/plain"
            }
            val shareIntent = Intent.createChooser(sendIntent, null)
            startActivity(shareIntent)
        }
    }

    override fun imageClick(image: String) {
        Glide.with(requireContext()).load(image).into(currentImageDetailsScreen)
    }

}
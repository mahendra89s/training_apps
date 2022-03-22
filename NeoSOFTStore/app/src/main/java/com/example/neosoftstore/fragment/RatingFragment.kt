package com.example.neosoftstore.fragment

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.InsetDrawable
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.DialogFragment
import com.bumptech.glide.Glide
import com.example.neosoftstore.R
import com.example.neosoftstore.apis.ApiClient
import com.example.neosoftstore.models.QuantitySceenDataModel
import com.example.neosoftstore.models.RatingResponseModel
import kotlinx.android.synthetic.main.fragment_enter_quantity.*
import kotlinx.android.synthetic.main.fragment_rating.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*


class RatingFragment : DialogFragment() {
    private var data : QuantitySceenDataModel? = null
    private var rating : Int? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_rating, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        data = arguments?.getParcelable("PassDataToRatingScreen")
        tvProductNameRatingScreen.text = data?.name?.replaceFirstChar {
            if (it.isLowerCase()) it.titlecase(
                Locale.getDefault()
            ) else it.toString()
        }
        Glide.with(requireContext()).load(data?.image).into(imgProductRatingScreen)
        imgStar1RatingScreen.setOnClickListener {
            imgStar1RatingScreen.setImageResource(R.drawable.star_check)
            imgStar2RatingScreen.setImageResource(R.drawable.star_unchek)
            imgStar3RatingScreen.setImageResource(R.drawable.star_unchek)
            imgStar4RatingScreen.setImageResource(R.drawable.star_unchek)
            imgStar5RatingScreen.setImageResource(R.drawable.star_unchek)
            rating = 1
        }
        imgStar2RatingScreen.setOnClickListener {
            imgStar1RatingScreen.setImageResource(R.drawable.star_check)
            imgStar2RatingScreen.setImageResource(R.drawable.star_check)
            imgStar3RatingScreen.setImageResource(R.drawable.star_unchek)
            imgStar4RatingScreen.setImageResource(R.drawable.star_unchek)
            imgStar5RatingScreen.setImageResource(R.drawable.star_unchek)
            rating = 2
        }
        imgStar3RatingScreen.setOnClickListener {
            imgStar1RatingScreen.setImageResource(R.drawable.star_check)
            imgStar2RatingScreen.setImageResource(R.drawable.star_check)
            imgStar3RatingScreen.setImageResource(R.drawable.star_check)
            imgStar4RatingScreen.setImageResource(R.drawable.star_unchek)
            imgStar5RatingScreen.setImageResource(R.drawable.star_unchek)
            rating = 3
        }
        imgStar4RatingScreen.setOnClickListener {
            imgStar1RatingScreen.setImageResource(R.drawable.star_check)
            imgStar2RatingScreen.setImageResource(R.drawable.star_check)
            imgStar3RatingScreen.setImageResource(R.drawable.star_check)
            imgStar4RatingScreen.setImageResource(R.drawable.star_check)
            imgStar5RatingScreen.setImageResource(R.drawable.star_unchek)
            rating = 4
        }
        imgStar5RatingScreen.setOnClickListener {
            imgStar1RatingScreen.setImageResource(R.drawable.star_check)
            imgStar2RatingScreen.setImageResource(R.drawable.star_check)
            imgStar3RatingScreen.setImageResource(R.drawable.star_check)
            imgStar4RatingScreen.setImageResource(R.drawable.star_check)
            imgStar5RatingScreen.setImageResource(R.drawable.star_check)
            rating = 5
        }
        btnRAteNow.setOnClickListener {
            Log.d("Rating",rating.toString())
            val call = ApiClient.ratingClient.setRating(data?.product_id!!, rating)
            call.enqueue(object  : Callback<RatingResponseModel>{
                override fun onResponse(
                    call: Call<RatingResponseModel>,
                    response: Response<RatingResponseModel>
                ) {
                    if(response.body()?.status == 200){
                        Toast.makeText(requireContext(), response.body()!!.user_msg,Toast.LENGTH_LONG).show()
                        dialog?.dismiss()
                    }
                    else{
                        Toast.makeText(requireContext(), response.body()!!.user_msg,Toast.LENGTH_LONG).show()
                        dialog?.dismiss()
                    }
                }

                override fun onFailure(call: Call<RatingResponseModel>, t: Throwable) {
                    Toast.makeText(requireContext(), t.message,Toast.LENGTH_LONG).show()
                    dialog?.dismiss()
                }

            })
        }

    }
    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(ConstraintLayout.LayoutParams.MATCH_PARENT, ConstraintLayout.LayoutParams.WRAP_CONTENT)
        val back = ColorDrawable(Color.TRANSPARENT)
        val margin = 50
        val inset = InsetDrawable(back, margin)
        dialog?.window?.setBackgroundDrawable(inset)
    }

}
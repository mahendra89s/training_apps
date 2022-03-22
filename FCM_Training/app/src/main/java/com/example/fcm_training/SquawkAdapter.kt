package com.example.fcm_training

import android.content.Context
import android.database.Cursor
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.fcm_training.provider.SquawkContract
import kotlinx.android.synthetic.main.item_squawk_list.view.*
import java.text.SimpleDateFormat
import java.util.*

class SquawkAdapter (
    val context: Context,
        ):
    RecyclerView.Adapter<SquawkAdapter.ViewHolder?>() {
    private var mData: Cursor? = null

    inner class ViewHolder(view : View) : RecyclerView.ViewHolder(view)
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context)
            .inflate(R.layout.item_squawk_list, parent, false))
    }

    override fun onBindViewHolder(
        holder: SquawkAdapter.ViewHolder,
        position: Int
    ) {
        mData!!.moveToPosition(position)
        val message = mData!!.getString(MainActivity.COL_NUM_MESSAGE)
        val author = mData!!.getString(MainActivity.COL_NUM_AUTHOR)
        val authorKey = mData!!.getString(MainActivity.COL_NUM_AUTHOR_KEY)

        // Get the date for displaying
        val dateMillis = mData!!.getLong(MainActivity.COL_NUM_DATE)
        var date = ""
        val now = System.currentTimeMillis()

        // Change how the date is displayed depending on whether it was written in the last minute,
        // the hour, etc.
        date = if (now - dateMillis < DAY_MILLIS) {
            if (now - dateMillis < HOUR_MILLIS) {
                val minutes = Math.round(((now - dateMillis) / MINUTE_MILLIS).toFloat()).toLong()
                minutes.toString() + "m"
            } else {
                val minutes = Math.round(((now - dateMillis) / HOUR_MILLIS).toFloat()).toLong()
                minutes.toString() + "h"
            }
        } else {
            val dateDate = Date(dateMillis)
            sDateFormat.format(dateDate)
        }

        // Add a dot to the date string
        date = "\u2022 $date"
        holder.itemView.message_text_view.text = message
        holder.itemView.author_text_view.text = author
        holder.itemView.date_text_view.text = date
        when (authorKey) {
            SquawkContract.ASSER_KEY -> Glide.with(context).load(R.drawable.asser).into(holder.itemView.author_image_view)
            SquawkContract.CEZANNE_KEY -> Glide.with(context).load(R.drawable.cezanne).into(holder.itemView.author_image_view)
            SquawkContract.JLIN_KEY -> Glide.with(context).load(R.drawable.jlin).into(holder.itemView.author_image_view)
            SquawkContract.LYLA_KEY -> Glide.with(context).load(R.drawable.lyla).into(holder.itemView.author_image_view)
            SquawkContract.NIKITA_KEY -> Glide.with(context).load(R.drawable.nikita).into(holder.itemView.author_image_view)
            else -> Glide.with(context).load(R.drawable.test).into(holder.itemView.author_image_view)
        }
    }


    fun swapCursor(newCursor: Cursor?) {
        mData = newCursor
        notifyDataSetChanged()
    }


    companion object {
        private val sDateFormat = SimpleDateFormat("dd MMM")
        private const val MINUTE_MILLIS = (1000 * 60).toLong()
        private const val HOUR_MILLIS = 60 * MINUTE_MILLIS
        private const val DAY_MILLIS = 24 * HOUR_MILLIS
    }

    override fun getItemCount(): Int {
        return if (null == mData) 0 else mData!!.count
    }
}
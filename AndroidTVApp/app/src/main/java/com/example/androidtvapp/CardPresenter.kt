package com.example.androidtvapp

import android.graphics.drawable.Drawable
import androidx.leanback.widget.ImageCardView
import androidx.leanback.widget.Presenter
import androidx.core.content.ContextCompat
import android.util.Log
import android.view.ViewGroup

import com.bumptech.glide.Glide
import kotlin.properties.Delegates

/**
 * A CardPresenter is used to generate Views and bind Objects to them on demand.
 * It contains an ImageCardView.
 */
class CardPresenter : Presenter() {
    private var mDefaultCardImage: Drawable? = null
    private var sSelectedBackgroundColor: Int by Delegates.notNull()
    private var sDefaultBackgroundColor: Int by Delegates.notNull()

    override fun onCreateViewHolder(parent: ViewGroup): ViewHolder {
        Log.d(TAG, "onCreateViewHolder")

        sDefaultBackgroundColor = ContextCompat.getColor(parent.context, R.color.default_background)
        sSelectedBackgroundColor =
            ContextCompat.getColor(parent.context, R.color.selected_background)
        mDefaultCardImage = ContextCompat.getDrawable(parent.context, R.drawable.movie)

        val cardView = object : ImageCardView(parent.context) {
            override fun setSelected(selected: Boolean) {
//                updateCardBackgroundColor(this, selected)
                super.setSelected(selected)
            }
        }
        cardView.isFocusable = true
        cardView.isFocusableInTouchMode = true
//        updateCardBackgroundColor(cardView, false)
        return ViewHolder(cardView)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, item: Any) {
        //1
        val videoItem = item as VideoItem
        val video = videoItem.video

//2
        val cardView = viewHolder?.view as ImageCardView
        cardView.titleText = video.title
        cardView.contentText = video.channel
        cardView.setMainImageDimensions(CARD_WIDTH, CARD_HEIGHT)

//3
        loadBitmapIntoImageView(
            cardView.context,
            video.cardImageUrl,
            R.drawable.movie,
            cardView.mainImageView
        )

    }

    override fun onUnbindViewHolder(viewHolder: ViewHolder) {
        val cardView = viewHolder.view as ImageCardView
        // Remove references to images so that the garbage collector can free up memory
        cardView.badgeImage = null
        cardView.mainImage = null
    }

//    private fun updateCardBackgroundColor(view: ImageCardView, selected: Boolean) {
//        val color = if (selected) sSelectedBackgroundColor else sDefaultBackgroundColor
//        // Both background colors should be set because the view"s background is temporarily visible
//        // during animations.
//        view.setBackgroundColor(color)
//        view.setInfoAreaBackgroundColor(color)
//    }

    companion object {
        private val TAG = "CardPresenter"

        private val CARD_WIDTH = 313
        private val CARD_HEIGHT = 176
    }
}
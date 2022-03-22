package com.example.tvapp

import android.view.ViewGroup
import androidx.leanback.widget.ImageCardView
import androidx.leanback.widget.Presenter

class CatalogCardPresenter : Presenter() {
    override fun onCreateViewHolder(parent: ViewGroup?): ViewHolder {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(viewHolder: ViewHolder?, item: Any?) {
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

    override fun onUnbindViewHolder(viewHolder: ViewHolder?) {
        val cardView = viewHolder?.view as ImageCardView
// Remove references to images so that the garbage collector can free up memory
        cardView.badgeImage = null
        cardView.mainImage = null
    }
}
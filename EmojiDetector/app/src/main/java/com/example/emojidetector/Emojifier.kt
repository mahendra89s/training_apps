package com.example.emojidetector

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.util.Log


import android.util.SparseArray
import android.widget.Toast
import com.google.android.gms.vision.Frame
import com.google.android.gms.vision.face.Face
import timber.log.Timber


class Emojifier {

    private val EMOJI_SCALE_FACTOR = .9f
    private val SMILING_PROB_THRESHOLD = .15
    private val EYE_OPEN_PROB_THRESHOLD = .5

    /**
     * Method for detecting faces in a bitmap, and drawing emoji depending on the facial
     * expression.
     *
     * @param context The application context.
     * @param picture The picture in which to detect the faces.
     */
    fun detectFacesandOverlayEmoji(context: Context, picture: Bitmap): Bitmap? {

        val detector = com.google.android.gms.vision.face.FaceDetector.Builder(context)
            .setTrackingEnabled(false)
            .setClassificationType(com.google.android.gms.vision.face.FaceDetector.ALL_CLASSIFICATIONS)
            .build()

        val frame = Frame.Builder().setBitmap(picture).build()

        val faces: SparseArray<Face> = detector.detect(frame)

        var resultBitap = picture

        if(faces.size() == 0){
            Toast.makeText(context, "no face detected", Toast.LENGTH_SHORT).show();
        }else
        {
            for (item in 0 until faces.size()){
                val face = faces.valueAt(item)
                var emojibitmap : Bitmap ?= null

                when(whichEmoji(face)){
                    Emoji.SMILE -> emojibitmap = BitmapFactory.decodeResource(context.resources,R.drawable.smile)
                    Emoji.FROWN -> emojibitmap = BitmapFactory.decodeResource(context.resources,R.drawable.frown)
                    Emoji.LEFT_WINK -> emojibitmap = BitmapFactory.decodeResource(context.resources,R.drawable.leftwink)
                    Emoji.RIGHT_WINK -> emojibitmap = BitmapFactory.decodeResource(context.resources,R.drawable.rightwink)
                    Emoji.LEFT_WINK_FROWN-> emojibitmap = BitmapFactory.decodeResource(context.resources,R.drawable.leftwink)
                    Emoji.RIGHT_WINK_FROWN->emojibitmap = BitmapFactory.decodeResource(context.resources,R.drawable.rightwinkfrown)
                    Emoji.CLOSED_EYE_SMILE->emojibitmap = BitmapFactory.decodeResource(context.resources,R.drawable.closed_smile)
                    Emoji.CLOSED_EYE_FROWN->emojibitmap = BitmapFactory.decodeResource(context.resources,R.drawable.closed_frown)
                    else -> emojibitmap = null

                }
                resultBitap = addBitmapToFace(resultBitap,emojibitmap,face)
            }

        }
        detector.release()
        return resultBitap
    }


    private fun whichEmoji(face: Face): Emoji? {

        val smiling: Boolean = face.isSmilingProbability > SMILING_PROB_THRESHOLD
        val leftEyeClosed: Boolean = face.isLeftEyeOpenProbability < EYE_OPEN_PROB_THRESHOLD
        val rightEyeClosed: Boolean = face.isRightEyeOpenProbability < EYE_OPEN_PROB_THRESHOLD


        // Determine and log the appropriate emoji
        val emoji: Emoji
        if (smiling) {
            if (leftEyeClosed && !rightEyeClosed) {
                emoji = Emoji.LEFT_WINK
            } else if (rightEyeClosed && !leftEyeClosed) {
                emoji = Emoji.RIGHT_WINK
            } else if (leftEyeClosed) {
                emoji = Emoji.CLOSED_EYE_SMILE
            } else {
                emoji = Emoji.SMILE
            }
        } else {
            if (leftEyeClosed && !rightEyeClosed) {
                emoji = Emoji.LEFT_WINK_FROWN
            } else if (rightEyeClosed && !leftEyeClosed) {
                emoji = Emoji.RIGHT_WINK_FROWN
            } else if (leftEyeClosed) {
                emoji = Emoji.CLOSED_EYE_FROWN
            } else {
                emoji = Emoji.FROWN
            }
        }
        return emoji
    }


    private fun addBitmapToFace(
        backgroundBitmap: Bitmap,
        emojiBitmap: Bitmap?,
        face: Face
    ): Bitmap {

        var emojiBitmap = emojiBitmap
        val resultBitmap = Bitmap.createBitmap(
            backgroundBitmap.width,
            backgroundBitmap.height, backgroundBitmap.config
        )

        val scaleFactor = EMOJI_SCALE_FACTOR

        val newEmojiWidth = (face.width * scaleFactor).toInt()
        val newEmojiHeight = (emojiBitmap!!.height *
                newEmojiWidth / emojiBitmap.width * scaleFactor).toInt()

        emojiBitmap = Bitmap.createScaledBitmap((emojiBitmap), newEmojiWidth, newEmojiHeight, false)

        val emojiPositionX: Float =
            (face.position.x + face.width / 2) - emojiBitmap.width / 2
        val emojiPositionY: Float =
            (face.position.y + face.height / 2) - emojiBitmap.height / 3

        val canvas = Canvas(resultBitmap)
        canvas.drawBitmap(backgroundBitmap, 0f, 0f, null)
        canvas.drawBitmap(emojiBitmap, emojiPositionX, emojiPositionY, null)
        return resultBitmap
    }

    private enum class Emoji {
        SMILE, FROWN, LEFT_WINK, RIGHT_WINK, LEFT_WINK_FROWN, RIGHT_WINK_FROWN, CLOSED_EYE_SMILE, CLOSED_EYE_FROWN
    }
}
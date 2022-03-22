package com.example.fcm_training.fcm

import android.annotation.SuppressLint
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.ContentValues
import android.content.Intent
import android.media.RingtoneManager
import android.os.AsyncTask
import android.util.Log
import androidx.core.app.NotificationCompat
import com.example.fcm_training.MainActivity
import com.example.fcm_training.R
import com.example.fcm_training.provider.SquawkContract
import com.example.fcm_training.provider.SquawkProvider
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class SquawkFirebaseMessageService : FirebaseMessagingService() {

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        // There are two types of messages data messages and notification messages. Data messages
        // are handled
        // here in onMessageReceived whether the app is in the foreground or background. Data
        // messages are the type
        // traditionally used with FCM. Notification messages are only received here in
        // onMessageReceived when the app
        // is in the foreground. When the app is in the background an automatically generated
        // notification is displayed.
        // When the user taps on the notification they are returned to the app. Messages
        // containing both notification
        // and data payloads are treated as notification messages. The Firebase console always
        // sends notification
        // messages. For more see: https://firebase.google.com/docs/cloud-messaging/concept-options\

        // The Squawk server always sends just *data* messages, meaning that onMessageReceived when
        // the app is both in the foreground AND the background
        Log.d(LOG_TAG, "From: " + remoteMessage.from)

        // Check if message contains a data payload.
        val data = remoteMessage.data
        if (data.size > 0) {
            Log.d(
                LOG_TAG,
                "Message data payload: $data"
            )

            // Send a notification that you got a new message
            sendNotification(data)
            insertSquawk(data)
        }
    }

    /**
     * Inserts a single squawk into the database;
     *
     * @param data Map which has the message data in it
     */
    private fun insertSquawk(data: Map<String, String>) {

        // Database operations should not be done on the main thread
        val insertSquawkTask: AsyncTask<Void?, Void?, Void?> =
            @SuppressLint("StaticFieldLeak")
            object : AsyncTask<Void?, Void?, Void?>() {
                override fun doInBackground(vararg p0: Void?): Void? {
                    val newMessage = ContentValues()
                    newMessage.put(
                        SquawkContract.COLUMN_AUTHOR,
                        data[JSON_KEY_AUTHOR]
                    )
                    newMessage.put(SquawkContract.COLUMN_MESSAGE, data[JSON_KEY_MESSAGE]!!
                        .trim { it <= ' ' })
                    newMessage.put(
                        SquawkContract.COLUMN_DATE,
                        data[JSON_KEY_DATE]
                    )
                    newMessage.put(
                        SquawkContract.COLUMN_AUTHOR_KEY,
                        data[JSON_KEY_AUTHOR_KEY]
                    )
                    contentResolver.insert(SquawkProvider.SquawkMessages.CONTENT_URI, newMessage)
                    return null
                }
            }
        insertSquawkTask.execute()
    }

    /**
     * Create and show a simple notification containing the received FCM message
     *
     * @param data Map which has the message data in it
     */
    private fun sendNotification(data: Map<String, String>) {
        val intent = Intent(this, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        // Create the pending intent to launch the activity
        val pendingIntent = PendingIntent.getActivity(
            this, 0 /* Request code */, intent,
            PendingIntent.FLAG_ONE_SHOT
        )
        val author = data[JSON_KEY_AUTHOR]
        var message = data[JSON_KEY_MESSAGE]

        // If the message is longer than the max number of characters we want in our
        // notification, truncate it and add the unicode character for ellipsis
        if (message!!.length > NOTIFICATION_MAX_CHARACTERS) {
            message = message.substring(0, NOTIFICATION_MAX_CHARACTERS) + "\u2026"
        }
        val defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val notificationBuilder: NotificationCompat.Builder = NotificationCompat.Builder(this)
            .setSmallIcon(R.drawable.ic_duck)
            .setContentTitle(String.format(getString(R.string.notification_message), author))
            .setContentText(message)
            .setAutoCancel(true)
            .setSound(defaultSoundUri)
            .setContentIntent(pendingIntent)
        val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build())
    }

    companion object {
        private val JSON_KEY_AUTHOR: String = SquawkContract.COLUMN_AUTHOR
        private val JSON_KEY_AUTHOR_KEY: String = SquawkContract.COLUMN_AUTHOR_KEY
        private val JSON_KEY_MESSAGE: String = SquawkContract.COLUMN_MESSAGE
        private val JSON_KEY_DATE: String = SquawkContract.COLUMN_DATE
        private const val NOTIFICATION_MAX_CHARACTERS = 30
        private val LOG_TAG = SquawkFirebaseMessageService::class.java.simpleName
    }
}
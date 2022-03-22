//package com.example.fcm_training.fcm
//
//import android.util.Log
//import com.google.android.gms.tasks.OnSuccessListener
//
//
//
//
//class SquawkFirebaseInstanceIdService : FirebaseInstanceIdService {
//
//    val LOG_TAG : String = "Hello"
//
//
//    fun onTokenRefresh() {
//        // Get updated InstanceID token.
//        val refreshedToken: String =
//            FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener(this@MyActivity,
//                OnSuccessListener<Any> { instanceIdResult ->
//                    val updatedToken: String = instanceIdResult.getToken()
//                    Log.e("Updated Token", updatedToken)
//                })
//        Log.d(LOG_TAG, "Refreshed token: $refreshedToken")
//
//        // If you want to send messages to this application instance or
//        // manage this apps subscriptions on the server side, send the
//        // Instance ID token to your app server.
//        sendRegistrationToServer(refreshedToken)
//    }
//
//    /**
//     * Persist token to third-party servers.
//     *
//     *
//     * Modify this method to associate the user's FCM InstanceID token with any server-side account
//     * maintained by your application.
//     *
//     * @param token The new token.
//     */
//    open fun sendRegistrationToServer(token: String) {
//        // This method is blank, but if you were to build a server that stores users token
//        // information, this is where you'd send the token to the server.
//    }
//}

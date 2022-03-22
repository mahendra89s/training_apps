package com.example.fcm_training

import android.content.ContentValues.TAG
import android.content.Intent
import android.database.Cursor
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.loader.app.LoaderManager
import androidx.loader.content.CursorLoader
import androidx.loader.content.Loader
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fcm_training.following.FollowingPreferenceActivity
import com.example.fcm_training.provider.SquawkContract
import com.example.fcm_training.provider.SquawkProvider
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.messaging.FirebaseMessaging
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity(), LoaderManager.LoaderCallbacks<Cursor?> {

    var mAdapter: SquawkAdapter? = null
    var firebaseToken : String? = null
    protected override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        // Use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        squawks_recycler_view.setHasFixedSize(true)

        // Use a linear layout manager
        val mLayoutManager = LinearLayoutManager(this)
        squawks_recycler_view.setLayoutManager(mLayoutManager)

        // Add dividers
        val dividerItemDecoration = DividerItemDecoration(
            squawks_recycler_view.context,
            mLayoutManager.orientation
        )
        squawks_recycler_view.addItemDecoration(dividerItemDecoration)

        // Specify an adapter
        mAdapter = SquawkAdapter(this)
        squawks_recycler_view.adapter = mAdapter

        // Start the loader
        getSupportLoaderManager().initLoader(LOADER_ID_MESSAGES, null, this)

        val extras : Bundle? = intent.extras
        if( extras != null && extras.containsKey("test")){
            Log.d("test", extras.getString("test").toString())
        }
        val token : Task<String> = FirebaseMessaging.getInstance().token.addOnCompleteListener {
            if(it.isComplete){
                firebaseToken = it.result.toString()
                Log.d("Tag", firebaseToken!!)
            }
        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == R.id.action_following_preferences) {
            // Opens the following activity when the menu icon is pressed
            val startFollowingActivity = Intent(this, FollowingPreferenceActivity::class.java)
            startActivity(startFollowingActivity)
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    /**
     * Loader callbacks
     */
    override fun onCreateLoader(id: Int, args: Bundle?): Loader<Cursor?> {
        // This method generates a selection off of only the current followers
        val selection: String? = SquawkContract.createSelectionForCurrentFollowers(
            PreferenceManager.getDefaultSharedPreferences(this)
        )
        Log.d(LOG_TAG, "Selection is $selection")
        return CursorLoader(
            this, SquawkProvider.SquawkMessages.CONTENT_URI,
            MESSAGES_PROJECTION, selection, null, SquawkContract.COLUMN_DATE.toString() + " DESC"
        )
    }


    companion object {
        private val LOG_TAG = MainActivity::class.java.simpleName
        private const val LOADER_ID_MESSAGES = 0
        val MESSAGES_PROJECTION = arrayOf<String>(
            SquawkContract.COLUMN_AUTHOR,
            SquawkContract.COLUMN_MESSAGE,
            SquawkContract.COLUMN_DATE,
            SquawkContract.COLUMN_AUTHOR_KEY
        )
        const val COL_NUM_AUTHOR = 0
        const val COL_NUM_MESSAGE = 1
        const val COL_NUM_DATE = 2
        const val COL_NUM_AUTHOR_KEY = 3
    }

    override fun onLoadFinished(loader: Loader<Cursor?>, data: Cursor?) {
        mAdapter!!.swapCursor(data)
    }

    override fun onLoaderReset(loader: Loader<Cursor?>) {
        mAdapter!!.swapCursor(null)
    }
}

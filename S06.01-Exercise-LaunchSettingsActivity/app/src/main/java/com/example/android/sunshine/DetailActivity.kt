package com.example.android.sunshine

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ShareCompat

class DetailActivity : AppCompatActivity() {
    private var mForecast: String? = null
    private var mWeatherDisplay: TextView? = null
    protected override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        actionBar?.setDisplayHomeAsUpEnabled(true)
        mWeatherDisplay = findViewById<TextView>(R.id.tv_display_weather)
        val intentThatStartedThisActivity: Intent = intent
        if (intentThatStartedThisActivity.hasExtra(Intent.EXTRA_TEXT)) {
            mForecast = intentThatStartedThisActivity.getStringExtra(Intent.EXTRA_TEXT)
            mWeatherDisplay?.text = mForecast
        }
    }

    /**
     * Uses the ShareCompat Intent builder to create our Forecast intent for sharing. We set the
     * type of content that we are sharing (just regular text), the text itself, and we return the
     * newly created Intent.
     *
     * @return The Intent to use to start our share.
     */
    private fun createShareForecastIntent(): Intent {
        return ShareCompat.IntentBuilder.from(this)
            .setType("text/plain")
            .setText(mForecast + FORECAST_SHARE_HASHTAG)
            .intent
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.detail, menu)
        val menuItem = menu.findItem(R.id.action_share)
        menuItem.intent = createShareForecastIntent()
        return true
    } // TODO (7) Launch SettingsActivity when the Settings option is clicked

    companion object {
        private const val FORECAST_SHARE_HASHTAG = " #SunshineApp"
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.action_settings){
            Intent(this,SettingsActivity::class.java).also {
                startActivity(it)
            }
        }
        return super.onOptionsItemSelected(item)
    }
}
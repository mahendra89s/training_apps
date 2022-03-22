package com.example.emojidetector

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.NonNull
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnClick
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.activity_main.*
import timber.log.Timber
import java.io.File
import java.io.IOException

class MainActivity : AppCompatActivity() {
    private val REQUEST_IMAGE_CAPTURE = 1
    private val REQUEST_STORAGE_PERMISSION = 1

    private val FILE_PROVIDER_AUTHORITY = "com.example.emojidetector.fileprovider"


    var mTempPhotoPath: String? = null

    var mResultsBitmap: Bitmap? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Bind the views
        ButterKnife.bind(this)

        // Set up Timber

        // Set up Timber
        Timber.plant(Timber.DebugTree())
        emojify_button.setOnClickListener {
            // Check for the external storage permission
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED
            ) {

                // If you do not have permission, request it
                ActivityCompat.requestPermissions(
                    this, arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                    REQUEST_STORAGE_PERMISSION
                )
            } else {
                // Launch the camera if the permission exists
                launchCamera()
            }
        }
        save_button.setOnClickListener{
            // Delete the temporary image file
            BitmapUtils().deleteImageFile(this, mTempPhotoPath)

            // Save the image
            BitmapUtils().saveImage(this, mResultsBitmap!!)
        }
        share_button.setOnClickListener {
            // Delete the temporary image file
            BitmapUtils().deleteImageFile(this, mTempPhotoPath)

            // Save the image
            BitmapUtils().saveImage(this, mResultsBitmap!!)

            // Share the image
            BitmapUtils().shareImage(this, mTempPhotoPath)
        }
        clear_button.setOnClickListener {
            // Clear the image and toggle the view visibility
            image_view.setImageResource(0)
            emojify_button.visibility = View.VISIBLE
            title_text_view.visibility = View.VISIBLE
            share_button.visibility = View.GONE
            save_button.visibility = View.GONE
            clear_button.visibility = View.GONE

            // Delete the temporary image file
            BitmapUtils().deleteImageFile(this, mTempPhotoPath)
        }
    }

    /**
     * OnClick method for "Emojify Me!" Button. Launches the camera app.
     */

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        // Called when you request permission to read and write to external storage
        when (requestCode) {
            REQUEST_STORAGE_PERMISSION -> {
                if (grantResults.size > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED
                ) {
                    // If you get permission, launch the camera
                    launchCamera()
                } else {
                    // If you do not get permission, show a Toast
                    Toast.makeText(this, R.string.permission_denied, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    /**
     * Creates a temporary image file and captures a picture to store in it.
     */
    private fun launchCamera() {

        // Create the capture image intent
        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)

        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(packageManager) != null) {
            // Create the temporary File where the photo should go
            var photoFile: File? = null
            try {
                photoFile = BitmapUtils().createTempImageFile(this)
            } catch (ex: IOException) {
                // Error occurred while creating the File
                ex.printStackTrace()
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {

                // Get the path of the temporary file
                mTempPhotoPath = photoFile.absolutePath

                // Get the content URI for the image file
                val photoURI: Uri = FileProvider.getUriForFile(
                    this,
                    FILE_PROVIDER_AUTHORITY,
                    photoFile
                )

                // Add the URI so the camera can store the image
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)

                // Launch the camera activity
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
            }
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        // If the image capture activity was called and was successful
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            // Process the image and set it to the TextView
            processAndSetImage()
        } else {

            // Otherwise, delete the temporary image file
            BitmapUtils().deleteImageFile(this, mTempPhotoPath)
        }
    }

    /**
     * Method for processing the captured image and setting it to the TextView.
     */
    private fun processAndSetImage() {

        // Toggle Visibility of the views
        emojify_button.visibility = View.GONE
        title_text_view.visibility = View.GONE
        save_button.visibility = View.VISIBLE
        share_button.visibility = View.VISIBLE
        clear_button.visibility = View.VISIBLE

        // Resample the saved image to fit the ImageView

        mResultsBitmap = BitmapUtils().resamplePic(this, mTempPhotoPath)


        // Detect the faces and overlay the appropriate emoji
        mResultsBitmap = Emojifier().detectFacesandOverlayEmoji(this, mResultsBitmap!!)

        // Set the new bitmap to the ImageView
        image_view.setImageBitmap(mResultsBitmap)
    }

}
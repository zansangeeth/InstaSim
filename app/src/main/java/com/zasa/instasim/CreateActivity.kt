package com.zasa.instasim

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_create.*

private const val TAG = "CreateActivity"
private const val PICK_PHOTO_CODE = 1234

class CreateActivity : AppCompatActivity() {

    private var photoUri : Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create)

        btnPickImage.setOnClickListener {
            Log.i(TAG, "Open up image picker on gallery")
            val imagePickerIntent = Intent(Intent.ACTION_GET_CONTENT)
            imagePickerIntent.type = "image/*"
            if (imagePickerIntent.resolveActivity(packageManager) != null) {
                startActivityForResult(Intent.createChooser(imagePickerIntent, "slect pic"), PICK_PHOTO_CODE)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_PHOTO_CODE){
            if (resultCode == Activity.RESULT_OK){
                photoUri = data?.data
                Log.i(TAG, "photo uri : $photoUri")
                ivUploadImage.setImageURI(photoUri)
            }else{
                Toast.makeText(this, "image pick action cancelled", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
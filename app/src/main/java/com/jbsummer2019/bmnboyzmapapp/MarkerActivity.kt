package com.jbsummer2019.bmnboyzmapapp

import android.app.PictureInPictureParams
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import kotlinx.android.synthetic.main.activity_marker.*
import android.content.Intent
import android.os.Environment
import android.provider.MediaStore
import android.os.Environment.getExternalStorageDirectory
import android.text.Html
import android.text.method.LinkMovementMethod
import android.util.Log
import kotlinx.android.synthetic.main.activity_marker.view.*
import java.io.File



class MarkerActivity : AppCompatActivity() {
    companion object {
        val IMAGE_KEY = "IMAGE_KEY"
    }

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_marker)

        name.text = intent.getStringExtra("title")
        text.text = intent.getStringExtra("text")

        web.setOnClickListener {
            val address = Uri.parse(intent.getStringExtra("giper_text"))
            val openLinkIntent = Intent(Intent.ACTION_VIEW, address)

            if (openLinkIntent.resolveActivity(packageManager) != null) {
                startActivity(openLinkIntent)
            } else {
                Log.d("Intent", "Не получается обработать намерение!")
            }
        }


        button_back.setOnClickListener {
            val intent = Intent(this, MapsActivity::class.java)
            startActivity(intent)
        }

        val imageId = intent.getIntExtra(IMAGE_KEY, 0)
        if (imageId != 0) {
            val drawable = resources.getDrawable(imageId)
            marker_picture.setImageDrawable(drawable)
        }

        button_like.setOnClickListener {
            if (intent.getBooleanExtra("like", false)) {
                SelectedArr.remove(intent.getStringExtra("title"))
                val currentMarkers = repository.searchByTitle(intent.getStringExtra("title"))
                currentMarkers.forEach {
                    it.like=false
                }
                
            }
            else{
                SelectedArr.add(intent.getStringExtra("title"))
                val currentMarkers = repository.searchByTitle(intent.getStringExtra("title"))
                currentMarkers.forEach {
                    it.like=true
                }
            }
        }
    }
}
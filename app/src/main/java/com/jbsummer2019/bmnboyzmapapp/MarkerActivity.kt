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
import android.util.Log
import java.io.File


class MarkerActivity : AppCompatActivity() {
    companion object {
        val IMAGE_KEY = "IMAGE_KEY"
    }

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_marker)

        name.text=intent.getStringExtra("title")
        text.text=intent.getStringExtra("text")



        button.setOnClickListener {
            val intent= Intent(this,MapsActivity::class.java)
            startActivity(intent)
}
//        val imageName = intent.getIntExtra("IMAGE_KEY")
//        val imageId = resources.getIdentifier("res/drawable/chiz", null, packageName)
        val imageId = intent.getIntExtra(IMAGE_KEY, 0)
        if(imageId != 0) {
            val drawable = resources.getDrawable(imageId)
            marker_picture.setImageDrawable(drawable)

        
    }
}

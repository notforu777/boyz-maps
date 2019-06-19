package com.jbsummer2019.bmnboyzmapapp

import android.app.PictureInPictureParams
import android.content.ContentValues
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
import com.jbsummer2019.bmnboyzmapapp.entity.DBHandler
import com.jbsummer2019.bmnboyzmapapp.entity.MarkerRepository
import java.io.File



class MarkerActivity : AppCompatActivity() {
    companion object {
        val IMAGE_KEY = "IMAGE_KEY"
    }



    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_marker)
        var localDB = DBHandler(this)

        name.text = intent.getStringExtra("title")
        text.text = intent.getStringExtra("text")


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

                    var values = ContentValues()
                    values.put(DBHandler.placePosition1, it.position1)
                    values.put(DBHandler.placePosition2, it.position2)
                    values.put(DBHandler._id, it.id)
                    values.put(DBHandler.placeTitle, it.title)
                    values.put(DBHandler.placeText, it.text)
                    values.put(DBHandler.placeLike, it.like)
                    values.put(DBHandler.placeIconImageID, it.iconimageId)
                    values.put(DBHandler.placeImageID, it.imageId)

                    localDB.updatePlace(values, it.id)
                }
                
            }
            else{
                SelectedArr.add(intent.getStringExtra("title"))
                val currentMarkers = repository.searchByTitle(intent.getStringExtra("title"))
                currentMarkers.forEach {
                    it.like=true


                    var values = ContentValues()
                    values.put(DBHandler.placePosition1, it.position1)
                    values.put(DBHandler.placePosition2, it.position2)
                    values.put(DBHandler._id, it.id)
                    values.put(DBHandler.placeTitle, it.title)
                    values.put(DBHandler.placeText, it.text)
                    values.put(DBHandler.placeLike, it.like)
                    values.put(DBHandler.placeIconImageID, it.iconimageId)
                    values.put(DBHandler.placeImageID, it.imageId)

                    localDB.updatePlace(values, it.id)
                }
            }
        }
    }
}
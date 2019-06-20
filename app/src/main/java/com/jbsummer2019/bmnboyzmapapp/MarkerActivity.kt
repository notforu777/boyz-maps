package com.jbsummer2019.bmnboyzmapapp

import android.content.ContentValues
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_marker.*
import android.content.Intent
import android.util.Log
import com.jbsummer2019.bmnboyzmapapp.entity.DBHandler


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
            val currentMarkers = localDB.listPlacesByTitle(intent.getStringExtra("title"))
            Log.d("debug", "${currentMarkers.size}")
                currentMarkers.forEach {
                    Log.d("COOL_DEBUG", "${it.title} like clicked")

                    var values = ContentValues()
                    values.put(DBHandler.placePosition1, it.position1)
                    values.put(DBHandler.placePosition2, it.position2)
                    values.put(DBHandler._id, it.id)
                    values.put(DBHandler.placeTitle, it.title)
                    values.put(DBHandler.placeText, it.text)
                    values.put(DBHandler.placeLike, (!it.like).toString())
                    values.put(DBHandler.placeIconImageID, it.iconimageId)
                    values.put(DBHandler.placeImageID, it.imageId)

                    localDB.updatePlace(values, it.id)

                    localDB.listPlacesByLike("%").forEach {
                        Log.d("COOL_DEBUG", it.title + it.like.toString())
                    }



            }
        }
    }
}
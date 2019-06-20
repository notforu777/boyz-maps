package com.jbsummer2019.bmnboyzmapapp

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import com.jbsummer2019.bmnboyzmapapp.entity.DBHandler
import com.jbsummer2019.bmnboyzmapapp.entity.MarkerEntity
import kotlinx.android.synthetic.main.activity_routes.*
import kotlinx.android.synthetic.main.activity_routes.list_view
import kotlinx.android.synthetic.main.activity_selected.*

class LikedActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_selected)


        val testData = DBHandler(this).listPlacesByLike("true").toStringList()
        testData.forEach {
            Log.d("COOL_DEBUG", "${it} is  in liked")
        }

        val adapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, testData)
        list_view.adapter=adapter

        button_back_from_favourites.setOnClickListener {
            val intent = Intent(this, MapsActivity::class.java)
            startActivity(intent)
        }
        Log.d("debug", "onCreate")
    }
}

fun ArrayList<MarkerEntity>.toStringList() : ArrayList<String> {
    var mutableList = ArrayList<String>()
    this.forEach {
        mutableList.add(it.title)
    }
    return  mutableList
}

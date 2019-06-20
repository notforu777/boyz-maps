package com.jbsummer2019.bmnboyzmapapp

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import kotlinx.android.synthetic.main.activity_routes.*

var SelectedArr : MutableList<String> = mutableListOf()

class selected : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_selected)

        val adapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, SelectedArr)
        list_view.adapter=adapter

        button.setOnClickListener {
            val intent = Intent(this, MapsActivity::class.java)
            startActivity(intent)
        }
        Log.d("debug", "onCreate")
    }
}

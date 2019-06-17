package com.jbsummer2019.bmnboyzmapapp

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_marker.*
import kotlinx.android.synthetic.main.activity_menu.*
import kotlinx.android.synthetic.main.activity_menu.button

class menu : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)


        button_route.setOnClickListener {
            val intent=Intent(this,routes::class.java)
            startActivity(intent)
        }
        button.setOnClickListener {
            val intent= Intent(this,MapsActivity::class.java)
            startActivity(intent)
        }
    }
}

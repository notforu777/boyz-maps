package com.jbsummer2019.bmnboyzmapapp

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import kotlinx.android.synthetic.main.activity_menu.*
import kotlinx.android.synthetic.main.activity_style_map_activity.*
import kotlinx.android.synthetic.main.activity_style_map_activity.button_back_from_styles_menu

class Style_map_activity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_style_map_activity)

        button_style_4.setOnClickListener {
            val intent= Intent(this,MapsActivity::class.java)
            intent.putExtra("Style","dark")
            startActivity(intent)
        }

        button_style_2.setOnClickListener {
            val intent= Intent(this,MapsActivity::class.java)
            intent.putExtra("Style","silver")
            startActivity(intent)
        }

        button_style_3.setOnClickListener {
            val intent= Intent(this,MapsActivity::class.java)
            intent.putExtra("Style","retro")
            startActivity(intent)
        }

        button_style_5.setOnClickListener {
            val intent= Intent(this,MapsActivity::class.java)
            intent.putExtra("Style","night")
            startActivity(intent)
        }

        button_style_6.setOnClickListener {
            val intent= Intent(this,MapsActivity::class.java)
            intent.putExtra("Style","aubergine")
            startActivity(intent)
        }

        button_style_1.setOnClickListener {
            val intent= Intent(this,MapsActivity::class.java)
            intent.putExtra("Style","standart")
            startActivity(intent)
        }

        button_back_from_styles_menu.setOnClickListener {
            val intent= Intent(this,Menu::class.java)
            startActivity(intent)
        }
    }
}

package com.jbsummer2019.bmnboyzmapapp

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import kotlinx.android.synthetic.main.activity_marker.*
import kotlinx.android.synthetic.main.activity_routes.*
import kotlinx.android.synthetic.main.activity_routes.button

class routes : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_routes)

        val dataArr= arrayOf("Simple Crook", "True Boy", "Hard Boss")
        val adapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, dataArr)
        list_view.adapter=adapter
        button.setOnClickListener {
            val intent= Intent(this,menu::class.java)
            startActivity(intent)
        }
    }
}

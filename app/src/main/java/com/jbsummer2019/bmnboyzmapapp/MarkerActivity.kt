package com.jbsummer2019.bmnboyzmapapp

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_marker.*

class MarkerActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_marker)

        name.text=intent.getStringExtra("title")
        text.text=intent.getStringExtra("text")
    }
}

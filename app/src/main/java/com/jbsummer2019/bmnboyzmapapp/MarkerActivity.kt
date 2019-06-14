package com.jbsummer2019.bmnboyzmapapp

import android.support.v7.app.AppCompatActivity
import android.os.Bundle

class MarkerActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chizh_pyzh)

        intent.getStringExtra("id")
    }
}

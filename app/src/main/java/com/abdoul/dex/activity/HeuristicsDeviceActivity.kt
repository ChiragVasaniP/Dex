package com.abdoul.dex.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import com.abdoul.dex.R

class HeuristicsDeviceActivity : AppCompatActivity() {

    lateinit var imgBackHur: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_heuristics_device)


        imgBackHur = findViewById(R.id.img_back_hur)

        imgBackHur.setOnClickListener {
            finish()
        }
    }
}
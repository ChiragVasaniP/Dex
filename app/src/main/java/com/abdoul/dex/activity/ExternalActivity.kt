package com.abdoul.dex.activity

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.abdoul.dex.R

class ExternalActivity : AppCompatActivity() {


    lateinit var continues: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_external)


        continues = findViewById(R.id.continues)

        continues.setOnClickListener {
            finish()
        }
    }
}
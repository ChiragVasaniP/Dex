package com.abdoul.dex.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import com.abdoul.dex.R

class SensesActivity : AppCompatActivity() {

    var back : ImageView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_senses)

        back = findViewById(R.id.back)
        back!!.setOnClickListener{
            finish()
        }
    }


}
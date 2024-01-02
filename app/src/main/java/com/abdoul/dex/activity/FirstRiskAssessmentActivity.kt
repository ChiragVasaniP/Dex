package com.abdoul.dex.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.abdoul.dex.R

class FirstRiskAssessmentActivity : AppCompatActivity() {
    var yestv : TextView? = null
    var notv : TextView? = null
    var continuing : TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_first_risk_assessment)
        yestv = findViewById(R.id.yestv)
        notv = findViewById(R.id.notv)
        continuing = findViewById(R.id.continuing)

        yestv!!.setOnClickListener {
//           yestv!!.setBackgroundColor(R.drawable.rounded_yellow)
//            notv!!.setBackgroundColor(R.drawable.rounded_white)
        }

        notv!!.setOnClickListener {
//            notv!!.setBackgroundColor(R.drawable.rounded_yellow)
//            yestv!!.setBackgroundColor(R.drawable.rounded_white)

        }
        continuing!!.setOnClickListener {
            val intent = Intent(this@FirstRiskAssessmentActivity, SecondRiskAssessmentActivity::class.java)
            startActivity(intent)
        }


    }
}
package com.abdoul.dex.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.abdoul.dex.R

class SecondRiskAssessmentActivity : AppCompatActivity() {
    var yestv : TextView? = null
    var notv : TextView? = null
    var continuing : TextView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second_risk_assessment)
        yestv = findViewById(R.id.yestv2)
        notv = findViewById(R.id.notv2)
        continuing = findViewById(R.id.continuing2)

        yestv!!.setOnClickListener {
//           yestv!!.setBackgroundColor(R.drawable.rounded_yellow)
//            notv!!.setBackgroundColor(R.drawable.rounded_white)
        }

        notv!!.setOnClickListener {
//            notv!!.setBackgroundColor(R.drawable.rounded_yellow)
//            yestv!!.setBackgroundColor(R.drawable.rounded_white)

        }
        continuing!!.setOnClickListener {
            val intent = Intent(this@SecondRiskAssessmentActivity, ThirdRiskAssessmentActivity::class.java)
            startActivity(intent)
        }
    }
}
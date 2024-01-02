package com.abdoul.dex.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.abdoul.dex.R

class ThirdRiskAssessmentActivity : AppCompatActivity() {
    var continual : TextView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_third_risk_assessment)
        continual = findViewById(R.id.continual)

        continual!!.setOnClickListener {
            val intent = Intent(this@ThirdRiskAssessmentActivity, RiskAssessmentCompletionActivity::class.java)
            startActivity(intent)
        }
    }
}
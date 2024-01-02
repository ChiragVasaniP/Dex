package com.abdoul.dex.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import com.abdoul.dex.R

class OptionalRiskAssessmentActivity : AppCompatActivity() {
    var no : TextView? = null
    var yes : TextView? = null
    var back : ImageView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_optional_risk_assessment)
        yes = findViewById(R.id.yes)
        no = findViewById(R.id.no)
        yes!!.setOnClickListener {
            val intent = Intent(this@OptionalRiskAssessmentActivity, FirstRiskAssessmentActivity::class.java)
            startActivity(intent)
        }
        no!!.setOnClickListener {
            val intent = Intent(this@OptionalRiskAssessmentActivity, BaseActivity::class.java)
            startActivity(intent)
        }

        back = findViewById(R.id.back)
        back!!.setOnClickListener{
            finish()
        }

    }
}
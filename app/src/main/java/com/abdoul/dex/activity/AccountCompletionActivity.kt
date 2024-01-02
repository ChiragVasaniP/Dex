package com.abdoul.dex.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.abdoul.dex.R

class AccountCompletionActivity : AppCompatActivity() {
    var yourprof: TextView? = null
    var counter = 0
    var lefTime = 4
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_account_completion)
        yourprof = findViewById(R.id.yourprof)

        val countTime: TextView = findViewById(R.id.txt_view_timer)


        object : CountDownTimer(5000, 1000) {
            @SuppressLint("StringFormatMatches")
            override fun onTick(millisUntilFinished: Long) {
                countTime.text = getString(R.string.counter, (lefTime - counter)).toString()
                counter++
            }

            override fun onFinish() {
                countTime.text = "Finished"
                val intent =
                    Intent(
                        this@AccountCompletionActivity,
                        OptionalRiskAssessmentActivity::class.java
                    )
                startActivity(intent)
                finish()
            }
        }.start()


    }
}


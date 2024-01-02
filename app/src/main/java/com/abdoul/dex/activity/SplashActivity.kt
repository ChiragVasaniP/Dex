package com.abdoul.dex.activity

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.abdoul.dex.R
import com.abdoul.dex.utils.SessionManager

class SplashActivity : AppCompatActivity() {
    var started: TextView? = null
    var signin: TextView? = null
    lateinit var llButtons: LinearLayout
    var sessionManager: SessionManager? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_splash)
        started = findViewById(R.id.started)
        signin = findViewById(R.id.signinsplash)
        llButtons = findViewById(R.id.ll_buttons)

        sessionManager = SessionManager(this@SplashActivity)

        if (sessionManager!!.getBooleanData(SessionManager.login)) {
            llButtons.isVisible = false
            Handler().postDelayed({
                val intent = Intent(this, BaseActivity::class.java)
                startActivity(intent)
                finish()
            }, 3000)
        }

        started!!.setOnClickListener {
            val intent = Intent(this@SplashActivity, SignupActivity::class.java)
            startActivity(intent)
        }
        signin!!.setOnClickListener {
            val intent = Intent(this@SplashActivity, SigninActivity::class.java)
            startActivity(intent)
        }
    }
}
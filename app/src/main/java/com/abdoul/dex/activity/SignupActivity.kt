package com.abdoul.dex.activity

import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.abdoul.dex.R
import com.abdoul.dex.utils.BasicMethods


class SignupActivity : AppCompatActivity() {
    var signup: TextView? = null
    var signin: TextView? = null
    lateinit var edtPasswordSignup: EditText
    lateinit var edtEmailSignUp: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)
        signin = findViewById(R.id.tosignin)
        signup = findViewById(R.id.txt_signup_button)

        edtPasswordSignup = findViewById(R.id.edt_password_signup)
        edtEmailSignUp = findViewById(R.id.edt_email_sign_up)

        edtEmailSignUp.setText("abc@test.com")
        edtPasswordSignup.setText("Abcd@test123")



        signin!!.setOnClickListener {
            val intent = Intent(this@SignupActivity, SigninActivity::class.java)
            startActivity(intent)

        }
        signup!!.setOnClickListener {
//            Toast.makeText(this, "CheckPress", Toast.LENGTH_LONG).show()
            registerApiCall()

        }
    }

    private fun registerApiCall() {
        var basicmethod: BasicMethods = BasicMethods()
        val strRegEmail = edtEmailSignUp.text.toString()
        val strRegPassword = edtPasswordSignup.text.toString()
        if (strRegEmail.isEmpty()) {
            edtEmailSignUp.error = "Please Enter Email Id"
        } else if (strRegPassword.isEmpty()) {
            edtPasswordSignup.error = "Please Enter Email Id"

        } else {
            if (!basicmethod.isEmailValid(strRegEmail)) {
                edtEmailSignUp.error = "Please Enter Valid Email Id"
            } else if (!basicmethod.isValidPassword(strRegPassword)) {
                edtPasswordSignup.error =getString(R.string.str_invalid_password)
                edtPasswordSignup.requestFocus()
            } else {
                val intent = Intent(this@SignupActivity, CreateAccountActivity::class.java)
                intent.putExtra("RegisterEmail", strRegEmail)
                intent.putExtra("RegisterPassword", strRegPassword)
                startActivity(intent)
            }

        }
    }

}
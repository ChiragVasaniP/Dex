package com.abdoul.dex.activity

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.TextUtils
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import cn.pedant.SweetAlert.SweetAlertDialog
import com.abdoul.dex.R
import com.abdoul.dex.models.BasicBodyModel
import com.abdoul.dex.retrofit.GetResult
import com.abdoul.dex.retrofit.RetrofitHelper
import com.abdoul.dex.utils.SessionManager
import com.google.gson.Gson
import com.google.gson.JsonObject

class ForgetPasswordActivity : AppCompatActivity(), GetResult.MyListener {

    lateinit var edtForgetPasEmail: EditText
    lateinit var txtEmailSubmit: TextView
    lateinit var txtBackLogin: TextView
    lateinit var pDialog: SweetAlertDialog
    lateinit var strEmail: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.forget_password)

        edtForgetPasEmail = findViewById(R.id.edt_forget_pas_email)
        txtEmailSubmit = findViewById(R.id.txt_email_submit)
        txtBackLogin = findViewById(R.id.txt_back_login)

        pDialog = SweetAlertDialog(this@ForgetPasswordActivity, SweetAlertDialog.PROGRESS_TYPE)
        pDialog.progressHelper.barColor = Color.parseColor("#A5DC86")
        pDialog.titleText = "Loading ..."
        pDialog.setCancelable(false)

        txtBackLogin.setOnClickListener {
            val intent = Intent(this, SigninActivity::class.java)
            startActivity(intent)
        }

        txtEmailSubmit.setOnClickListener {
            if (TextUtils.isEmpty(edtForgetPasEmail.text.toString())) {
                edtForgetPasEmail.error = "Please Enter Registered Email"
            } else {
                strEmail = edtForgetPasEmail.text.toString()
                callForgetPassword(strEmail)
//                val intent = Intent(this, VerifyCodeActivity::class.java)
//                intent.putExtra("forgetEmail",strEmail)
//                startActivity(intent)
//                finish()
            }
        }
    }

    private fun callForgetPassword(strEmail: String) {
        pDialog.show()

        val apiInterFace = RetrofitHelper.create("").forgetPassword(strEmail)
        val getResult = GetResult(this)
        getResult.setMyListener(this)
        getResult.callForLogin(apiInterFace, "ForgetPasswordApi", pDialog)

    }

    override fun callback(result: JsonObject?, callNo: String?, code: Int) {
        if (callNo.equals("ForgetPasswordApi", ignoreCase = true)) {
            val gson = Gson()
            if (code in 200..299) {
                val modelData = gson.fromJson(result, BasicBodyModel::class.java)
                pDialog.dismiss()
                var message: String
                if (modelData.data.isNullOrEmpty()) {
                    message = modelData.message.toString()
                } else {
                    message = modelData.data.toString()
                }
                message = message.replace("[", "")
                message = message.replace("]", "")
                pDialog.dismiss()

                var success: SweetAlertDialog =
                    SweetAlertDialog(this@ForgetPasswordActivity, SweetAlertDialog.SUCCESS_TYPE)
                success.titleText = "Otp Send successfully"
                success.contentText = message.toString()
                success.setCancelable(false)
                success.setConfirmButton("Okay", SweetAlertDialog.OnSweetClickListener {
                    val intent = Intent(this, VerifyCodeActivity::class.java)
                    intent.putExtra("forgetEmail", strEmail)
                    startActivity(intent)
                    success.dismiss()
                    finish()
                })
                    .show()

            } else if (code in 400..499) {
                val modelData = gson.fromJson(result, BasicBodyModel::class.java)
                pDialog.dismiss()
                var message: String
                if (modelData.data.isNullOrEmpty()) {
                    message = modelData.message.toString()
                } else {
                    message = modelData.data.toString()
                }
                message = message.replace("[", "")
                message = message.replace("]", "")
                pDialog.dismiss()
                SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE)
                    .setContentText(message.toString())
                    .setConfirmText("Okay")
                    .show()
            }
        }

    }
}
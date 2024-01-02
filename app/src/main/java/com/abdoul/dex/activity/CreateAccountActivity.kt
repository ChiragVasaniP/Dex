package com.abdoul.dex.activity

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import cn.pedant.SweetAlert.SweetAlertDialog
import com.abdoul.dex.R
import com.abdoul.dex.models.BasicBodyModel
import com.abdoul.dex.models.registerModel.RegisterMainModel
import com.abdoul.dex.retrofit.GetResult
import com.abdoul.dex.retrofit.RetrofitHelper
import com.abdoul.dex.utils.SessionManager
import com.google.gson.Gson
import com.google.gson.JsonObject

class CreateAccountActivity : AppCompatActivity(), GetResult.MyListener {
    var next: TextView? = null
    var back: ImageView? = null

    lateinit var edtFirstName: EditText
    lateinit var edtLastName: EditText
    lateinit var edtPhoneNumber: EditText
    lateinit var edtLocation: EditText
    lateinit var strRegisterEmail: String
    lateinit var strRegisterPassword: String
    lateinit var pDialog: SweetAlertDialog
    lateinit var sessionManager: SessionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_account)
        sessionManager = SessionManager(this)

        next = findViewById(R.id.next)
        back = findViewById(R.id.back)
        edtFirstName = findViewById(R.id.edt_first_name)
        edtLastName = findViewById(R.id.edt_last_name)
        edtPhoneNumber = findViewById(R.id.edt_phone_number)
        edtLocation = findViewById(R.id.edt_location)

        pDialog = SweetAlertDialog(this@CreateAccountActivity, SweetAlertDialog.PROGRESS_TYPE)
        pDialog.progressHelper.barColor = Color.parseColor("#A5DC86")
        pDialog.titleText = "Loading ..."
        pDialog.setCancelable(false)

        strRegisterEmail = intent.getStringExtra("RegisterEmail").toString()
        strRegisterPassword = intent.getStringExtra("RegisterPassword").toString()

        next!!.setOnClickListener {
            nextActivity()
        }


        back!!.setOnClickListener {
            finish()
        }
    }

    private fun nextActivity() {
        val strFirstName = edtFirstName.text.toString()
        val strLastName = edtLastName.text.toString()
        val strPhoneNumber = edtPhoneNumber.text.toString()
        val strLocation = edtLocation.text.toString()

        if (strFirstName.isEmpty()) {
            edtFirstName.error = "Enter First Name"
            edtFirstName.requestFocus();
        }
        if (strLastName.isEmpty()) {
            edtLastName.error = "Eknter Last Name"
            edtLastName.requestFocus();
        }
        if (strPhoneNumber.isEmpty()) {
            edtPhoneNumber.error = "Eknter Last Name"
            edtPhoneNumber.requestFocus();
        }
        if (strLocation.isEmpty()) {
            edtLocation.error = "Eknter Last Name"
            edtLocation.requestFocus();
        }

        if (strFirstName.isNotBlank() && strLastName.isNotEmpty() && strPhoneNumber.isNotEmpty() && strLocation.isNotEmpty()) {
            callApiRegister(strFirstName, strLastName, strPhoneNumber, strLocation)
        }
    }

    private fun callApiRegister(
        strFirstName: String,
        strLastName: String,
        strPhoneNumber: String,
        strLocation: String
    ) {
        pDialog.show()
        val strFullName = "$strFirstName $strLastName"
        val apiInterface = RetrofitHelper.create("").registerUser(
            strFullName,
            strRegisterEmail,
            strRegisterPassword,
            strRegisterPassword,
            "", strPhoneNumber
        )
        val getResult: GetResult = GetResult(this)
        getResult.callForLogin(apiInterface, "ApiRegister", pDialog)
        getResult.setMyListener(this)
    }

    override fun callback(result: JsonObject?, callNo: String?, code: Int) {
        if (callNo.equals("ApiRegister", ignoreCase = false)) {
            val gson = Gson()
            if (code in 200..299) {
                pDialog.dismiss()
                val registerModelData = gson.fromJson(result, RegisterMainModel::class.java)
                if (registerModelData.success == true) {
                    val getUserData = registerModelData.data
                    if (getUserData != null) {
                        sessionManager.setUserDetails(SessionManager.user, getUserData)
                        sessionManager.setBooleanData(SessionManager.login, true)
                        sessionManager.setStringData(SessionManager.api_token, getUserData.apiToken)
                        val intent = Intent(
                            this@CreateAccountActivity,
                            AccountCompletionActivity::class.java
                        )
                        startActivity(intent)
                        finish()
                    }
                }
            } else if (code in 400..499) {
                pDialog.dismiss()
                val loginDataModel = gson.fromJson(result.toString(), BasicBodyModel::class.java)
                var message: String
                if (loginDataModel.data.isNullOrEmpty()) {
                    message = loginDataModel.message.toString()
                } else {
                    message = loginDataModel.data.toString()
                }
                message = message.replace("[", "")
                message = message.replace("]", "")
                SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE)
                    .setContentText(message.toString())
                    .setConfirmText("Okay")
                    .show()
            }
        }
    }
}
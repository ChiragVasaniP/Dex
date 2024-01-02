package com.abdoul.dex.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.util.Log
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.core.widget.doAfterTextChanged
import cn.pedant.SweetAlert.SweetAlertDialog
import com.abdoul.dex.R
import com.abdoul.dex.models.BasicBodyModel
import com.abdoul.dex.models.loginModel.LoginMainDataModel
import com.abdoul.dex.retrofit.GetResult
import com.abdoul.dex.retrofit.RetrofitHelper
import com.abdoul.dex.utils.BasicMethods
import com.google.gson.Gson
import com.google.gson.JsonObject

class VerifyCodeActivity : AppCompatActivity(), GetResult.MyListener {
    var confirm: TextView? = null
    var back: ImageView? = null
    lateinit var edtPin1: EditText
    lateinit var edtPin2: EditText
    lateinit var edtPin3: EditText
    lateinit var edtPin4: EditText
    lateinit var edtPin5: EditText
    lateinit var edtPin6: EditText
    lateinit var edtEmailForget: EditText
    lateinit var edtPasswordForget: EditText
    lateinit var edtConfPasswordForget: EditText
    lateinit var txtSubmitForgetTocallApi: TextView
    lateinit var txtEmailIdLine: TextView
    lateinit var txtReEnterOtp: TextView
    lateinit var llOtpVerification: LinearLayout
    lateinit var llResetPassword: LinearLayout
    var basicmethod: BasicMethods = BasicMethods()
    var flag = 0
    private var otp: String? = null
    lateinit var pDialog: SweetAlertDialog

    @SuppressLint("StringFormatInvalid")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_verify_code)

        confirm = findViewById(R.id.confirm)
        edtPin1 = findViewById(R.id.edt_pin1)
        edtPin2 = findViewById(R.id.edt_pin2)
        edtPin3 = findViewById(R.id.edt_pin3)
        edtPin4 = findViewById(R.id.edt_pin4)
        edtPin5 = findViewById(R.id.edt_pin5)
        edtPin6 = findViewById(R.id.edt_pin6)
        txtReEnterOtp = findViewById(R.id.txt_re_enter_otp)
        txtEmailIdLine = findViewById(R.id.txt_email_id_line)
        llOtpVerification = findViewById(R.id.ll_otp_verification)
        llResetPassword = findViewById(R.id.ll_reset_password)
        back = findViewById(R.id.back)
        edtEmailForget = findViewById(R.id.edt_email_forget)
        edtPasswordForget = findViewById(R.id.edt_password_forget)
        edtConfPasswordForget = findViewById(R.id.edt_conf_password_forget)
        txtSubmitForgetTocallApi = findViewById(R.id.txt_submit_forget)

        pDialog = SweetAlertDialog(this@VerifyCodeActivity, SweetAlertDialog.PROGRESS_TYPE)
        pDialog.progressHelper.barColor = Color.parseColor("#A5DC86")
        pDialog.titleText = "Loading ..."
        pDialog.setCancelable(false)


        configOtpEditText(
            edtPin1, edtPin2, edtPin3, edtPin4, edtPin5, edtPin6
        )

        val strEmail = intent.getStringExtra("forgetEmail").toString()

        edtEmailForget.setText(strEmail)

        txtEmailIdLine.text = getString(
            R.string.enter_the_verification_code_sent_to_abc_gmail_com,
            strEmail
        ).toString()

        Log.e("CheckEmail", strEmail.toString())

        confirm!!.setOnClickListener {
            otp = getOtp().toString()
            if (otp != null) {
                if (otp!!.toInt() < 6) {
                    Toast.makeText(this, "Please Enter 6 Digit Otp", Toast.LENGTH_LONG).show()
                } else {
                    llResetPassword.isVisible = true
                    llOtpVerification.isVisible = false
                    flag = 1
                }
            } else {
                Toast.makeText(this, "Please Enter Otp", Toast.LENGTH_LONG).show()
            }
        }
        txtReEnterOtp.setOnClickListener {

        }

        back!!.setOnClickListener {
            if (flag == 0) {
                val intent = Intent(this@VerifyCodeActivity, SigninActivity::class.java)
                startActivity(intent)
                finish()
            } else if (flag == 1) {
                llOtpVerification.isVisible = true
                llResetPassword.isVisible = false
                flag=0
            }
        }

        txtSubmitForgetTocallApi.setOnClickListener {
            if (!basicmethod.isValidPassword(edtPasswordForget.text.toString())) {
                edtPasswordForget.error = getString(R.string.str_invalid_password)
                edtPasswordForget.requestFocus()
            } else if (!basicmethod.isValidPassword(edtConfPasswordForget.text.toString())) {
                edtConfPasswordForget.error = getString(R.string.str_invalid_password)
                edtConfPasswordForget.requestFocus()
            } else if (edtConfPasswordForget.text.toString() != edtPasswordForget.text.toString()
            ) {
                edtConfPasswordForget.error = "Password and Confirm Password should be same "
            } else {
                callResetApi(
                    strEmail,
                    otp,
                    edtPasswordForget.text.toString(),
                    edtConfPasswordForget.text.toString()
                )
            }
        }


    }

    private fun getOtp(): String? {
        if (TextUtils.isEmpty(edtPin1.text) && TextUtils.isEmpty(edtPin2.text) && TextUtils.isEmpty(
                edtPin3.text
            ) &&
            TextUtils.isEmpty(edtPin4.text) && TextUtils.isEmpty(edtPin5.text) && TextUtils.isEmpty(
                edtPin6.text
            )
        ) {
//            Toast.makeText(this, "Please Enter Otp", Toast.LENGTH_LONG).show()
            return null
        } else {
            var strOtp: String = String()
            strOtp = StringBuilder(edtPin1.text)
                .append(edtPin2.text)
                .append(edtPin3.text)
                .append(edtPin4.text)
                .append(edtPin5.text)
                .append(edtPin6.text).toString()

            Log.e("Otp", strOtp)
            return strOtp
        }
    }

    fun configOtpEditText(vararg etList: EditText) {
        val afterTextChanged = { index: Int, e: Editable? ->
            val view = etList[index]
            val text = e.toString()

            when (view.id) {
                // first text changed
                etList[0].id -> {
                    if (text.isNotEmpty()) etList[index + 1].requestFocus()
                }

                // las text changed
                etList[etList.size - 1].id -> {
                    if (text.isEmpty()) etList[index - 1].requestFocus()
                }

                // middle text changes
                else -> {
                    if (text.isNotEmpty()) etList[index + 1].requestFocus()
                    else etList[index - 1].requestFocus()
                }
            }
            false
        }
        etList.forEachIndexed { index, editText ->
            editText.doAfterTextChanged { afterTextChanged(index, it) }
        }

    }


    private fun callResetApi(
        strEmail: String,
        otp: String?,
        password: String,
        confirmPassword: String
    ) {
        Log.e("validationOk", "Validate")
        pDialog.show()
        val apiInterface = RetrofitHelper.create("").resetPassword(
            strEmail,
            otp.toString(), password, confirmPassword
        )
        val getResult: GetResult = GetResult(this)
        getResult.callForLogin(apiInterface, "ResetPassword", pDialog)
        getResult.setMyListener(this)
    }

    override fun callback(result: JsonObject?, callNo: String?, code: Int) {
        if (callNo.equals("ResetPassword", ignoreCase = true)) {
            var gson: Gson = Gson()
            if (code in 200..299) {
                val resetPasswordResponse = gson.fromJson(result, LoginMainDataModel::class.java)
                if (resetPasswordResponse.success == true) {
                    pDialog.dismiss()
                    var success: SweetAlertDialog =
                        SweetAlertDialog(this@VerifyCodeActivity, SweetAlertDialog.SUCCESS_TYPE)
                    success.titleText = "Password Changed successfully"
                    success.contentText = resetPasswordResponse.message.toString()
                    success.setCancelable(false)
                    success.setConfirmButton("Okay", SweetAlertDialog.OnSweetClickListener {
                        val intent = Intent(this, SigninActivity::class.java)
                        startActivity(intent)
                        success.dismiss()
                        finish()
                    })
                        .show()
                }
            } else if (code in 400..499) {
                pDialog.dismiss()
                val basicBodyModel = gson.fromJson(result, BasicBodyModel::class.java)
                var message: String
                if (basicBodyModel.data.isNullOrEmpty()) {
                    message = basicBodyModel.message.toString()
                } else {
                    message = basicBodyModel.data.toString()
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

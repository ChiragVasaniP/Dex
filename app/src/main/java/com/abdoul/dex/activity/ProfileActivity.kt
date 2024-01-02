package com.abdoul.dex.activity

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import cn.pedant.SweetAlert.SweetAlertDialog
import com.abdoul.dex.R
import com.abdoul.dex.models.loginModel.LoginData
import com.abdoul.dex.models.loginModel.LoginMainDataModel
import com.abdoul.dex.retrofit.GetResult
import com.abdoul.dex.retrofit.RetrofitHelper
import com.abdoul.dex.utils.SessionManager
import com.bumptech.glide.Glide
import com.google.gson.Gson
import com.google.gson.JsonObject

class ProfileActivity : AppCompatActivity(), GetResult.MyListener {

    lateinit var imgBackProfile: ImageView
    var sessionManager: SessionManager? = null
    lateinit var pDialog: SweetAlertDialog
    lateinit var imgProfile: ImageView
    lateinit var txtUserName: TextView
    lateinit var txtUserAge: TextView
    lateinit var txtUserProfileMain: TextView
    lateinit var txtEmailUser: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        sessionManager = SessionManager(this)
        imgProfile=findViewById(R.id.img_profile_image)
        txtUserName=findViewById(R.id.txt_user_name)
        txtUserAge=findViewById(R.id.txt_user_age)
        txtUserProfileMain=findViewById(R.id.txt_user_profile_main)
        txtEmailUser=findViewById(R.id.txt_email_user)

        pDialog = SweetAlertDialog(this@ProfileActivity, SweetAlertDialog.PROGRESS_TYPE)
        pDialog.progressHelper.barColor = Color.parseColor("#A5DC86")
        pDialog.titleText = "Loading ..."
        pDialog.setCancelable(false)

        val ApiToken = sessionManager!!.getStringData(SessionManager.api_token)

        if (ApiToken != null) {
            getProfile(ApiToken)
        }
        imgBackProfile = findViewById(R.id.img_back_profile)

        imgBackProfile.setOnClickListener {
            finish()
        }
    }

    private fun getProfile(apiToken: String) {
        val apiInterface = RetrofitHelper.create(apiToken).getProfile()
        val getResult = GetResult(this@ProfileActivity)
        getResult.callForLogin(apiInterface, "GetProfile", pDialog)
        getResult.setMyListener(this)
    }

    override fun callback(result: JsonObject?, callNo: String?, code: Int) {
        if (callNo.equals("GetProfile", ignoreCase = true)) {
            val gson = Gson()
            if (code in 200..299) {
                val profileData = gson.fromJson(result, LoginMainDataModel::class.java)
                if (profileData.success == true) {
                    val profile = profileData.loginData
                    setData(profile)
                }

            } else if (code in 400..499) {

            }
        }

    }

    @SuppressLint("SetTextI18n")
    private fun setData(profile: LoginData?) {
        if (profile != null) {

        Glide
            .with(this@ProfileActivity)
            .load(profile?.avatar)
            .centerCrop()
            .placeholder(R.drawable.man)
            .into(imgProfile);

            txtUserName.text=profile.name.toString()
            txtUserProfileMain.text=profile.name.toString()
            txtEmailUser.text=profile.email.toString()


            if(profile.age!=null){
                txtUserAge.text=profile.age.toString()
            }else{
                txtUserAge.text="Not available"
            }

        }
//        Toast.makeText(this@ProfileActivity, "Get It", Toast.LENGTH_SHORT).show()


    }
}
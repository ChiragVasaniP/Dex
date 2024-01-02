package com.abdoul.dex.activity

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.graphics.Color
import android.os.Bundle
import android.util.Base64
import android.util.Log
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import cn.pedant.SweetAlert.SweetAlertDialog
import com.abdoul.dex.R
import com.abdoul.dex.models.BasicBodyModel
import com.abdoul.dex.models.GoogleSignInAccessTokenDataClass
import com.abdoul.dex.models.loginModel.LoginMainDataModel
import com.abdoul.dex.retrofit.GetResult
import com.abdoul.dex.retrofit.RetrofitHelper
import com.abdoul.dex.utils.SessionManager
import com.facebook.*
import com.facebook.appevents.AppEventsLogger
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.Scopes
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.Scope
import com.google.gson.Gson
import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import java.util.*


class SigninActivity : AppCompatActivity(), GetResult.MyListener {
    var signup: TextView? = null
    var signin: TextView? = null

    lateinit var edtEmail: EditText
    lateinit var edtPassword: EditText
    var sessionManager: SessionManager? = null
    lateinit var pDialog: SweetAlertDialog
    lateinit var txtForgetpasswordScreen: TextView
    lateinit var imgFbLogin: ImageView

    var gso: GoogleSignInOptions? = null
    var gsc: GoogleSignInClient? = null
    lateinit var googleBtn: ImageView
    lateinit var strProvider: String
    private var callbackManager: CallbackManager? = null
    private var loginManager: LoginManager? = null

    @SuppressLint("SetTextI18n", "LongLogTag")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signin)

        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(application);

        callbackManager = CallbackManager.Factory.create();

        signin = findViewById(R.id.signin)
        googleBtn = findViewById(R.id.img_google_login)
        signup = findViewById(R.id.tosignup)
        edtEmail = findViewById(R.id.edt_email_signin)
        edtPassword = findViewById(R.id.edt_password_signin)
        txtForgetpasswordScreen = findViewById(R.id.txt_forgetpassword_screen)
        imgFbLogin = findViewById(R.id.img_fb_login)

        pDialog = SweetAlertDialog(this@SigninActivity, SweetAlertDialog.PROGRESS_TYPE)
        pDialog.progressHelper.barColor = Color.parseColor("#A5DC86")
        pDialog.titleText = "Loading ..."
        pDialog.setCancelable(false)

//        printHashKey(this)

        gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestScopes(Scope(Scopes.DRIVE_APPFOLDER))
            .requestServerAuthCode(getString(R.string.str_client_id))
            .requestIdToken(getString(R.string.str_client_id))
            .requestEmail()
            .build()
        gsc = GoogleSignIn.getClient(this, gso!!)




        sessionManager = SessionManager(this@SigninActivity)

        edtPassword.setText("secret")
        edtEmail.setText("dev.sbiswas7991@gmail.com")

        val request = GraphRequest.newMeRequest(
            AccessToken.getCurrentAccessToken()
        ) { jsonObject, response ->
            Log.d("LOG_TAG", "onCompleted jsonObject: $jsonObject")
            Log.d("LOG_TAG", "onCompleted response: $response")
        }
        val parameters = Bundle()
        parameters.putString("fields", "id,name,link,cover,email")
        request.parameters = parameters
        request.executeAsync()


        LoginManager.getInstance().registerCallback(callbackManager,
            object : FacebookCallback<LoginResult> {
                override fun onCancel() {
                    Log.e("FaceBook", "Cancel")
                }

                override fun onError(error: FacebookException) {
                    Log.e("FaceBook", "error" + error.message.toString())
                }

                override fun onSuccess(result: LoginResult) {
                    Log.e("CodeFaceBook", result.accessToken.token.toString())
                    strProvider = ""
                    strProvider = "facebook"
                    callApiSocial(result.accessToken.token.toString(),strProvider)
                }
            })



        imgFbLogin.setOnClickListener {
            strProvider = ""
            strProvider = "facebook"
            LoginManager.getInstance().logInWithReadPermissions(
                this, Arrays.asList("email", "public_profile")
            )
        }

        txtForgetpasswordScreen.setOnClickListener {
            val intent = Intent(this@SigninActivity, ForgetPasswordActivity::class.java)
            startActivity(intent)
            finish()
        }

        googleBtn.setOnClickListener {
            strProvider = ""
            strProvider = "google"
            signIn()
        }

        signin!!.setOnClickListener {
            var email = edtEmail.text.toString()
            var password = edtPassword.text.toString()
            if (email.isEmpty()) {
                edtEmail.error = "Please Enter Email"
            } else if (password.isEmpty()) {
                edtPassword.error = "Pleased Enter Password"
            } else {
                callApi(email, password)
            }

        }

        signup!!.setOnClickListener {
            val intent = Intent(this@SigninActivity, SignupActivity::class.java)
            startActivity(intent)
        }
    }

    private fun signIn() {
        val signInIntent = gsc!!.signInIntent
        startActivityForResult(signInIntent, 1000)
    }

    private fun callApi(email: String, password: String) {
        pDialog.show()
        val apiInterface =
            RetrofitHelper.create("").loginUser(email, password, "")
        val getResult = GetResult(this)
        getResult.setMyListener(this)
        getResult.callForLogin(apiInterface, "LoginApi", pDialog)
    }

    override fun callback(result: JsonObject?, callNo: String?, code: Int) {
        if (callNo.equals("LoginApi", ignoreCase = true)) {
            val gson = Gson()
            if (code in 200..299) {
                val loginDataModel =
                    gson.fromJson(result.toString(), LoginMainDataModel::class.java)
                if (loginDataModel.success == true) {
                    pDialog.dismiss()
                    val getUserData = loginDataModel.loginData
                    sessionManager?.setUserDetails(SessionManager.user, loginDataModel.loginData)
                    Log.e("UserName", getUserData!!.name.toString())
                    sessionManager?.setBooleanData(SessionManager.login, true)
                    sessionManager?.setStringData(SessionManager.api_token, getUserData.apiToken)
                    val intent = Intent(this@SigninActivity, BaseActivity::class.java)
                    startActivity(intent)
                }
            } else if (code in 400..499) {
                val loginDataModel = gson.fromJson(result.toString(), BasicBodyModel::class.java)
                var message: String
                if (loginDataModel.data.isNullOrEmpty()) {
                    message = loginDataModel.message.toString()
                } else {
                    message = loginDataModel.data.toString()
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        callbackManager!!.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1000) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                task.getResult(ApiException::class.java)
                val acct = GoogleSignIn.getLastSignedInAccount(this)
                if (acct != null) {
                    getGoogleAccessToken(acct.serverAuthCode, acct.idToken)
                }
            } catch (e: ApiException) {
                Toast.makeText(applicationContext, "Something went wrong", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }


    private fun getGoogleAccessToken(serverAuthCode: String?, idToken: String?) {
        val call = RetrofitHelper.getAuthToken().getAccessTokenGoogle(
            grant_type = getString(R.string.str_grant_type),
            client_id = getString(R.string.str_client_id),
            client_secret = getString(R.string.str_client_secret),
            redirect_uri = "",
            authCode = serverAuthCode.toString(),
            id_token = idToken.toString(),
            url = getString(R.string.str_google_oth)
        )

        call.enqueue(object : Callback<GoogleSignInAccessTokenDataClass> {
            val tag = "CodeGoogle"
            override fun onFailure(call: Call<GoogleSignInAccessTokenDataClass>, t: Throwable) {
                Log.e(tag, t.toString())
            }

            override fun onResponse(
                call: Call<GoogleSignInAccessTokenDataClass>,
                response: Response<GoogleSignInAccessTokenDataClass>
            ) {
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    Log.e(tag, responseBody!!.access_token.toString())
                    val accessCode = responseBody!!.access_token.toString()
                    callApiSocial(accessCode, strProvider)

                } else {
                    try {
                        val responseError = response.errorBody()!!.string()
                        Log.e(tag, responseError)
                    } catch (e: Exception) {
                        Log.e(tag, e.toString())
                    }
                }
            }
        })
    }

    private fun callApiSocial(accessCode: String, strProvider: String) {
        pDialog.show()
        val apiInterface = RetrofitHelper.create("").socialLogin(
            provider = strProvider,
            token = accessCode, fcm_token = ""
        )
        val getResult = GetResult(this)
        getResult.callForLogin(apiInterface, "LoginApi", pDialog)
        getResult.setMyListener(this)
    }

    fun printHashKey(pContext: Context) {
        try {
            val info: PackageInfo = pContext.getPackageManager()
                .getPackageInfo(pContext.getPackageName(), PackageManager.GET_SIGNATURES)
            for (signature in info.signatures) {
                val md: MessageDigest = MessageDigest.getInstance("SHA")
                md.update(signature.toByteArray())
                val hashKey: String = String(Base64.encode(md.digest(), 0))
                Log.i("TAG", "printHashKey() Hash Key: $hashKey")
            }
        } catch (e: NoSuchAlgorithmException) {
            Log.e("TAG", "printHashKey()", e)
        } catch (e: java.lang.Exception) {
            Log.e("TAG", "printHashKey()", e)
        }
    }
}
package com.abdoul.dex.retrofit

import android.content.Context
import android.util.Log
import android.widget.Toast
import cn.pedant.SweetAlert.SweetAlertDialog
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class GetResult(context: Context) {

    var context: Context? = context

    fun callForLogin(call: Call<JsonObject>, callno: String, pDialog: SweetAlertDialog) {

        call.enqueue(object : Callback<JsonObject> {
            override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>) {

                Log.e("Header", response.headers().toString())
                Log.e("CheckCall", response.toString())
                Log.e("codeNumber", response.code().toString())
                if (response.code() in 200..299) {
                    Log.e("body", " : " + response.body().toString())
                    Log.e("callno", " : $callno")
                    myListener!!.callback(response.body(), callno, response.code())
                } else if (response.code() in 400..499) {
                    pDialog.dismiss()
                    val errorBody = (response?.errorBody() as ResponseBody).string()
                    var jsonparser: JsonParser = JsonParser()
                    var jsonOb: JsonObject = JsonObject()
                    jsonOb = jsonparser.parse(errorBody) as JsonObject
                    Log.e("Response400", jsonOb.toString())
                    myListener!!.callback(jsonOb, callno, response.code())
                }
            }

            override fun onFailure(call: Call<JsonObject>, throwable: Throwable) {
                Log.e("Failed", callno + "   " + throwable.message.toString())
                pDialog.dismiss()
                Toast.makeText(context, "Server Error", Toast.LENGTH_LONG).show()
                call.cancel()
                throwable.printStackTrace()
            }
        })
    }

    interface MyListener {
        fun callback(result: JsonObject?, callNo: String?, code: Int)
    }

    fun setMyListener(Listener: MyListener?) {
        myListener = Listener
    }

    companion object {
        var myListener: MyListener? = null
    }

}



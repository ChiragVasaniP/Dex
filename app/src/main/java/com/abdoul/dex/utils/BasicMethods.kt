package com.abdoul.dex.utils

import android.text.TextUtils
import android.widget.EditText
import java.util.regex.Matcher
import java.util.regex.Pattern


class BasicMethods {


    fun isEmailValid(email: String): Boolean =
        email.isNotEmpty() && Pattern.compile(
            "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
                    "\\@" +
                    "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
                    "(" +
                    "\\." +
                    "[a-zA-Z0-9][a-zA-Z0-9\\-]{1,25}" +
                    ")+"
        ).matcher(email).matches()

    fun  isValidPassword(password: String?): Boolean {
        val matcher: Matcher =
            Pattern.compile("((?=.*[a-z])(?=.*\\d)(?=.*[A-Z])(?=.*[@#$%!]).{4,20})")
                .matcher(password)
        return matcher.matches()
    }

//    val edtError = arrayOf<EditText>(
//        edtEmailForget,
//        edtPasswordForget,
//        edtConfPasswordForget
//    )

    fun checkEditTextEmpty(edtError: Array<EditText>) {
        val ErrorFields: MutableList<EditText> = ArrayList()
        for (edit in edtError) {
            if (TextUtils.isEmpty(edit.text)) {

                // EditText was empty
                ErrorFields.add(edit) //add empty Edittext only in this ArayList
                for (i in ErrorFields.indices) {
                    val currentField = ErrorFields[i]
                    currentField.error = "this field required"
                    currentField.requestFocus()
                }
            }
        }
    }
}
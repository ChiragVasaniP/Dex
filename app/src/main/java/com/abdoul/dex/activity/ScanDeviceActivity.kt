package com.abdoul.dex.activity

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.abdoul.dex.R
import com.google.android.material.bottomsheet.BottomSheetDialog

class ScanDeviceActivity : AppCompatActivity() {

    lateinit var imgBackScan: ImageView
    lateinit var continues: TextView

    @SuppressLint("ResourceType")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scan_device)

        imgBackScan = findViewById(R.id.img_back_scan)
        continues = findViewById(R.id.continues)

        imgBackScan.setOnClickListener {
            onBackPressed()
        }

        continues.setOnClickListener {
            val dialog = BottomSheetDialog(this, R.style.NoBackgroundDialogTheme)
            val view = layoutInflater.inflate(R.layout.bottomsheet_instruction, this.findViewById(R.id.root_layout_bottom))
            val txtAcceptDialog = view.findViewById<TextView>(R.id.txt_accept_dialog)
            val txtCancelDialog = view.findViewById<TextView>(R.id.txt_cancel_dialog)

            txtAcceptDialog.setOnClickListener {
                val myToast = Toast.makeText(this, "You Accepted!", Toast.LENGTH_LONG)
                myToast.show()
                dialog.dismiss()
            }

            txtCancelDialog.setOnClickListener {
                dialog.dismiss()
            }
            dialog.setCancelable(false)
            dialog.setContentView(view)
            dialog.show()

        }


    }
}
package com.abdoul.dex.activity

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import androidx.core.view.isVisible
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.abdoul.dex.R
import com.abdoul.dex.fragment.*
import com.abdoul.dex.utils.SessionManager
import com.bumptech.glide.Glide
import com.abdoul.dex.models.loginModel.LoginData
import com.google.android.material.navigation.NavigationView


class BaseActivity : AppCompatActivity() {
    lateinit var toggle: ActionBarDrawerToggle
    lateinit var drawerLayout: DrawerLayout
    lateinit var llEmiPolution: LinearLayout
    lateinit var llCrime: LinearLayout
    lateinit var llWeather: LinearLayout
    lateinit var llDisease: LinearLayout
    lateinit var vLinePolution: View
    lateinit var vLineCrime: View
    lateinit var vLineWhether: View
    lateinit var vLineDisease: View
    var sessionManager: SessionManager? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_base)

        drawerLayout = findViewById(R.id.drawerLayout)
        llEmiPolution = findViewById(R.id.ll_emi_pollution)
        llCrime = findViewById(R.id.ll_crime)
        llWeather = findViewById(R.id.ll_weather)
        llDisease = findViewById(R.id.ll_disease)
        vLinePolution = findViewById(R.id.v_line_polution)
        vLineCrime = findViewById(R.id.v_line_crime)
        vLineWhether = findViewById(R.id.v_line_whether)
        vLineDisease = findViewById(R.id.v_line_disease)

        sessionManager = SessionManager(this@BaseActivity)

        val navView: NavigationView = findViewById(R.id.navView)
        var headerView: View? = navView.inflateHeaderView(R.layout.nav_header_main)
        headerView = navView.getHeaderView(0)
        getUserData(headerView)


        toggle = ActionBarDrawerToggle(this, drawerLayout, R.string.nav_open, R.string.nav_close)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        replaceFragment(HomeFragment(), "")

        llEmiPolution.setOnClickListener {
            hideViewColor()
            showView(vLinePolution, llEmiPolution)
            val toast = Toast.makeText(applicationContext, "in Emi Pollution", Toast.LENGTH_SHORT)
            toast.show()
        }

        llCrime.setOnClickListener {
            hideViewColor()
            showView(vLineCrime, llCrime)
            val toast = Toast.makeText(applicationContext, "in Crime", Toast.LENGTH_SHORT)
            toast.show()
        }

        llWeather.setOnClickListener {
            hideViewColor()
            showView(vLineWhether, llWeather)
            val toast = Toast.makeText(applicationContext, "in Whether", Toast.LENGTH_SHORT)
            toast.show()
        }

        llDisease.setOnClickListener {
            hideViewColor()
            showView(vLineDisease, llDisease)
            val toast = Toast.makeText(applicationContext, "in Disease", Toast.LENGTH_SHORT)
            toast.show()
        }



        navView.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.nav_home -> replaceFragment(HomeFragment(), it.title.toString())
                R.id.nav_protection -> replaceFragment(ProtectionFragment(), it.title.toString())
                R.id.nav_terms -> replaceFragment(TermsFragment(), it.title.toString())
                R.id.nav_logout -> logout()
                R.id.nav_services -> replaceFragment(ServicesFragment(), it.title.toString())
                R.id.nav_settings -> replaceFragment(SettingsFragment(), it.title.toString())

            }
            true
        }
    }


    private fun logout() {
        val dialogClickListener =
            DialogInterface.OnClickListener { dialog, which ->
                when (which) {
                    DialogInterface.BUTTON_POSITIVE -> {
                        sessionManager?.logoutUser()
                        val intent = Intent(this@BaseActivity, SigninActivity::class.java)
                        startActivity(intent)
                        finish()
                        dialog.dismiss()

                    }
                    DialogInterface.BUTTON_NEGATIVE -> {
//                        drawerLayout.close()
                    }
                }
            }

        val builder: AlertDialog.Builder = AlertDialog.Builder(this)
        builder.setMessage(resources.getString(R.string.youwantlogout))
            .setPositiveButton(resources.getString(R.string.yes), dialogClickListener)
            .setNegativeButton(resources.getString(R.string.no), dialogClickListener).show()
    }

    fun openCloseNavigationDrawer() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            drawerLayout.openDrawer(GravityCompat.START)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if (toggle.onOptionsItemSelected(item)) {
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun replaceFragment(fragment: Fragment, title: String) {
        val fm = supportFragmentManager
        val ft = fm.beginTransaction().addToBackStack("grafico")
        ft.replace(R.id.frameLayout, fragment)
        ft.commit()
        drawerLayout.closeDrawers()
        setTitle(title)
    }


    private fun hideViewColor() {
        vLinePolution.isVisible = false
        vLineCrime.isVisible = false
        vLineWhether.isVisible = false
        vLineDisease.isVisible = false
        val colorValue = ContextCompat.getColor(this, R.color.white)
        llEmiPolution.setBackgroundColor(colorValue)
        llCrime.setBackgroundColor(colorValue)
        llWeather.setBackgroundColor(colorValue)
        llDisease.setBackgroundColor(colorValue)
    }


    private fun showView(view: View, llEmiPolution: LinearLayout) {
        val colorValue = ContextCompat.getColor(this, R.color.selected_color)
        view.isVisible = true
        llEmiPolution.setBackgroundColor(colorValue)
    }

    private fun getUserData(headerView: View) {

        val txtEmailDraw: TextView = headerView.findViewById(R.id.txt_u_email_draw)
        val txtUserNameDraw: TextView = headerView.findViewById(R.id.txt_u_name_draw)
        val imgProfileImage: ImageView = headerView.findViewById(R.id.img_profile_image)

        if (sessionManager?.getUserDetails(SessionManager.user) != null) {
            val user: LoginData = sessionManager?.getUserDetails(SessionManager.user)!!

            txtEmailDraw.text = user.email
            txtUserNameDraw.text = user.name

            Glide
                .with(this)
                .load(user.avatar)
                .circleCrop()
                .placeholder(R.drawable.man)
                .into(imgProfileImage);
        }
    }
}
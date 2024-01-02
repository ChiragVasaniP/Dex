package com.abdoul.dex.activity

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import com.abdoul.dex.R
import com.abdoul.dex.whetherService.BreezoTileProvider
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*


class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    var add : ImageView? = null
    var toggle : ImageView? = null
    var alert : ImageView? = null
    private var tileOverlay: TileOverlay? = null
    var menu : ImageView? = null
    private var breezoTileProvider: TileProvider = BreezoTileProvider()

    lateinit var toggleDrawer : ActionBarDrawerToggle
//    lateinit var drawerLayout : DrawerLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
        add = findViewById(R.id.add)
        toggle = findViewById(R.id.toggle)
        alert = findViewById(R.id.alertbg)

        add!!.setOnClickListener {
            val intent = Intent(this@MapsActivity, SensesActionActivity::class.java)
            startActivity(intent)
        }
        toggle!!.setOnClickListener {
            val intent = Intent(this@MapsActivity, SensesActivity::class.java)
            startActivity(intent)
        }
        alert!!.setOnClickListener {

        }


        menu = findViewById(R.id.menu)
        menu!!.setOnClickListener {
//            drawerLayout.openDrawer(Gravity.LEFT)
        }

    }
    
    

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        // Add a marker in Sydney and move the camera
        val sydney = LatLng(-34.0, 151.0)
        mMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))
        this.tileOverlay = googleMap.addTileOverlay(
            TileOverlayOptions().tileProvider(
                breezoTileProvider
            ).transparency(0.425f)
        )

    }
}
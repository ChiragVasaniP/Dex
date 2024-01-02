package com.abdoul.dex.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.abdoul.dex.R
import com.abdoul.dex.activity.BaseActivity
import com.abdoul.dex.activity.SensesActionActivity
import com.abdoul.dex.activity.SensesActivity
import com.abdoul.dex.retrofit.RetrofitHelper
import com.google.gson.JsonObject
import com.mapbox.mapboxsdk.Mapbox
import com.mapbox.mapboxsdk.maps.MapView
import com.mapbox.mapboxsdk.maps.Style
import com.mapbox.mapboxsdk.style.layers.LineLayer
import com.mapbox.mapboxsdk.style.layers.PropertyFactory
import com.mapbox.mapboxsdk.style.layers.RasterLayer
import com.mapbox.mapboxsdk.style.sources.RasterSource
import com.mapbox.mapboxsdk.style.sources.TileSet
import com.mapbox.mapboxsdk.style.sources.VectorSource
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class HomeFragment : Fragment() {
    private var param1: String? = null
    private var param2: String? = null
    var add: ImageView? = null
    var toggle: ImageView? = null
    var alert: ImageView? = null
    var hamburgermenu: ImageView? = null
    lateinit var mapView: MapView
    private val rootStyleUrl = "mapbox://styles/shahar/ck65hhzh62kog1imgi2bfp610"
    private val urlTemplateString =
        "https://tiles.breezometer.com/v1/air-quality/breezometer-aqi/current-conditions/{z}/{x}/{y}.png?key=52dc388c4d4f4032998529a9452b4e54&breezometer_aqi_color=indiper"
    private val urlVectorsString =
        "https://tiles.breezometer.com/v1/air-quality/traffic-pollution/current-conditions/{z}/{x}/{y}.pbf?key=52dc388c4d4f4032998529a9452b4e54"


    override fun onCreate(savedInstanceState: Bundle?) {
        Mapbox.getInstance(
            requireActivity().applicationContext,
            getString(R.string.mapbox_access_token)
        )
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        add = view.findViewById(R.id.add)
        toggle = view.findViewById(R.id.toggle)
        alert = view.findViewById(R.id.alertbg)
        mapView = view.findViewById(R.id.mapView)

        mapView.getMapAsync { mapboxMap ->
            mapboxMap.setStyle(Style.MAPBOX_STREETS) { style ->
                style.addSource(
                    RasterSource(
                        "brz-tiles-raster",
                        TileSet("2.1.0", urlTemplateString),
                        256
                    )
                )
                val withProperties =
                    RasterLayer("", "brz-tiles-raster").withProperties(
                        PropertyFactory.rasterOpacity(java.lang.Float.valueOf(0.45f))
                    )
                if (style.getLayer("admin-1-boundary-bg") != null) {
                    style.addLayerBelow(withProperties, "admin-1-boundary-bg");
                } else {
                    style.addLayer(withProperties);
                }

                val tileSet =
                    TileSet("2.1.0", urlVectorsString)
                tileSet.minZoom = 0.0f
                tileSet.maxZoom = 10.0f
                style.addSource(VectorSource("brz-roads", tileSet))
                val lineLayer = LineLayer("roads", "brz-roads")
                lineLayer.sourceLayer = "brz-roads"
            }
        }

        val txtCityData = view.findViewById<TextView>(R.id.txt_city_data)

        getAirQuality()


        txtCityData.text = getString(R.string.tsr_box_text)

        add!!.setOnClickListener {
            val intent = Intent(activity, SensesActionActivity::class.java)
            startActivity(intent)
        }
        toggle!!.setOnClickListener {
            val intent = Intent(activity, SensesActivity::class.java)
            startActivity(intent)
        }
        alert!!.setOnClickListener {
            val intent = Intent(activity, SensesActivity::class.java)
            startActivity(intent)
        }

        hamburgermenu = view.findViewById(R.id.hamburgermenu)
        hamburgermenu!!.setOnClickListener {
            (activity as BaseActivity).openCloseNavigationDrawer()
        }
        return view
    }

    override fun onStart() {
        super.onStart()
        mapView.onStart()
    }

    override fun onStop() {
        super.onStop()
        mapView.onStop()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mapView.onLowMemory()
    }

    override fun onDestroy() {
        super.onDestroy()
        mapView.onDestroy()
    }

    private fun getAirQuality() {

        val apiInterface: Call<JsonObject> = RetrofitHelper.getBreezoMeter()
            .getMAp("21.170240", "72.831062", getString(R.string.str_breezometer))
        apiInterface.enqueue(object : Callback<JsonObject> {
            override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>) {
                Log.e("Jsonresponse", response.body().toString())

            }

            override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                Log.e("Jsonresponse", t.message.toString())
            }

        })
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            HomeFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

//    @SuppressLint("WrongConstant")
//    override fun onMapReady(mapboxMap: MapboxMap) {
//
//        // Reference the local JSON style file in the assets folder and pass through as the string parameter
//        mapboxMap.setStyle(Style.Builder().fromUri("asset://local_style_file.json"))
//
//        mapboxMap.setStyle(rootStyleUrl) { style ->
//            style.addSource(
//                RasterSource(
//                    "brz-tiles-raster",
//                    TileSet("2.1.0", urlTemplateString),
//                    256
//                )
//            )
//            val withProperties =
//                RasterLayer("", "brz-tiles-raster").withProperties(
//                    PropertyFactory.rasterOpacity(java.lang.Float.valueOf(0.45f))
//                )
//            if (style.getLayer("admin-1-boundary-bg") != null) {
//                style.addLayerBelow(withProperties, "admin-1-boundary-bg");
//            } else {
//                style.addLayer(withProperties);
//            }
//
//            val tileSet =
//                TileSet("2.1.0", urlVectorsString)
//            tileSet.minZoom = 0.0f
//            tileSet.maxZoom = 10.0f
//            style.addSource(VectorSource("brz-roads", tileSet))
//            val lineLayer = LineLayer("roads", "brz-roads")
//            lineLayer.sourceLayer = "brz-roads"
//        }
//    }


}
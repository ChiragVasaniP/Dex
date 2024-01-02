package com.abdoul.dex.whetherService

import com.google.android.gms.maps.model.UrlTileProvider
import java.net.MalformedURLException
import java.net.URL
import java.util.*


class BreezoTileProvider : UrlTileProvider(256, 256) {


    private fun checkTileExists(i: Int, i2: Int, i3: Int): Boolean {
        return i3 in 0..16
    }

    companion object {
        private var ApiKey: String? = null
//        private const val TILE_SERVER_URL = "https://tiles.breezometer.com/%d/%d/%d.png?key=%s"
        private const val TILE_SERVER_URL = "https://tiles.breezometer.com/v1/air-quality/breezometer-aqi/current-conditions/%d/%d/%d.png?key=%s"

        init {
            ApiKey = "ENTER_DEMO_KEY"
        }
    }

    override fun getTileUrl(i: Int, i2: Int, i3: Int): URL? {
        val format = String.format(
            Locale.US,
            TILE_SERVER_URL,
            Integer.valueOf(i3),
            Integer.valueOf(i),
            Integer.valueOf(i2),
            ApiKey
        )
        return if (!checkTileExists(i, i2, i3)) {
            null
        } else try {
            URL(format)
        } catch (e: MalformedURLException) {
            throw AssertionError(e)
        }
    }
}
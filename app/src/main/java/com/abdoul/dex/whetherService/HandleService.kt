package com.abdoul.dex.whetherService
class HandleService {
//package com.abdoul.dex.whetherService
//    var response: HttpResponse? = null
//    var responseString: String? = null
//    var aqi: JSONObject? = null
//    var uri: URI? = null
//    fun getAQ(location: String, usingGps: Boolean, latitude: Double, longitude: Double): String? {
//        try {
//            uri = if (usingGps) {
//                URI("http://api.breezometer.com/baqi/?lat=$latitude&lon=$longitude&key=DEMO_KEY")

//    air-quality/v2/current-conditions?key=" + _apiKey + "&features=breezometer_aqi,local_aqi,health_recommendations,sources_and_effects,pollutants_concentrations,pollutants_aqi_information&metadata=true
//            } else {
//                URI("http://api.breezometer.com/baqi/?location=$location&key=DEMO_KEY")
//            }
//            val request = HttpGet(uri)
//            val client: HttpClient = DefaultHttpClient()
//            response = client.execute(request)
//            val httpEntity = response?.entity
//            responseString = EntityUtils.toString(httpEntity)
//        } catch (e: Exception) {
//            Log.v("exception_dan", e.message!!)
//        }
//        return responseString
//    }
//
//    fun getAQI(location: String): String? {
//        try {
//            val uri =
//                URI("http://api.breezometer.com/baqi/?location=$location&fields=breezometer_color,breezometer_aqi&key=DEMO_KEY")
//            val request = HttpGet(uri)
//            val client: HttpClient = DefaultHttpClient()
//            response = client.execute(request)
//            val httpEntity = response?.entity
//            responseString = EntityUtils.toString(httpEntity)
//        } catch (e: Exception) {
//            Log.v("exception_dan", e.message!!)
//        }
//        return responseString
//    }
//
//    private val client: Client = ClientBuilder.newBuilder()
//
//    fun getAirConditions(latitude: String, longitude: String): JSONObject? {
//        return client.target(
//            "https://api.breezometer.com/air-quality/v2/current-conditions?" + "lat=" + latitude +
//                    "&lon=" + longitude +
//                    "&key=" + "ENTER_DEMO_KEY" +
//                    "&features=breezometer_aqi,local_aqi,pollutants_concentrations,pollutants_aqi_information"
//        ).request(MediaType.APPLICATION_JSON).get(
//            JSONObject::class.java
//        )
//    }
}
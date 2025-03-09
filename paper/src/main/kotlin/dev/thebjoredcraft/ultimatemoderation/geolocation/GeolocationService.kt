package dev.thebjoredcraft.ultimatemoderation.geolocation

import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import net.kyori.adventure.text.logger.slf4j.ComponentLogger
import java.net.HttpURLConnection
import java.net.URL

object GeolocationService {
    private val gson = Gson()
    private val logger = ComponentLogger.logger(this.javaClass)

    suspend fun getGeoLocation(ip: String): GeoLocation? {
        return withContext(Dispatchers.IO) {
            try {
                val url = URL("http://ip-api.com/json/$ip?fields=66846719")
                val connection = url.openConnection() as HttpURLConnection

                connection.requestMethod = "GET"
                connection.connect()

                if (connection.responseCode == 200) {
                    val response = connection.inputStream.bufferedReader().use { it.readText() }
                    gson.fromJson(response, GeoLocation::class.java)
                } else {
                    null
                }
            } catch (e: Exception) {
                logger.error("Failed to get geolocation for IP $ip", e)
                null
            }
        }
    }
}
package dev.thebjoredcraft.ultimatemoderation.geolocation

data class GeoLocation (
    val continent: String,
    val continentCode: String,
    val country: String,
    val countryCode: String,
    val region: String,
    val regionName: String,
    val city: String,
    val district: String,
    val zip: String,
    val lat: Double,
    val lon: Double,
    val timezone: String,
    val offset: Int,
    val currency: String,
    val isp: String,
    val org: String,
    val aS: String,
    val asName: String,
    val mobile: Boolean,
    val proxy: Boolean,
    val hosting: Boolean
)
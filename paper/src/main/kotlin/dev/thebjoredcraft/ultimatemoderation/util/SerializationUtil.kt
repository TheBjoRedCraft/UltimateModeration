package dev.thebjoredcraft.ultimatemoderation.util

import com.google.gson.Gson

object SerializationUtil {
    val gson = Gson()

    fun toString(value: Any): String {
        return gson.toJson(value)

    }

    inline fun <reified T> fromString(value: String): T {
        return gson.fromJson(value, T::class.java)
    }
}
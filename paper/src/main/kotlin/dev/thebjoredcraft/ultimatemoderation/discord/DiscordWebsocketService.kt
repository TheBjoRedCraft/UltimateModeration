package dev.thebjoredcraft.ultimatemoderation.discord

import dev.thebjoredcraft.ultimatemoderation.plugin
import java.net.HttpURLConnection
import java.net.URL

object DiscordWebsocketService {
    var STAFF_CHAT_URL = ""

    fun reload() {
        STAFF_CHAT_URL = plugin.config.getString("discord.staff-chat-url", "") ?: return
    }

    fun sendMessage(url: String, message: String) {
        if (url.isBlank()) {
            return
        }

        val jsonPayload = """{"content": "$message"}"""
        val connection = URL(url).openConnection() as HttpURLConnection

        connection.requestMethod = "POST"
        connection.setRequestProperty("Content-Type", "application/json")
        connection.doOutput = true

        connection.outputStream.use { os ->
            val input = jsonPayload.toByteArray()
            os.write(input, 0, input.size)
        }

        connection.inputStream.use { it.readBytes() }
    }

    fun sendMessage(url: String, embed: EmbedBuilder) {
        if (url.isBlank()) {
            return
        }

        val jsonPayload = """{"embeds": [${embed.build()}]}"""
        val connection = URL(url).openConnection() as HttpURLConnection

        connection.requestMethod = "POST"
        connection.setRequestProperty("Content-Type", "application/json")
        connection.doOutput = true

        connection.outputStream.use { os ->
            val input = jsonPayload.toByteArray()
            os.write(input, 0, input.size)
        }

        connection.inputStream.use { it.readBytes() }
    }
}
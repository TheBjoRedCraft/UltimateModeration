package dev.thebjoredcraft.ultimatemoderation.language

import com.google.common.reflect.TypeToken
import com.google.gson.Gson
import dev.thebjoredcraft.ultimatemoderation.plugin
import dev.thebjoredcraft.ultimatemoderation.util.Colors
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.logger.slf4j.ComponentLogger
import net.kyori.adventure.text.minimessage.MiniMessage
import org.bukkit.entity.Player
import java.io.File

object LanguageController {
    private val logger = ComponentLogger.logger(this.javaClass)
    private val gson = Gson()
    private var language = Object2ObjectOpenHashMap<String, String>()

    fun loadLanguage() {
        val fileLanguage = plugin.config.getString("language", "en_us")
        val languageFile = File(plugin.dataFolder, "lang/$fileLanguage.json")

        if (!languageFile.exists()) {
            logger.error("Language file not found! (${languageFile.name}) Using $fileLanguage as default language.")
            return
        }

        val jsonContent = languageFile.readText()
        val type = object : TypeToken<List<LanguageEntry>>() {}.type
        val entries: List<LanguageEntry> = gson.fromJson(jsonContent, type)

        entries.forEach { entry ->
            language.putAll(entry.entries)
        }
    }

    fun translate(key: String, player: Player?, target: Player?): Component {
        val string = language[key] ?: key

        return MiniMessage.miniMessage().deserialize(update(string, player, target))
    }

    private fun update(string: String, player: Player?, target: Player?): String {
        return string
            .replace("%prefix%", MiniMessage.miniMessage().serialize(Colors.PREFIX))
            .replace("%player%", player?.name ?: "Unknown")
            .replace("%target%", target?.name ?: "Unknown")
    }
}
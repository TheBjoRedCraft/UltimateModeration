package dev.thebjoredcraft.ultimatemoderation

import com.github.shynixn.mccoroutine.bukkit.SuspendingJavaPlugin
import dev.thebjoredcraft.ultimatemoderation.util.Colors
import dev.thebjoredcraft.ultimatemoderation.util.MessageBuilder
import dev.thebjoredcraft.ultimatemoderation.util.PageableMessageBuilder
import net.kyori.adventure.text.minimessage.MiniMessage
import org.bukkit.entity.Player
import org.bukkit.plugin.java.JavaPlugin.getPlugin

val plugin: UltimateModerationPaper get() = getPlugin(UltimateModerationPaper::class.java)

class UltimateModerationPaper(): SuspendingJavaPlugin() {
    override suspend fun onEnableAsync() {

    }

    companion object {
        fun send(messageBuilder: MessageBuilder, vararg players: Player) {
            players.forEach { player -> player.sendMessage(Colors.PREFIX.append(messageBuilder.build())) }
        }
    }
}
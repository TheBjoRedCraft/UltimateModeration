package dev.thebjoredcraft.ultimatemoderation

import com.github.shynixn.mccoroutine.bukkit.SuspendingJavaPlugin
import dev.thebjoredcraft.ultimatemoderation.spectatemode.SpectateModeCommand
import dev.thebjoredcraft.ultimatemoderation.util.Colors
import dev.thebjoredcraft.ultimatemoderation.util.MessageBuilder

import org.bukkit.entity.Player
import org.bukkit.plugin.java.JavaPlugin.getPlugin

val plugin: UltimateModerationPaper get() = getPlugin(UltimateModerationPaper::class.java)

class UltimateModerationPaper(): SuspendingJavaPlugin() {
    override fun onEnable() {
        SpectateModeCommand("spectatemode").register()
    }

    override suspend fun onEnableAsync() {

    }

    companion object {
        fun send(messageBuilder: MessageBuilder, vararg players: Player) {
            players.forEach { player -> player.sendMessage(Colors.PREFIX.append(messageBuilder.build())) }
        }
    }
}
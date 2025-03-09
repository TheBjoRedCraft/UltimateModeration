package dev.thebjoredcraft.ultimatemoderation.auth

import io.papermc.paper.event.player.AsyncChatEvent

import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerQuitEvent

class AuthListener(): Listener {
    @EventHandler
    fun onQuit(event: PlayerQuitEvent) {
        AuthController.logout(event.player)
    }
}
package dev.thebjoredcraft.ultimatemoderation.player

import com.github.shynixn.mccoroutine.bukkit.launch
import dev.thebjoredcraft.ultimatemoderation.plugin
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerLoginEvent

class PlayerInitialListener(): Listener {
    @EventHandler
    fun onLogin(event: PlayerLoginEvent) {
        plugin.launch {
            val player = event.player
            val umPlayer = UMPlayer.getPlayer(player.uniqueId)

            umPlayer.edit {
                lastLogin = System.currentTimeMillis()
            }
        }
    }

    @EventHandler
    fun onJoin(event: PlayerJoinEvent) {
        plugin.launch {
            val player = event.player
            val umPlayer = UMPlayer.getPlayer(player.uniqueId)

            umPlayer.edit {
                lastJoin = System.currentTimeMillis()
            }

            player.inventory.contents = umPlayer.inventory.contents
        }
    }
}
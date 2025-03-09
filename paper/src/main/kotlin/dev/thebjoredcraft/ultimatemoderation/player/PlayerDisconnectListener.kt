package dev.thebjoredcraft.ultimatemoderation.player

import com.github.shynixn.mccoroutine.bukkit.launch
import dev.thebjoredcraft.ultimatemoderation.database.DatabaseProvider
import dev.thebjoredcraft.ultimatemoderation.plugin
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerLoginEvent
import org.bukkit.event.player.PlayerQuitEvent

class PlayerDisconnectListener(): Listener {
    @EventHandler
    fun onQuit(event: PlayerQuitEvent) {
        plugin.launch {
            val player = event.player
            val umPlayer = UMPlayer.getPlayer(player.uniqueId)

            DatabaseProvider.savePlayer(umPlayer)
        }
    }
}
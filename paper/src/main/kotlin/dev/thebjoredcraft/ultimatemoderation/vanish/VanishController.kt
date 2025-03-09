package dev.thebjoredcraft.ultimatemoderation.vanish

import dev.thebjoredcraft.ultimatemoderation.plugin
import it.unimi.dsi.fastutil.objects.ObjectArraySet
import org.bukkit.entity.Player

object VanishController {
    private val players = ObjectArraySet<Player>()

    private fun vanish(player: Player) {
        this.players.add(player)
        this.update()
    }

    private fun unVanish(player: Player) {
        this.players.remove(player)
        this.update()
    }

    fun toggle(player: Player) {
        if (this.isVanished(player)) {
            this.unVanish(player)
        } else {
            this.vanish(player)
        }
    }

    fun isVanished(player: Player): Boolean {
        return players.contains(player)
    }

    fun getVanishedPlayers(): ObjectArraySet<Player> {
        return players
    }

    fun update() {
        for (player in players) {
            for (online in player.server.onlinePlayers) {
                if (online != player) {
                    if (this.isVanished(online) && !player.hasPermission("ultimatemoderation.command.vanish.bypass")) {
                        player.hidePlayer(plugin, online)
                    } else {
                        player.showPlayer(plugin, online)
                    }
                }
            }
        }
    }
}
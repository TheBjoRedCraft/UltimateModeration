package dev.thebjoredcraft.ultimatemoderation.freeze

import org.bukkit.entity.Player
import java.util.*

object FreezeService {
    private val frozenPlayers = mutableSetOf<Player>()

    fun isFrozen(player: Player): Boolean {
        return player in frozenPlayers
    }

    private fun freeze(player: Player) {
        frozenPlayers.add(player)
    }

    private fun unfreeze(player: Player) {
        frozenPlayers.remove(player)

        player.freezeTicks = 0
        player.clearTitle()
    }

    fun toggle(player: Player) {
        if (this.isFrozen(player)) {
            this.unfreeze(player)
        } else {
            this.freeze(player)
        }
    }

    fun getFrozenPlayers(): Set<Player> {
        return frozenPlayers
    }
}
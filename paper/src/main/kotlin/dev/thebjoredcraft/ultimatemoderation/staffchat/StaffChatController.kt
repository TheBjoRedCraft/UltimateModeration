package dev.thebjoredcraft.ultimatemoderation.staffchat

import dev.thebjoredcraft.ultimatemoderation.UltimateModerationPaper
import dev.thebjoredcraft.ultimatemoderation.util.Colors
import dev.thebjoredcraft.ultimatemoderation.util.MessageBuilder
import it.unimi.dsi.fastutil.objects.ObjectArraySet
import it.unimi.dsi.fastutil.objects.ObjectSet
import net.kyori.adventure.text.Component
import org.bukkit.entity.Player

object StaffChatController {
    private val players = ObjectArraySet<Player>()

    fun inStaffChat(player: Player): Boolean {
        return player in players
    }

    private fun join(player: Player) {
        players.add(player)
    }

    fun chat(player: Player, message: String) {
        for (staff in players) {
            UltimateModerationPaper.send(MessageBuilder().primary(player.name).darkSpacer("@Staff: ").info(message), staff)
        }
    }

    fun chat(player: Player, message: Component) {
        for (staff in players) {
            UltimateModerationPaper.send(MessageBuilder().primary(player.name).darkSpacer("@Staff: ").component(message.color(Colors.INFO)), staff)
        }
    }

    fun leave(player: Player) {
        players.remove(player)
    }

    fun toggle(player: Player) {
        if (this.inStaffChat(player)) {
            this.leave(player)
        } else {
            this.join(player)
        }
    }

    fun getStaffChatPlayers(): ObjectSet<Player> {
        return players
    }
}
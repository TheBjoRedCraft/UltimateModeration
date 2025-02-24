package dev.thebjoredcraft.ultimatemoderation.listener

import dev.thebjoredcraft.ultimatemoderation.freeze.FreezeService
import dev.thebjoredcraft.ultimatemoderation.util.MessageBuilder
import net.kyori.adventure.title.Title

import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerMoveEvent


class MoveListener(): Listener {
    @EventHandler
    fun onMove(event: PlayerMoveEvent) {
        if(FreezeService.isFrozen(event.player)) {
            if(event.hasChangedBlock()) {
                event.isCancelled = true
                event.player.freezeTicks = 300

                event.player.showTitle(Title.title(MessageBuilder().error("Du bist eingefroren.").build(), MessageBuilder().info("Bitte melde dich bei einem Teammitglied.").build()))
            }
        }
    }
}
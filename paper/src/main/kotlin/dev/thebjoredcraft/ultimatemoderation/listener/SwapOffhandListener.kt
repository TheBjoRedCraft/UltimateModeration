package dev.thebjoredcraft.ultimatemoderation.listener

import dev.thebjoredcraft.ultimatemoderation.spectatemode.SpectateModeService
import org.bukkit.Bukkit
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerSwapHandItemsEvent

class SwapOffhandListener() : Listener {
    @EventHandler
    fun onSwapOffhand(event: PlayerSwapHandItemsEvent) {
        if(SpectateModeService.isSpectating(event.player)) {
            if(event.player.isSneaking) {
                SpectateModeService.back(event.player)
            } else {
                SpectateModeService.next(event.player)
            }
        }
    }
}
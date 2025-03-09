package dev.thebjoredcraft.ultimatemoderation.staffchat

import io.papermc.paper.event.player.AsyncChatEvent

import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerQuitEvent

class StaffChatListener(): Listener {
    @EventHandler
    fun onAsyncChat(event: AsyncChatEvent) {
        if(StaffChatController.inStaffChat(event.player)) {
            event.isCancelled = true

            for(player in StaffChatController.getStaffChatPlayers()) {
                StaffChatController.chat(player, event.message())
            }
        }
    }

    @EventHandler
    fun onQuit(event: PlayerQuitEvent) {
        StaffChatController.leave(event.player)
    }
}
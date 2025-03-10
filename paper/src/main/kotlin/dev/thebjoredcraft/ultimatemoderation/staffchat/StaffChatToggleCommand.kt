package dev.thebjoredcraft.ultimatemoderation.staffchat

import dev.jorel.commandapi.CommandAPICommand
import dev.jorel.commandapi.executors.PlayerCommandExecutor
import dev.thebjoredcraft.ultimatemoderation.UltimateModerationPaper
import dev.thebjoredcraft.ultimatemoderation.auth.AuthController
import dev.thebjoredcraft.ultimatemoderation.util.MessageBuilder

class StaffChatToggleCommand(commandName: String): CommandAPICommand(commandName) {
    init {
        withPermission("ultimatemoderation.command.staffchat")
        executesPlayer(PlayerCommandExecutor { player, args ->
            if(!AuthController.isLoggedIn(player)) {
                UltimateModerationPaper.send(MessageBuilder().error("Du musst eingeloggt sein, um diesen Befehl zu nutzen."), player)
                return@PlayerCommandExecutor
            }

            StaffChatController.toggle(player)

            if(StaffChatController.inStaffChat(player)) {
                UltimateModerationPaper.send(MessageBuilder().primary("Du bist nun im ").success("Staff-Chat").primary("."), player)
            } else {
                UltimateModerationPaper.send(MessageBuilder().primary("Du bist nun nicht mehr im ").error("Staff-Chat").primary("."), player)
            }
        })
    }
}
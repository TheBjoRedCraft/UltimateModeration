package dev.thebjoredcraft.ultimatemoderation.auth.command

import dev.jorel.commandapi.CommandAPICommand
import dev.jorel.commandapi.arguments.StringArgument
import dev.jorel.commandapi.executors.PlayerCommandExecutor
import dev.thebjoredcraft.ultimatemoderation.UltimateModerationPaper
import dev.thebjoredcraft.ultimatemoderation.auth.AuthController
import dev.thebjoredcraft.ultimatemoderation.util.MessageBuilder

class AuthLogoutCommand(commandName: String): CommandAPICommand(commandName) {
    init {
        withPermission("ultimatemoderation.command.auth.logout")
        executesPlayer(PlayerCommandExecutor { player, _ ->

            val success = AuthController.logout(player)

            if(success) {
                UltimateModerationPaper.send(MessageBuilder().primary("Du bist nun ").success("ausgeloggt."), player)
            } else {
                UltimateModerationPaper.send(MessageBuilder().primary("Du bist ").error("nicht eingeloggt."), player)
            }
        })
    }
}
package dev.thebjoredcraft.ultimatemoderation.auth.command

import dev.jorel.commandapi.CommandAPICommand
import dev.jorel.commandapi.arguments.StringArgument
import dev.jorel.commandapi.executors.PlayerCommandExecutor
import dev.thebjoredcraft.ultimatemoderation.UltimateModerationPaper
import dev.thebjoredcraft.ultimatemoderation.auth.AuthController
import dev.thebjoredcraft.ultimatemoderation.util.MessageBuilder

class AuthChangePasswordCommand(commandName: String): CommandAPICommand(commandName) {
    init {
        withPermission("ultimatemoderation.command.auth.block")
        withArguments(StringArgument("username"))
        withArguments(StringArgument("newPassword"))
        executesPlayer(PlayerCommandExecutor { player, args ->
            val username: String = args.getUnchecked("username") ?: return@PlayerCommandExecutor
            val newPassword: String = args.getUnchecked("newPassword") ?: return@PlayerCommandExecutor
            val success = AuthController.changePassword(username, newPassword)

            if(success) {
                UltimateModerationPaper.send(MessageBuilder().primary("Du hast das Passwort von ").info(username).primary(" auf ").info(newPassword).primary(" geändert."), player)
            } else {
                UltimateModerationPaper.send(MessageBuilder().error("Ein Account mit diesem Name existiert nicht."), player)
            }
        })
    }
}
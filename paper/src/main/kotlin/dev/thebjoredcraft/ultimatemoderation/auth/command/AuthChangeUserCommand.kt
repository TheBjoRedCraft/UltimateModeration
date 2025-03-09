package dev.thebjoredcraft.ultimatemoderation.auth.command

import dev.jorel.commandapi.CommandAPICommand
import dev.jorel.commandapi.arguments.StringArgument
import dev.jorel.commandapi.executors.PlayerCommandExecutor
import dev.thebjoredcraft.ultimatemoderation.UltimateModerationPaper
import dev.thebjoredcraft.ultimatemoderation.auth.AuthController
import dev.thebjoredcraft.ultimatemoderation.util.MessageBuilder

class AuthChangeUserCommand(commandName: String): CommandAPICommand(commandName) {
    init {
        withPermission("ultimatemoderation.command.auth.block")
        withArguments(StringArgument("username"))
        withArguments(StringArgument("newUsername"))
        executesPlayer(PlayerCommandExecutor { player, args ->
            val username: String = args.getUnchecked("username") ?: return@PlayerCommandExecutor
            val newUsername: String = args.getUnchecked("newUsername") ?: return@PlayerCommandExecutor
            val success = AuthController.changeUsername(username, newUsername)

            if(success) {
                UltimateModerationPaper.send(MessageBuilder().primary("Du hast den Benutzernamen von ").info(username).primary(" auf ").info(newUsername).primary(" geändert."), player)
            } else {
                UltimateModerationPaper.send(MessageBuilder().error("Ein Account mit diesem Name existiert nicht."), player)
            }
        })
    }
}
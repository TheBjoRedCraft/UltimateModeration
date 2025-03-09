package dev.thebjoredcraft.ultimatemoderation.auth.command

import dev.jorel.commandapi.CommandAPICommand
import dev.jorel.commandapi.arguments.StringArgument
import dev.jorel.commandapi.executors.PlayerCommandExecutor
import dev.thebjoredcraft.ultimatemoderation.UltimateModerationPaper
import dev.thebjoredcraft.ultimatemoderation.auth.AuthController
import dev.thebjoredcraft.ultimatemoderation.util.MessageBuilder

class AuthDeleteCommand(commandName: String): CommandAPICommand(commandName) {
    init {
        withPermission("ultimatemoderation.command.auth.delete")
        withArguments(StringArgument("username"))
        executesPlayer(PlayerCommandExecutor { player, args ->
            val username: String = args.getUnchecked("username") ?: return@PlayerCommandExecutor
            val success = AuthController.deleteAccount(username)

            if(success) {
                UltimateModerationPaper.send(MessageBuilder().primary("Du hast den Account ").info(username).error(" gelöscht."), player)
            } else {
                UltimateModerationPaper.send(MessageBuilder().error("Ein Account mit diesem Name existiert nicht."), player)
            }
        })
    }
}
package dev.thebjoredcraft.ultimatemoderation.auth.command

import dev.jorel.commandapi.CommandAPICommand
import dev.jorel.commandapi.arguments.StringArgument
import dev.jorel.commandapi.executors.PlayerCommandExecutor
import dev.thebjoredcraft.ultimatemoderation.auth.AuthController

class AuthLoginCommand(commandName: String): CommandAPICommand(commandName) {
    init {
        withPermission("ultimatemoderation.command.auth.login")
        withArguments(StringArgument("username"))
        withArguments(StringArgument("password"))
        executesPlayer(PlayerCommandExecutor { player, args ->
            val username: String = args.getUnchecked("username") ?: return@PlayerCommandExecutor
            val password: String = args.getUnchecked("password") ?: return@PlayerCommandExecutor

            AuthController.authenticate(player, username, password)
        })
    }
}
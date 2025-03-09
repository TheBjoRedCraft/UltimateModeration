package dev.thebjoredcraft.ultimatemoderation.auth.command

import dev.jorel.commandapi.CommandAPICommand

class AuthCommand(commandName: String): CommandAPICommand(commandName) {
    init {
        withPermission("ultimatemoderation.command.auth")
        withSubcommand(AuthLoginCommand("login"))
        withSubcommand(AuthLogoutCommand("logout"))
        withSubcommand(AuthListCommand("list"))

        withSubcommand(AuthCreateCommand("create"))
        withSubcommand(AuthDeleteCommand("delete"))

        withSubcommand(AuthChangePasswordCommand("changePassword"))
        withSubcommand(AuthChangeUserCommand("changeUsername"))

        withSubcommand(AuthBlockCommand("block"))
        withSubcommand(AuthUnBlockCommand("unblock"))
    }
}
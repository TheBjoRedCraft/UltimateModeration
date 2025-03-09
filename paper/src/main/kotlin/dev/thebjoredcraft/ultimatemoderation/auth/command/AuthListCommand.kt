package dev.thebjoredcraft.ultimatemoderation.auth.command

import dev.jorel.commandapi.CommandAPICommand
import dev.jorel.commandapi.arguments.IntegerArgument
import dev.jorel.commandapi.executors.PlayerCommandExecutor
import dev.thebjoredcraft.ultimatemoderation.auth.AuthController
import dev.thebjoredcraft.ultimatemoderation.util.MessageBuilder
import dev.thebjoredcraft.ultimatemoderation.util.PageableMessageBuilder

class AuthListCommand(commandName: String): CommandAPICommand(commandName) {
    init {
        withPermission("ultimatemoderation.command.auth.list")

        withOptionalArguments(IntegerArgument("page"))
        executesPlayer(PlayerCommandExecutor { player, args ->
            val page = args.getOrDefaultUnchecked("page", 1)
            val message = PageableMessageBuilder()

            message.setTitle(MessageBuilder().primary("Accounts auf diesem Server").build())
            message.setPageCommand("/auth list %page%")

            AuthController.accounts
              .sortedByDescending { AuthController.isLoggedIn(it.username) }
              .forEach { account ->
                  message.addLine(MessageBuilder().darkSpacer("- ").variableValue(account.username).darkSpacer(if (AuthController.isLoggedIn(account.username)) " (Active)" else "").build())
              }

            message.send(player, page)
        })
    }
}
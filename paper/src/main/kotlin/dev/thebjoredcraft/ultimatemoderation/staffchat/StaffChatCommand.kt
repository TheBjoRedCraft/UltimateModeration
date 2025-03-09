package dev.thebjoredcraft.ultimatemoderation.staffchat

import dev.jorel.commandapi.CommandAPICommand
import dev.jorel.commandapi.arguments.GreedyStringArgument
import dev.jorel.commandapi.executors.PlayerCommandExecutor
import dev.thebjoredcraft.ultimatemoderation.util.MessageBuilder

class StaffChatCommand(commandName: String): CommandAPICommand(commandName) {
    init {
        withPermission("ultimatemoderation.command.staffchat")
        withSubcommands(StaffChatToggleCommand("toggle"))
        withArguments(GreedyStringArgument("message"))

        executesPlayer(PlayerCommandExecutor { player, args ->
            val message = args.getUnchecked<String>("message") ?: return@PlayerCommandExecutor
            StaffChatController.chat(player, message)
        })
    }
}
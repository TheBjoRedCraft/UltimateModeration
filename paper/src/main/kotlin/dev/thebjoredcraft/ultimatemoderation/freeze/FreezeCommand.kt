package dev.thebjoredcraft.ultimatemoderation.freeze

import dev.jorel.commandapi.CommandAPICommand
import dev.jorel.commandapi.executors.PlayerCommandExecutor
import dev.thebjoredcraft.ultimatemoderation.UltimateModerationPaper
import dev.thebjoredcraft.ultimatemoderation.util.MessageBuilder

class FreezeCommand(commandName: String): CommandAPICommand(commandName) {
    init {
        withPermission("ultimatemoderation.command.freeze")
        withSubcommands(FreezeListCommand("list"))
        withSubcommands(FreezeToggleCommand("toggle"))
    }
}
package dev.thebjoredcraft.ultimatemoderation.spectatemode

import dev.jorel.commandapi.CommandAPICommand
import dev.jorel.commandapi.executors.PlayerCommandExecutor

class SpectateModeCommand(commandName: String): CommandAPICommand(commandName) {
    init {
        withPermission("ultimatemoderation.command.spectatemode")
        executesPlayer(PlayerCommandExecutor() { player, args ->
            SpectateModeService.toggle(player)
        })
    }
}
package dev.thebjoredcraft.ultimatemoderation.internal

import dev.jorel.commandapi.CommandAPICommand

class UltimateModerationCommand(commandName: String): CommandAPICommand(commandName) {
    init {
        withPermission("ultimatemoderation.command")

    }
}
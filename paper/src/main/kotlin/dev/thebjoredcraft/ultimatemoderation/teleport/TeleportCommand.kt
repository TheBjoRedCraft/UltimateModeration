package dev.thebjoredcraft.ultimatemoderation.teleport

import dev.jorel.commandapi.CommandAPICommand

class TeleportCommand(commandName: String): CommandAPICommand(commandName) {
    init {
        withPermission("ultimatemoderation.command.teleport")


    }
}
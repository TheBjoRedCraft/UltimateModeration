package dev.thebjoredcraft.ultimatemoderation.punishment

import dev.jorel.commandapi.CommandAPICommand

class PunishmentCommand(commandName: String): CommandAPICommand(commandName) {
    init {
        withPermission("ultimatemoderation.command.punishment")

    }
}
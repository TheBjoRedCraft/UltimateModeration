package dev.thebjoredcraft.ultimatemoderation.spectatemode

import dev.jorel.commandapi.CommandAPICommand
import dev.jorel.commandapi.executors.PlayerCommandExecutor
import dev.thebjoredcraft.ultimatemoderation.UltimateModerationPaper
import dev.thebjoredcraft.ultimatemoderation.util.MessageBuilder

class SpectateModeCommand(commandName: String): CommandAPICommand(commandName) {
    init {
        withPermission("ultimatemoderation.command.spectatemode")
        withSubcommands(SpectateModeListCommand("list"))
        executesPlayer(PlayerCommandExecutor() { player, args ->
            SpectateModeService.toggle(player)

            if(SpectateModeService.isSpectating(player)) {
                UltimateModerationPaper.send(MessageBuilder().primary("Du bist nun im ").success("Spectate-Modus"), player)
            } else {
                UltimateModerationPaper.send(MessageBuilder().primary("Du bist nun nicht mehr im ").error("Spectate-Modus"), player)
            }
        })
    }
}
package dev.thebjoredcraft.ultimatemoderation.spectatemode

import dev.jorel.commandapi.CommandAPICommand
import dev.jorel.commandapi.executors.PlayerCommandExecutor
import dev.thebjoredcraft.ultimatemoderation.UltimateModerationPaper
import dev.thebjoredcraft.ultimatemoderation.auth.AuthController
import dev.thebjoredcraft.ultimatemoderation.util.MessageBuilder

class SpectateModeCommand(commandName: String): CommandAPICommand(commandName) {
    init {
        withPermission("ultimatemoderation.command.spectatemode")
        withSubcommands(SpectateModeListCommand("list"))
        executesPlayer(PlayerCommandExecutor() { player, args ->
            if(!AuthController.isLoggedIn(player)) {
                UltimateModerationPaper.send(MessageBuilder().error("Du musst eingeloggt sein, um diesen Befehl zu nutzen."), player)
                return@PlayerCommandExecutor
            }

            SpectateModeService.toggle(player)

            if(SpectateModeService.isSpectating(player)) {
                UltimateModerationPaper.send(MessageBuilder().primary("Du bist nun im ").success("Spectate-Modus").primary("."), player)
            } else {
                UltimateModerationPaper.send(MessageBuilder().primary("Du bist nun nicht mehr im ").error("Spectate-Modus").primary("."), player)
            }
        })
    }
}
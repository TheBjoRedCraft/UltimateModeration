package dev.thebjoredcraft.ultimatemoderation.spectatemode

import dev.jorel.commandapi.CommandAPICommand
import dev.jorel.commandapi.arguments.IntegerArgument
import dev.jorel.commandapi.executors.PlayerCommandExecutor
import dev.thebjoredcraft.ultimatemoderation.UltimateModerationPaper
import dev.thebjoredcraft.ultimatemoderation.util.MessageBuilder
import dev.thebjoredcraft.ultimatemoderation.util.PageableMessageBuilder

class SpectateModeListCommand(commandName: String): CommandAPICommand(commandName) {
    init {
        withPermission("ultimatemoderation.command.spectatemode")
        withOptionalArguments(IntegerArgument("page"))
        executesPlayer(PlayerCommandExecutor() { player, args ->
            val message = PageableMessageBuilder()
            val page = args.getOrDefaultUnchecked("page", 1)

            if(SpectateModeService.getSpectatingPlayers().isEmpty()) {
                UltimateModerationPaper.send(MessageBuilder().error("Es sind keine Spieler in Spectate-Mode."), player)
                return@PlayerCommandExecutor
            }

            message.setPageCommand("/spectatemode list %page%")

            var index = 1

            for (spectatingPlayer in SpectateModeService.getSpectatingPlayers()) {
                message.addLine(MessageBuilder().variableValue("$index. ").primary(spectatingPlayer.name))

                index++;
            }

            message.send(player, page)
        })
    }
}
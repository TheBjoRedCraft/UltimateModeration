package dev.thebjoredcraft.ultimatemoderation.freeze

import dev.jorel.commandapi.CommandAPICommand
import dev.jorel.commandapi.arguments.IntegerArgument
import dev.jorel.commandapi.executors.PlayerCommandExecutor
import dev.thebjoredcraft.ultimatemoderation.UltimateModerationPaper
import dev.thebjoredcraft.ultimatemoderation.util.MessageBuilder
import dev.thebjoredcraft.ultimatemoderation.util.PageableMessageBuilder

class FreezeListCommand(commandName: String): CommandAPICommand(commandName) {
    init {
        withOptionalArguments(IntegerArgument("page"))
        executesPlayer(PlayerCommandExecutor() { player, args ->
            val message = PageableMessageBuilder()
            val page = args.getOrDefaultUnchecked("page", 1)

            if(FreezeService.getFrozenPlayers().isEmpty()) {
                UltimateModerationPaper.send(MessageBuilder().error("Es sind keine Spieler im Freeze-Modus."), player)
                return@PlayerCommandExecutor
            }

            message.setPageCommand("/freeze list %page%")

            var index = 1

            for (frozenPlayer in FreezeService.getFrozenPlayers()) {
                message.addLine(MessageBuilder().variableValue("$index. ").primary(frozenPlayer.name).command(MessageBuilder().darkSpacer(" (Klicke, zum teleportieren)"), MessageBuilder().darkSpacer(" (Klicke, zum teleportieren)"), "/tp " + frozenPlayer.name).build())
                index++;
            }

            message.send(player, page)
        })
    }
}
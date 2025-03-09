package dev.thebjoredcraft.ultimatemoderation.vanish

import dev.jorel.commandapi.CommandAPICommand
import dev.jorel.commandapi.arguments.IntegerArgument
import dev.jorel.commandapi.arguments.PlayerArgument
import dev.jorel.commandapi.executors.PlayerCommandExecutor
import dev.thebjoredcraft.ultimatemoderation.util.MessageBuilder
import dev.thebjoredcraft.ultimatemoderation.util.PageableMessageBuilder
import org.bukkit.entity.Player

class VanishListCommand(commandName: String): CommandAPICommand(commandName) {
    init {
        withPermission("ultimatemoderation.command.vanish.list")

        withArguments(IntegerArgument("page"))
        executesPlayer(PlayerCommandExecutor { player, args ->
            val page = args.getOrDefaultUnchecked("page", 1)
            val message = PageableMessageBuilder()

            message.setTitle(MessageBuilder().primary("Spieler im Vanish-Modus").build())
            message.setPageCommand("/vanish list %page%")

            VanishController.getVanishedPlayers().forEach { vanishedPlayer ->
                message.addLine(MessageBuilder().darkSpacer("- ").variableValue(vanishedPlayer.name).build())
            }

            message.send(player, page)
        })
    }
}
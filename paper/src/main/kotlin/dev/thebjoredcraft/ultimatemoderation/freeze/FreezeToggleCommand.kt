package dev.thebjoredcraft.ultimatemoderation.freeze

import dev.jorel.commandapi.CommandAPICommand
import dev.jorel.commandapi.arguments.PlayerArgument
import dev.jorel.commandapi.executors.PlayerCommandExecutor
import dev.thebjoredcraft.ultimatemoderation.UltimateModerationPaper
import dev.thebjoredcraft.ultimatemoderation.util.MessageBuilder
import org.bukkit.entity.Player

class FreezeToggleCommand(commandName: String): CommandAPICommand(commandName) {
    init {
        withArguments(PlayerArgument("player"))
        executesPlayer(PlayerCommandExecutor() { player, args ->
            val target = args.getUnchecked<Player>("player") ?: return@PlayerCommandExecutor

            FreezeService.toggle(target)

            if(FreezeService.isFrozen(target)) {
                UltimateModerationPaper.send(MessageBuilder().primary("Du hast ").success(target.name).primary(" eingefroren."), player)
            } else {
                UltimateModerationPaper.send(MessageBuilder().primary("Du hast ").error(target.name).primary(" aufgetaut."), player)
            }
        })
    }
}
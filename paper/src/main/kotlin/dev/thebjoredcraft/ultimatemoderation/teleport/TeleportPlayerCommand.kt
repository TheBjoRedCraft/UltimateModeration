package dev.thebjoredcraft.ultimatemoderation.teleport

import dev.jorel.commandapi.CommandAPICommand
import dev.jorel.commandapi.arguments.EntitySelectorArgument
import dev.jorel.commandapi.arguments.PlayerArgument
import dev.jorel.commandapi.executors.PlayerCommandExecutor
import dev.thebjoredcraft.ultimatemoderation.UltimateModerationPaper
import dev.thebjoredcraft.ultimatemoderation.util.MessageBuilder
import org.bukkit.entity.Player

class TeleportPlayerCommand(commandName: String): CommandAPICommand(commandName) {
    init {
        withPermission("ultimatemoderation.command.teleport.player")
        withArguments(PlayerArgument("player"))
        withOptionalArguments(PlayerArgument("target"))

        executesPlayer(PlayerCommandExecutor { player, args ->
            val target: Player = args.getUnchecked("player") ?: return@PlayerCommandExecutor
            val otherTarget: Player? = args.getUnchecked("target")

            if(otherTarget == null) {
                player.teleport(target)
                UltimateModerationPaper.send(MessageBuilder().primary("Du wurdest zu ").variableKey(target.name).primary(" teleportiert."), player)
                return@PlayerCommandExecutor
            }

            target.teleport(otherTarget)
            UltimateModerationPaper.send(MessageBuilder().variableKey(target.name).primary(" wurde zu ").variableKey(otherTarget.name).primary(" teleportiert."), player)
        })
    }
}
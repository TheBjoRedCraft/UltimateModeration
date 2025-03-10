package dev.thebjoredcraft.ultimatemoderation.vanish

import dev.jorel.commandapi.CommandAPICommand
import dev.jorel.commandapi.arguments.PlayerArgument
import dev.jorel.commandapi.executors.PlayerCommandExecutor
import dev.thebjoredcraft.ultimatemoderation.UltimateModerationPaper
import dev.thebjoredcraft.ultimatemoderation.auth.AuthController
import dev.thebjoredcraft.ultimatemoderation.util.MessageBuilder
import org.bukkit.entity.Player

class VanishCommand(commandName: String): CommandAPICommand(commandName) {
    init {
        withPermission("ultimatemoderation.command.vanish")
        withSubcommand(VanishListCommand("list"))
        withOptionalArguments(PlayerArgument("player"))

        executesPlayer(PlayerCommandExecutor { player, args ->
            val target: Player = args.getOrDefaultUnchecked("player", player) ?: return@PlayerCommandExecutor

            if(!AuthController.isLoggedIn(player)) {
                UltimateModerationPaper.send(MessageBuilder().error("Du musst eingeloggt sein, um diesen Befehl zu nutzen."), player)
                return@PlayerCommandExecutor
            }

            VanishController.toggle(target)

            if(VanishController.isVanished(target)) {
                if(player == target) {
                    UltimateModerationPaper.send(MessageBuilder().info("Du ").primary("bist nun ").success("im Vanish"), player)
                } else {
                    UltimateModerationPaper.send(MessageBuilder().info(target.name).primary("ist nun ").success("im Vanish"), player)
                }
            } else {
                if(player == target) {
                    UltimateModerationPaper.send(MessageBuilder().info("Du ").primary("bist nun nicht mehr").error("im Vanish"), player)
                } else {
                    UltimateModerationPaper.send(MessageBuilder().info(target.name).primary("ist nun nicht mehr ").error("im Vanish"), player)
                }
            }
        })
    }
}
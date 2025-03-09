package dev.thebjoredcraft.ultimatemoderation.internal

import com.github.shynixn.mccoroutine.bukkit.launch
import dev.jorel.commandapi.CommandAPICommand
import dev.jorel.commandapi.executors.PlayerCommandExecutor
import dev.thebjoredcraft.ultimatemoderation.UltimateModerationPaper
import dev.thebjoredcraft.ultimatemoderation.plugin
import dev.thebjoredcraft.ultimatemoderation.util.MessageBuilder
import kotlin.system.measureTimeMillis

class InternalReloadCommand(commandName: String): CommandAPICommand(commandName) {
    init {
        withPermission("ultimatemoderation.command.reload")
        executesPlayer(PlayerCommandExecutor { player, _ ->
            plugin.launch {
                measureTimeMillis {
                    plugin.reload()
                }.also {
                    UltimateModerationPaper.send(MessageBuilder().primary("Das Plugin wurde neu geladen. ").darkSpacer("(").success(it.toString()).success("ms").darkSpacer(")"), player)
                }
            }
        })
    }
}
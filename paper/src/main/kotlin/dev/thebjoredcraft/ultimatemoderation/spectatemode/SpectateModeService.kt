package dev.thebjoredcraft.ultimatemoderation.spectatemode

import dev.thebjoredcraft.ultimatemoderation.plugin
import dev.thebjoredcraft.ultimatemoderation.util.Colors
import dev.thebjoredcraft.ultimatemoderation.util.MessageBuilder
import it.unimi.dsi.fastutil.objects.ObjectArraySet
import it.unimi.dsi.fastutil.objects.ObjectSet
import net.kyori.adventure.text.format.TextColor
import net.kyori.adventure.text.format.TextDecoration
import org.bukkit.Bukkit
import org.bukkit.GameMode
import org.bukkit.entity.Player
import kotlin.random.Random

object SpectateModeService {
    private val players: ObjectSet<Player> = ObjectArraySet()
    private val previousPlayers: MutableMap<Player, ArrayDeque<Player>> = mutableMapOf()

    fun startTask() {
        Bukkit.getScheduler().runTaskTimerAsynchronously(plugin, Runnable {
            for (player in players) {
                player.sendActionBar(MessageBuilder().primary("ᴅʀüᴄᴋᴇ ").miniMessage(Colors.INFO.asMMString() + "<key:key.sneak> + <key:key.swapOffhand>").primary(" ᴜᴍ ᴢᴜʀüᴄᴋᴢᴜɢᴇʜᴇɴ, ᴅʀüᴄᴋᴇ ").miniMessage(Colors.INFO.asMMString() + "<key:key.swapOffhand>").primary(" ᴜᴍ ᴡᴇɪᴛᴇʀᴢᴜɢᴇʜᴇɴ").build().decorate(TextDecoration.BOLD))
            }
        }, 0, 40)
    }

    private fun startSpectateMode(player: Player) {
        players.add(player)
        previousPlayers[player] = ArrayDeque()

        player.allowFlight = true
        player.isFlying = true

        this.updateHiding()
    }

    private fun stopSpectateMode(player: Player) {
        players.remove(player)
        previousPlayers.remove(player)

        if (player.gameMode != GameMode.CREATIVE && player.gameMode != GameMode.SPECTATOR) {
            player.isFlying = false
            player.allowFlight = false
        }
        this.updateHiding()
    }

    fun isSpectating(player: Player): Boolean {
        return players.contains(player)
    }

    fun toggle(player: Player) {
        if (isSpectating(player)) {
            stopSpectateMode(player)
        } else {
            startSpectateMode(player)
        }
    }

    fun back(player: Player) {
        val history = previousPlayers[player]

        if (history != null && history.size > 1) {
            history.removeLast()
            val previousPlayer = history.removeLast()
            player.teleport(previousPlayer)
        } else {
            player.sendActionBar(MessageBuilder().error("Es gibt keine weiteren Spieler zum Zuschauen.").build().decorate(TextDecoration.BOLD))
        }
    }

    fun next(player: Player) {
        val onlinePlayers = Bukkit.getOnlinePlayers().filter { it != player && (previousPlayers[player]?.lastOrNull() != it) }

        if (onlinePlayers.isNotEmpty()) {
            val nextPlayer = onlinePlayers[Random.nextInt(onlinePlayers.size)]
            previousPlayers[player]?.addLast(nextPlayer)
            player.teleport(nextPlayer)
        } else {
            player.sendActionBar(MessageBuilder().error("Es gibt keine weiteren Spieler zum Zuschauen.").build().decorate(TextDecoration.BOLD))
        }
    }

    fun getSpectatingPlayers(): ObjectSet<Player> {
        return players
    }

    private fun updateHiding() {
        for (player in Bukkit.getOnlinePlayers()) {
            for (spectator in players) {
                if (player.hasPermission("spectate.bypass") || players.contains(player)) {
                    player.showPlayer(plugin, spectator)
                } else {
                    player.hidePlayer(plugin, spectator)
                }
            }
        }
    }

    private fun TextColor.asMMString(): String {
        return "<" + this.asHexString() + ">"
    }
}
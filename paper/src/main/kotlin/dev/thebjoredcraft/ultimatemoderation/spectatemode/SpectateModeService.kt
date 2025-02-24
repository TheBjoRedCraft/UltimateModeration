package dev.thebjoredcraft.ultimatemoderation.spectatemode

import dev.thebjoredcraft.ultimatemoderation.util.Colors
import dev.thebjoredcraft.ultimatemoderation.util.MessageBuilder
import it.unimi.dsi.fastutil.objects.ObjectArraySet
import it.unimi.dsi.fastutil.objects.ObjectSet
import kotlinx.coroutines.delay
import net.kyori.adventure.text.format.TextDecoration
import org.bukkit.Bukkit
import org.bukkit.GameMode
import org.bukkit.entity.Player
import kotlin.random.Random

object SpectateModeService {
    private val players: ObjectSet<Player> = ObjectArraySet()
    private val previousPlayers: MutableMap<Player, ArrayDeque<Player>> = mutableMapOf()

    suspend fun startTask() {
        while (players.isNotEmpty()) {
            delay(2500)

            for (player in players) {
                player.sendActionBar(MessageBuilder().primary("ᴅʀüᴄᴋᴇ ").miniMessage(Colors.INFO.asHexString() + "<key:key.sneak> + <key:key.swapOffhand").primary(" ᴜᴍ ᴢᴜʀüᴄᴋᴢᴜɢᴇʜᴇɴ, ᴅʀüᴄᴋᴇ ").miniMessage(Colors.INFO.asHexString() + "<key:key.swapOffhand").primary(" ᴜᴍ ᴡᴇɪᴛᴇʀᴢᴜɢᴇʜᴇɴ").build().decorate(TextDecoration.BOLD))
            }
        }
    }

    fun startSpectateMode(player: Player) {
        players.add(player)
        previousPlayers[player] = ArrayDeque()

        player.allowFlight = true
        player.isFlying = true
    }

    fun stopSpectateMode(player: Player) {
        players.remove(player)
        previousPlayers.remove(player)

        if(player.gameMode != GameMode.CREATIVE || player.gameMode != GameMode.SPECTATOR) {
            player.isFlying = false
            player.allowFlight = false
        }
    }

    fun isSpectating(player: Player): Boolean {
        return players.contains(player)
    }

    fun toggle(player: Player) {
        if (isSpectating(player)) {
            this.stopSpectateMode(player)
        } else {
            this.startSpectateMode(player)
        }
    }

    fun back(player: Player) {
        val history = previousPlayers[player]

        if (!history.isNullOrEmpty()) {
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
}
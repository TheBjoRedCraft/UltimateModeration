package dev.thebjoredcraft.ultimatemoderation.player

import com.github.benmanes.caffeine.cache.Caffeine
import com.github.benmanes.caffeine.cache.RemovalCause
import com.github.shynixn.mccoroutine.bukkit.launch
import dev.hsbrysk.caffeine.CoroutineLoadingCache
import dev.hsbrysk.caffeine.buildCoroutine
import dev.thebjoredcraft.ultimatemoderation.database.DatabaseProvider
import dev.thebjoredcraft.ultimatemoderation.plugin
import org.bukkit.Bukkit
import org.bukkit.GameMode
import org.bukkit.Location
import org.bukkit.event.inventory.InventoryType
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.PlayerInventory
import java.util.UUID
import java.util.concurrent.TimeUnit

class UMPlayer (
    val uuid: UUID,
    var name: String = "Unknown",

    var isMuted: Boolean = false,
    var isBanned: Boolean = false,
    var isFrozen: Boolean = false,
    var isVanished: Boolean = false,
    var isInvincible: Boolean = false,
    var inSpectateMode: Boolean = false,

    var latestHost: String = "",
    var latestClient: String = "",
    var latestAddress: String = "",
    var latestGeoLocation: String = "",

    var latestLocation: Location = Bukkit.getWorlds().first().spawnLocation,
    var respawnLocation: Location = Bukkit.getWorlds().first().spawnLocation,
    var bedLocation: Location = Bukkit.getWorlds().first().spawnLocation,

    var allowFlight: Boolean = false,
    var isFlying: Boolean = false,
    var flySpeed: Double = 1.5,

    var walkSpeed: Double = 1.5,

    var lastLogin: Long = -1,
    var lastLogout: Long = -1,
    var lastJoin: Long = -1,

    var latestViewDistance: Int = 16,
    var simulationDistance: Int = 16,

    var latestCompassTarget: String = "Unknown",
    var latestHitCooldown: Long = 0,

    var latestLocale: String = "German",
    var latestPing: Int = -1,
    var latestTimeStamp: Long = -1,
    var latestTimeZoneOffset: Long = -1,

    var health: Double = 20.0,
    var healthScale: Double = 20.0,
    var foodLevel: Int = 20,
    var totalExperience: Int = 0,

    var gameMode: GameMode = Bukkit.getDefaultGameMode(),
    var previousGamemode: GameMode = Bukkit.getDefaultGameMode(),

    var spectatorTarget: String = "",
    var inventory: PlayerInventory = Bukkit.createInventory(null, InventoryType.PLAYER) as PlayerInventory,
    var enderChest: Inventory = Bukkit.createInventory(null, InventoryType.PLAYER)
) {
    companion object {
        private val cache: CoroutineLoadingCache<UUID, UMPlayer> = Caffeine
            .newBuilder()
            .removalListener<UUID, UMPlayer> { _: UUID?, user: UMPlayer?, _: RemovalCause -> plugin.launch { DatabaseProvider.savePlayer(user as UMPlayer) } }
            .expireAfterWrite(30, TimeUnit.MINUTES)
            .buildCoroutine { uuid: UUID -> DatabaseProvider.loadPlayer(uuid) }

        suspend fun getPlayer(uuid: UUID): UMPlayer {
            return cache.get(uuid) ?: DatabaseProvider.emptyPlayer(uuid)
        }
    }


    fun edit(block: UMPlayer.() -> Unit) {
        apply { block() }

        cache.put(uuid, this)
    }
}
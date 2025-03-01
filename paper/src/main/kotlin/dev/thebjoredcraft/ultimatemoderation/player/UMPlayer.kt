package dev.thebjoredcraft.ultimatemoderation.player

import com.github.benmanes.caffeine.cache.Caffeine
import dev.hsbrysk.caffeine.CoroutineLoadingCache
import dev.hsbrysk.caffeine.buildCoroutine
import dev.thebjoredcraft.ultimatemoderation.database.DatabaseProvider
import dev.thebjoredcraft.ultimatemoderation.punishment.Punishment
import it.unimi.dsi.fastutil.objects.ObjectArrayList
import it.unimi.dsi.fastutil.objects.ObjectList
import java.util.UUID

class UMPlayer (
    val uuid: UUID,
    val name: String = "Unbekannt",

    var real: Boolean = false,

    var isMuted: Boolean = false,
    var isBanned: Boolean = false,
    var isFrozen: Boolean = false,
    var isVanished: Boolean = false,
    var isInvincible: Boolean = false,
    var inSpectateMode: Boolean = false,

    var isLoggedIn: Boolean = false,
    var password: String = "",

    var punishments: ObjectList<Punishment> = ObjectArrayList(),

    var latestAddress: String = "Unbekannt",
    ) {
    private val cache: CoroutineLoadingCache<UUID, UMPlayer> = Caffeine
        .newBuilder()
        .buildCoroutine { key -> DatabaseProvider.loadPlayer(key) }

}
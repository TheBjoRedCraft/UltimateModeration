package dev.thebjoredcraft.ultimatemoderation.player

import java.util.UUID

class UMPlayer (
    val uuid: UUID,
    val name: String = "Unbekannt",

    var isMuted: Boolean = false,
    var isBanned: Boolean = false,
    var isFrozen: Boolean = false,
    var isVanished: Boolean = false,
    var isInvincible: Boolean = false,
    var inSpectateMode: Boolean = false,

    var isLoggedIn: Boolean = false,
    var password: String = "",

    var latestAddress: String = "Unbekannt",
    ) {

}
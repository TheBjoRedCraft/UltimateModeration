package dev.thebjoredcraft.ultimatemoderation.punishment

import org.bukkit.OfflinePlayer

interface Punishment {
    val reason: String
    val expiration: Long
    val staff: String

    fun execute(target: OfflinePlayer)
}
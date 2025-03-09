package dev.thebjoredcraft.ultimatemoderation.punishment

import org.bukkit.OfflinePlayer
import java.util.UUID

interface Punishment {
    val id : Int
    val uuid: UUID
    val type: String
    val reason: String
    val expiration: Long
    val creation: Long
    val staff: String

    fun execute(target: OfflinePlayer)
}
package dev.thebjoredcraft.ultimatemoderation.punishment.impl

import dev.thebjoredcraft.ultimatemoderation.punishment.Punishment
import org.bukkit.OfflinePlayer

class BanPunishment (
    override val reason: String,
    override val expiration: Long,
    override val staff: String
): Punishment {
    override fun execute(target: OfflinePlayer) {
        TODO("Not yet implemented")
    }
}
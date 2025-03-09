package dev.thebjoredcraft.ultimatemoderation.punishment.impl

import dev.thebjoredcraft.ultimatemoderation.punishment.Punishment
import org.bukkit.OfflinePlayer
import java.util.*

class WarnPunishment (
    override val reason: String,
    override val expiration: Long,
    override val staff: String,
    override val id: Int,
    override val uuid: UUID,
    override val type: String,
    override val creation: Long
): Punishment {
    override fun execute(target: OfflinePlayer) {
        TODO("Not yet implemented")
    }
}
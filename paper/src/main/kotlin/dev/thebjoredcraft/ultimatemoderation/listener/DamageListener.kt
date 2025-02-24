package dev.thebjoredcraft.ultimatemoderation.listener

import dev.thebjoredcraft.ultimatemoderation.freeze.FreezeService
import net.kyori.adventure.sound.Sound
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityDamageByEntityEvent
import org.bukkit.event.entity.EntityDamageEvent

class DamageListener(): Listener {
    @EventHandler
    fun onDamage(event: EntityDamageEvent) {
        if(event.entity is Player) {
            if(FreezeService.isFrozen(event.entity as Player)) {
                event.isCancelled = true

                event.entity.playSound(Sound.sound(org.bukkit.Sound.ENTITY_ENDER_DRAGON_HURT, Sound.Source.PLAYER, 1f, 1f), Sound.Emitter.self())
            }
        }
    }

    @EventHandler
    fun onDamage(event: EntityDamageByEntityEvent) {
        if(event.entity is Player) {
            if(FreezeService.isFrozen(event.entity as Player)) {
                event.damager.playSound(Sound.sound(org.bukkit.Sound.ENTITY_ENDER_DRAGON_HURT, Sound.Source.PLAYER, 1f, 1f), Sound.Emitter.self())
            }
        }

        if(event.damager is Player) {
            if(FreezeService.isFrozen(event.damager as Player)) {
                event.isCancelled = true
            }
        }
    }
}
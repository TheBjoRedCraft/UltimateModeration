package dev.thebjoredcraft.ultimatemoderation

import com.github.shynixn.mccoroutine.bukkit.SuspendingJavaPlugin
import org.bukkit.plugin.java.JavaPlugin.getPlugin

val plugin: UltimateModerationPaper get() = getPlugin(UltimateModerationPaper::class.java)

class UltimateModerationPaper(): SuspendingJavaPlugin() {
    override suspend fun onEnableAsync() {

    }
}
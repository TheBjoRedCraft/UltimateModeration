package dev.thebjoredcraft.ultimatemoderation

import com.github.shynixn.mccoroutine.bukkit.SuspendingJavaPlugin
import dev.thebjoredcraft.ultimatemoderation.auth.AuthListener
import dev.thebjoredcraft.ultimatemoderation.auth.command.AuthCommand
import dev.thebjoredcraft.ultimatemoderation.database.DatabaseProvider
import dev.thebjoredcraft.ultimatemoderation.freeze.FreezeCommand
import dev.thebjoredcraft.ultimatemoderation.listener.DamageListener
import dev.thebjoredcraft.ultimatemoderation.listener.MoveListener
import dev.thebjoredcraft.ultimatemoderation.listener.SwapOffhandListener
import dev.thebjoredcraft.ultimatemoderation.spectatemode.SpectateModeCommand
import dev.thebjoredcraft.ultimatemoderation.spectatemode.SpectateModeService
import dev.thebjoredcraft.ultimatemoderation.staffchat.StaffChatCommand
import dev.thebjoredcraft.ultimatemoderation.staffchat.StaffChatListener
import dev.thebjoredcraft.ultimatemoderation.util.Colors
import dev.thebjoredcraft.ultimatemoderation.util.MessageBuilder

import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.plugin.java.JavaPlugin.getPlugin

val plugin: UltimateModerationPaper get() = getPlugin(UltimateModerationPaper::class.java)

class UltimateModerationPaper(): SuspendingJavaPlugin() {
    override fun onEnable() {
        SpectateModeCommand("spectatemode").register()
        FreezeCommand("freeze").register()
        StaffChatCommand("staffchat").register()
        AuthCommand("auth").register()

        Bukkit.getPluginManager().registerEvents(SwapOffhandListener(), this)
        Bukkit.getPluginManager().registerEvents(DamageListener(), this)
        Bukkit.getPluginManager().registerEvents(MoveListener(), this)
        Bukkit.getPluginManager().registerEvents(StaffChatListener(), this)
        Bukkit.getPluginManager().registerEvents(AuthListener(), this)

        SpectateModeService.startTask()
    }

    override suspend fun onEnableAsync() {
        DatabaseProvider.connect()

        DatabaseProvider.loadAccounts()
    }

    override suspend fun onDisableAsync() {
        DatabaseProvider.saveAccounts()
    }

    companion object {
        fun send(messageBuilder: MessageBuilder, vararg players: Player) {
            players.forEach { player -> player.sendMessage(Colors.PREFIX.append(messageBuilder.build())) }
        }

        fun disable(reason: String) {
            Bukkit.getConsoleSender().sendMessage(MessageBuilder().withPrefix().error(reason).build())
            Bukkit.getPluginManager().disablePlugin(plugin)
        }
    }
}
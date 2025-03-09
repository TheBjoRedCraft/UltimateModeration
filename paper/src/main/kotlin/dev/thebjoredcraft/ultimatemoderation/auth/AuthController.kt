package dev.thebjoredcraft.ultimatemoderation.auth

import it.unimi.dsi.fastutil.objects.Object2ObjectMap
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap
import it.unimi.dsi.fastutil.objects.ObjectArraySet
import org.bukkit.entity.Player

object AuthController {
    private val players = Object2ObjectOpenHashMap<Player, AuthAccount>()
    val accounts = ObjectArraySet<AuthAccount>()

    fun authenticate(player: Player, username: String, password: String): Boolean {
        if(!accounts.any { it.username == username }) {
            return false
        }

        val account = accounts.first { it.username == username }

        if(account.password != password) {
            return false
        }

        if(account.blocked) {
            return false
        }

        this.login(player, account)
        return true
    }

    private fun login(player: Player, account: AuthAccount) {
        players[player] = account
    }

    fun logout(player: Player): Boolean {
        val account = players[player] ?: return false

        return players.remove(player, account)
    }

    fun createAccount(username: String, password: String): Boolean {
        if(accounts.any { it.username == username }) {
            return false
        }

        accounts.add(AuthAccount(username, password, false))
        return true
    }

    fun deleteAccount(username: String): Boolean {
        if(!accounts.any { it.username == username }) {
            return false
        }

        val account = accounts.first { it.username == username }

        accounts.remove(account)

        return true
    }

    fun blockAccount(username: String): Boolean {
        val account = accounts.firstOrNull { it.username == username } ?: return false

        account.edit {
            this.blocked = true
        }

        return true
    }

    fun unblockAccount(username: String): Boolean {
        val account = accounts.firstOrNull { it.username == username } ?: return false

        account.edit {
            this.blocked = false
        }

        return true
    }

    fun changePassword(username: String, password: String): Boolean {
        val account = accounts.firstOrNull { it.username == username } ?: return false

        account.edit {
            this.password = password
        }

        return true
    }

    fun changeUsername(username: String, newUsername: String): Boolean {
        val account = accounts.firstOrNull { it.username == username } ?: return false

        account.edit {
            this.username = newUsername
        }

        return true
    }

    fun isLoggedIn(player: Player): Boolean {
        return players.contains(player)
    }

    fun isLoggedIn(user: String): Boolean {
        return players.any { it.value.username == user }
    }
}
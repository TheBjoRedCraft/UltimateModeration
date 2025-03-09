package dev.thebjoredcraft.ultimatemoderation.auth

import it.unimi.dsi.fastutil.objects.ObjectArraySet
import org.bukkit.entity.Player

object AuthController {
    private val players = ObjectArraySet<Player>()
    val accounts = ObjectArraySet<AuthAccount>()

    fun authenticate(player: Player, username: String, password: String): Boolean {
        if(!accounts.any { it.username == username }) {
            return false
        }

        val account = accounts.first { it.username == username }

        if(account.password != password) {
            return false
        }

        this.login(player)
        return true
    }

    private fun login(player: Player) {
        players.add(player)
    }

    fun logout(player: Player): Boolean {
        return players.remove(player)
    }

    fun createAccount(username: String, password: String): Boolean {
        if(accounts.any { it.username == username }) {
            return false
        }

        accounts.add(AuthAccount(username, password))
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
}
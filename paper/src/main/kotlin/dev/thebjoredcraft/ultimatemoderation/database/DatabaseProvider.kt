package dev.thebjoredcraft.ultimatemoderation.database

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import dev.thebjoredcraft.ultimatemoderation.UltimateModerationPaper
import dev.thebjoredcraft.ultimatemoderation.config.ConfigProvider
import dev.thebjoredcraft.ultimatemoderation.player.UMPlayer
import dev.thebjoredcraft.ultimatemoderation.plugin
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.UUID

class DatabaseProvider {
    private lateinit var dataSource: HikariDataSource

    fun connect() {
        dataSource = HikariDataSource(HikariConfig().apply {
            when (ConfigProvider.storageMethod) {
                "database" -> {
                    jdbcUrl = "jdbc:mysql://${ConfigProvider.mysqlHost}:${ConfigProvider.mysqlPort}/${ConfigProvider.mysqlDatabase}"
                    username = ConfigProvider.mysqlUsername
                    password = ConfigProvider.mysqlPassword
                }

                "local" -> {
                    jdbcUrl = "jdbc:sqlite:${plugin.dataFolder}/storage.db"
                }

                else -> UltimateModerationPaper.disable("Invalid storage method.")
            }

            maximumPoolSize = 10
            minimumIdle = 2
            idleTimeout = 30000
            connectionTimeout = 30000
            maxLifetime = 1800000
        })

        dataSource.connection.use { connection ->
            connection.createStatement().use { statement ->
                statement.executeUpdate("""
                    CREATE TABLE IF NOT EXISTS users (
                        uuid VARCHAR(36) PRIMARY KEY,
                        name VARCHAR(255) NOT NULL,
                        real BOOLEAN NOT NULL,
                        isMuted BOOLEAN NOT NULL,
                        isBanned BOOLEAN NOT NULL,
                        isFrozen BOOLEAN NOT NULL,
                        isVanished BOOLEAN NOT NULL,
                        isInvincible BOOLEAN NOT NULL,
                        inSpectateMode BOOLEAN NOT NULL,
                        isLoggedIn BOOLEAN NOT NULL,
                        password VARCHAR(255) NOT NULL,
                        latestAddress VARCHAR(255) NOT NULL
                    )
                """.trimIndent())
            }
        }
    }


    suspend fun savePlayer(player: UMPlayer) {
        withContext(Dispatchers.IO) {
            dataSource.connection.use { connection ->
                connection.prepareStatement("""
                INSERT INTO users (uuid, name, real, isMuted, isBanned, isFrozen, isVanished, isInvincible, inSpectateMode, isLoggedIn, password, latestAddress)
                VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
                ON DUPLICATE KEY UPDATE
                name = VALUES(name),
                real = VALUES(real),
                isMuted = VALUES(isMuted),
                isBanned = VALUES(isBanned),
                isFrozen = VALUES(isFrozen),
                isVanished = VALUES(isVanished),
                isInvincible = VALUES(isInvincible),
                inSpectateMode = VALUES(inSpectateMode),
                isLoggedIn = VALUES(isLoggedIn),
                password = VALUES(password),
                latestAddress = VALUES(latestAddress)
            """.trimIndent()).use { statement ->
                    statement.setString(1, player.uuid.toString())
                    statement.setString(2, player.name)
                    statement.setBoolean(3, player.real)
                    statement.setBoolean(4, player.isMuted)
                    statement.setBoolean(5, player.isBanned)
                    statement.setBoolean(6, player.isFrozen)
                    statement.setBoolean(7, player.isVanished)
                    statement.setBoolean(8, player.isInvincible)
                    statement.setBoolean(9, player.inSpectateMode)
                    statement.setBoolean(10, player.isLoggedIn)
                    statement.setString(11, player.password)
                    statement.setString(12, player.latestAddress)

                    statement.executeUpdate()
                }
            }
        }
    }

    suspend fun loadPlayer(uuid: UUID): UMPlayer {
        withContext(Dispatchers.IO) {
            dataSource.connection.use { connection ->
                connection.prepareStatement("SELECT * FROM users WHERE uuid = ?").use { statement ->
                    statement.setString(1, uuid.toString())

                    statement.executeQuery().use { resultSet ->
                        if (!resultSet.next()) {
                            return@withContext null
                        }

                        return@withContext UMPlayer(
                            uuid = UUID.fromString(resultSet.getString("uuid")),
                            name = resultSet.getString("name"),
                            real = resultSet.getBoolean("real"),
                            isMuted = resultSet.getBoolean("isMuted"),
                            isBanned = resultSet.getBoolean("isBanned"),
                            isFrozen = resultSet.getBoolean("isFrozen"),
                            isVanished = resultSet.getBoolean("isVanished"),
                            isInvincible = resultSet.getBoolean("isInvincible"),
                            inSpectateMode = resultSet.getBoolean("inSpectateMode"),
                            isLoggedIn = resultSet.getBoolean("isLoggedIn"),
                            password = resultSet.getString("password"),
                            latestAddress = resultSet.getString("latestAddress")
                        )
                    }
                }
            }
        }

        return this.createFakePlayer()
    }

    suspend fun loadPlayerFromName(name: String): UMPlayer {
        withContext(Dispatchers.IO) {
            dataSource.connection.use { connection ->
                connection.prepareStatement("SELECT * FROM users WHERE name = ?").use { statement ->
                    statement.setString(1, name)

                    statement.executeQuery().use { resultSet ->
                        if (!resultSet.next()) {
                            return@withContext null
                        }

                        return@withContext UMPlayer(
                            uuid = UUID.fromString(resultSet.getString("uuid")),
                            name = resultSet.getString("name"),
                            real = resultSet.getBoolean("real"),
                            isMuted = resultSet.getBoolean("isMuted"),
                            isBanned = resultSet.getBoolean("isBanned"),
                            isFrozen = resultSet.getBoolean("isFrozen"),
                            isVanished = resultSet.getBoolean("isVanished"),
                            isInvincible = resultSet.getBoolean("isInvincible"),
                            inSpectateMode = resultSet.getBoolean("inSpectateMode"),
                            isLoggedIn = resultSet.getBoolean("isLoggedIn"),
                            password = resultSet.getString("password"),
                            latestAddress = resultSet.getString("latestAddress")
                        )
                    }
                }
            }
        }

        return this.createFakePlayer()
    }

    private fun createFakePlayer(): UMPlayer {
        return UMPlayer(UUID.randomUUID(), real = false)
    }

    fun isFake(player: UMPlayer): Boolean {
        return !player.real
    }

    fun disconnect() {
        val dataSource = this.dataSource

        if (!dataSource.isClosed) {
            dataSource.close()
        }
    }
}
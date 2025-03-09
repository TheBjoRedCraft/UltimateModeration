package dev.thebjoredcraft.ultimatemoderation.database

import dev.thebjoredcraft.ultimatemoderation.auth.AuthAccount
import dev.thebjoredcraft.ultimatemoderation.auth.AuthController
import dev.thebjoredcraft.ultimatemoderation.player.UMPlayer
import dev.thebjoredcraft.ultimatemoderation.plugin
import dev.thebjoredcraft.ultimatemoderation.util.MessageBuilder
import dev.thebjoredcraft.ultimatemoderation.util.SerializationUtil
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import net.kyori.adventure.text.logger.slf4j.ComponentLogger
import org.bukkit.GameMode
import org.bukkit.Location
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import java.io.File
import java.util.*
import kotlin.io.path.*

object DatabaseProvider {
    private val config = plugin.config
    private val logger = ComponentLogger.logger(this.javaClass)

    object Users : Table() {
        val uuid = text("uuid").transform({ UUID.fromString(it) }, { it.toString() })
        val name = char("name", 16)

        val isMuted = bool("isMuted")
        val isBanned = bool("isBanned")
        val isFrozen = bool("isFrozen")
        val isVanished = bool("isVanished")
        val isInvincible = bool("isInvincible")
        val inSpectateMode = bool("inSpectateMode")

        val latestHost = text("latestHost")
        val latestClient = text("latestClient")
        val latestAddress = char("latestAddress", 64)
        val latestGeoLocation = text("latestGeoLocation")

        val latestLocation = text("latestLocation")
        val respawnLocation = text("respawnLocation")
        val bedLocation = text("bedLocation")

        val allowFlight = bool("allowFlight")
        val isFlying = bool("isFlying")
        val flySpeed = double("flySpeed")

        val walkSpeed = double("walkSpeed")

        val lastLogin = long("lastLogin")
        val lastLogout = long("lastLogout")
        val lastJoin = long("lastJoin")

        val latestViewDistance = integer("latestViewDistance")
        val simulationDistance = integer("simulationDistance")

        val latestCompassTarget = text("latestCompassTarget")
        val latestHitCooldown = long("latestHitCooldown")

        val latestLocale = char("latestLocale", 5)
        val latestPing = integer("latestPing")
        val latestTimeStamp = long("latestTimeStamp")
        val latestTimeZoneOffset = long("latestTimeZoneOffset")

        val health = double("health")
        val healthScale = double("healthScale")
        val foodLevel = integer("foodLevel")
        val totalExperience = integer("totalExperience")


        val gameMode = text("gameMode")
        val previousGamemode = text("previousGamemode")

        val spectatorTarget = text("spectatorTarget")

        val isOnGround = bool("isOnGround")
        val isSneaking = bool("isSneaking")
        val isSprinting = bool("isSprinting")

        val inventory = text("inventory")
        val enderChest = text("enderChest")

        override val primaryKey = PrimaryKey(uuid)
    }

    object Punishments : Table() {
        val id = integer("id").autoIncrement()
        val uuid = char("uuid", 36).transform({ UUID.fromString(it) }, { it.toString() })
        val type = text("type")
        val reason = text("reason")
        val expiration = long("expiration")
        val creation = long("creation")
        val staff = text("staff")

        val player = char("player", 36).transform({ UUID.fromString(it) }, { it.toString() })

        override val primaryKey = PrimaryKey(id)
    }

    object Accounts : Table() {
        val username = text("username")
        val password = text("password")
        val blocked = bool("blocked")

        override val primaryKey = PrimaryKey(username)
    }

    suspend fun connect() {
        val method = config.getString("storage-method") ?: "local"

        when (method.lowercase()) {
            "local" -> {
                Class.forName("org.sqlite.JDBC")
                val dbfile = File("${plugin.dataPath}/storage.db")

                if (!dbfile.exists()) {
                    dbfile.parentFile.mkdirs()

                    withContext(Dispatchers.IO) {
                        dbfile.createNewFile()
                    }
                }

                Database.connect("jdbc:sqlite:file:${dbfile.absolutePath}", "org.sqlite.JDBC")
                logger.info(MessageBuilder().withPrefix().success("Successfully connected to database with sqlite!").build())
            }

            "external" -> {
                Class.forName("com.mysql.cj.jdbc.Driver")
                Database.connect(
                    url = "jdbc:mysql://${config.getString("database.hostname")}:${config.getInt("database.port")}/${
                        config.getString(
                            "database.database"
                        )
                    }",
                    driver = "com.mysql.cj.jdbc.Driver",
                    user = config.getString("database.username") ?: return,
                    password = config.getString("database.password") ?: return
                )

                logger.info(MessageBuilder().withPrefix().success("Successfully connected to database with mysql!").build())
            }

            else -> {
                Class.forName("org.sqlite.JDBC")
                val dbfile = File("${plugin.dataPath}/storage.db")

                if (!dbfile.exists()) {
                    dbfile.parentFile.mkdirs()

                    withContext(Dispatchers.IO) {
                        dbfile.createNewFile()
                    }
                }

                Database.connect("jdbc:sqlite:file:${dbfile.absolutePath}", "org.sqlite.JDBC")
                logger.info(MessageBuilder().withPrefix().success("Successfully connected to database with sqlite!").build())
            }
        }

        newSuspendedTransaction {
            SchemaUtils.create(
                Users, Punishments, Accounts
            )
        }
    }

    suspend fun loadPlayer(uuid: UUID): UMPlayer {
        return withContext(Dispatchers.IO) {
            newSuspendedTransaction {
                val user = Users.selectAll().where { Users.uuid eq uuid }.firstOrNull() ?: return@newSuspendedTransaction emptyPlayer(uuid)

                return@newSuspendedTransaction user.let { UMPlayer(
                    uuid = uuid,
                    name = it[Users.name],
                    isMuted = it[Users.isMuted],
                    isBanned = it[Users.isBanned],
                    isFrozen = it[Users.isFrozen],
                    isVanished = it[Users.isVanished],
                    isInvincible = it[Users.isInvincible],
                    inSpectateMode = it[Users.inSpectateMode],
                    latestHost = it[Users.latestHost],
                    latestClient = it[Users.latestClient],
                    latestAddress = it[Users.latestAddress],
                    latestGeoLocation = it[Users.latestGeoLocation],
                    latestLocation = SerializationUtil.fromString<Location>(it[Users.latestLocation]),
                    respawnLocation = SerializationUtil.fromString<Location>(it[Users.respawnLocation]),
                    bedLocation = SerializationUtil.fromString<Location>(it[Users.bedLocation]),
                    allowFlight = it[Users.allowFlight],
                    isFlying = it[Users.isFlying],
                    flySpeed = it[Users.flySpeed],
                    walkSpeed = it[Users.walkSpeed],
                    lastLogin = it[Users.lastLogin],
                    lastLogout = it[Users.lastLogout],
                    lastJoin = it[Users.lastJoin],
                    latestViewDistance = it[Users.latestViewDistance],
                    simulationDistance = it[Users.simulationDistance],
                    latestCompassTarget = it[Users.latestCompassTarget],
                    latestHitCooldown = it[Users.latestHitCooldown],
                    latestLocale = it[Users.latestLocale],
                    latestPing = it[Users.latestPing],
                    latestTimeStamp = it[Users.latestTimeStamp],
                    latestTimeZoneOffset = it[Users.latestTimeZoneOffset],
                    health = it[Users.health],
                    healthScale = it[Users.healthScale],
                    foodLevel = it[Users.foodLevel],
                    totalExperience = it[Users.totalExperience],
                    gameMode = SerializationUtil.fromString<GameMode>(it[Users.gameMode]),
                    previousGamemode = SerializationUtil.fromString<GameMode>(it[Users.previousGamemode]),
                    spectatorTarget = it[Users.spectatorTarget],
                    inventory = SerializationUtil.fromString(it[Users.inventory]),
                    enderChest = SerializationUtil.fromString(it[Users.enderChest])
                ) }
            }
        }
    }

    suspend fun savePlayer(player: UMPlayer) {
        newSuspendedTransaction {
            Users.replace {
                it[uuid] = player.uuid
                it[name] = player.name
                it[isMuted] = player.isMuted
                it[isBanned] = player.isBanned
                it[isFrozen] = player.isFrozen
                it[isVanished] = player.isVanished
                it[isInvincible] = player.isInvincible
                it[inSpectateMode] = player.inSpectateMode
                it[latestHost] = player.latestHost
                it[latestClient] = player.latestClient
                it[latestAddress] = player.latestAddress
                it[latestGeoLocation] = player.latestGeoLocation
                it[latestLocation] = SerializationUtil.toString(player.latestLocation)
                it[respawnLocation] = SerializationUtil.toString(player.respawnLocation)
                it[bedLocation] = SerializationUtil.toString(player.bedLocation)
                it[allowFlight] = player.allowFlight
                it[isFlying] = player.isFlying
                it[flySpeed] = player.flySpeed
                it[walkSpeed] = player.walkSpeed
                it[lastLogin] = player.lastLogin
                it[lastLogout] = player.lastLogout
                it[lastJoin] = player.lastJoin
                it[latestViewDistance] = player.latestViewDistance
                it[simulationDistance] = player.simulationDistance
                it[latestCompassTarget] = player.latestCompassTarget
                it[latestHitCooldown] = player.latestHitCooldown
                it[latestLocale] = player.latestLocale
                it[latestPing] = player.latestPing
                it[latestTimeStamp] = player.latestTimeStamp
                it[latestTimeZoneOffset] = player.latestTimeZoneOffset
                it[health] = player.health
                it[healthScale] = player.healthScale
                it[foodLevel] = player.foodLevel
                it[totalExperience] = player.totalExperience
                it[gameMode] = SerializationUtil.toString(player.gameMode)
                it[previousGamemode] = SerializationUtil.toString(player.previousGamemode)
                it[spectatorTarget] = player.spectatorTarget
                it[inventory] = SerializationUtil.toString(player.inventory)
                it[enderChest] = SerializationUtil.toString(player.enderChest)
            }
        }
    }

    suspend fun loadAccounts() {
        newSuspendedTransaction {
            Accounts.selectAll().forEach {
                val username = it[Accounts.username]
                val password = it[Accounts.password]
                val blocked = it[Accounts.blocked]

                AuthController.accounts.add(AuthAccount(username, password, blocked))
            }
        }
    }

    suspend fun saveAccounts() {
        newSuspendedTransaction {
            Accounts.deleteAll()

            AuthController.accounts.forEach { account ->
                Accounts.insert {
                    it[username] = account.username
                    it[password] = account.password
                    it[blocked] = account.blocked
                }
            }
        }
    }

    fun emptyPlayer(uuid: UUID): UMPlayer {
        return UMPlayer(uuid)
    }
}
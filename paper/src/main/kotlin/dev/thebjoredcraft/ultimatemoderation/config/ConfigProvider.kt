package dev.thebjoredcraft.ultimatemoderation.config

import dev.thebjoredcraft.ultimatemoderation.plugin

object ConfigProvider {
    private val config = plugin.config

    var storageMethod: String = "sqlite"

    var mysqlHost: String = "localhost"
    var mysqlPort: Int = 3306
    var mysqlDatabase: String = "ultimatemoderation"
    var mysqlUsername: String = "root"
    var mysqlPassword: String = "password"

    fun load() {
        storageMethod = config.getString("storage-method") ?: "sqlite"

        mysqlHost = config.getString("mysql.host") ?: "localhost"
        mysqlPort = config.getInt("mysql.port") ?: 3306
        mysqlDatabase = config.getString("mysql.database") ?: "ultimatemoderation"
        mysqlUsername = config.getString("mysql.username") ?: "root"
        mysqlPassword = config.getString("mysql.password") ?: "password"
    }


}
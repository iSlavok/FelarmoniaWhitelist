package online.slavok.felarmoniaWhitelist

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import java.sql.Connection

class Database (
    private val mysqlUrl: String
) {
    private val dataSource = createHikariDataSource()

    private fun createHikariDataSource(): HikariDataSource {
        val config = HikariConfig()
        config.jdbcUrl = mysqlUrl
        config.maximumPoolSize = 10
        config.connectionTimeout = 5000
        return HikariDataSource(config)
    }

    private fun getConnection(): Connection {
        return dataSource.connection
    }

    init {
        getConnection().use { connection ->
            connection.prepareStatement("""
                create table if not exists whitelist
                (
                    discord_id bigint            not null
                        primary key,
                    nickname   text              not null,
                    isAccess   bool default true not null
                );
            """.trimIndent()).use { ps ->
                ps.execute()
            }
        }
    }
    fun inWhitelist(nickname: String): Boolean {
        getConnection().use { connection ->
            connection.prepareStatement("SELECT * FROM whitelist WHERE nickname = ? and isAccess = true;").use { ps ->
                ps.setString(1, nickname)
                ps.executeQuery().use { rs ->
                    return rs.next()
                }
            }
        }
    }
}
package online.slavok.felarmoniaWhitelist

import org.bukkit.plugin.java.JavaPlugin

class FelarmoniaWhitelist : JavaPlugin() {

    override fun onEnable() {
        this.saveDefaultConfig()
        val mySqlUrl = this.config.getString("mysql-url")!!
        val database = Database(mySqlUrl)
        server.pluginManager.registerEvents(PreLoginListener(database), this)
    }

    override fun onDisable() {
        // Plugin shutdown logic
    }
}

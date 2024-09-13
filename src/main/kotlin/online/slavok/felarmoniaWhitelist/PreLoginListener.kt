package online.slavok.felarmoniaWhitelist

import net.kyori.adventure.text.minimessage.MiniMessage
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.AsyncPlayerPreLoginEvent

class PreLoginListener (
    private val database: Database
) : Listener {
    @EventHandler
    fun onAsyncPlayerPreLogin(event: AsyncPlayerPreLoginEvent) {
        if (event.loginResult != AsyncPlayerPreLoginEvent.Result.ALLOWED) return
        if (!database.inWhitelist(event.name)) {
            event.disallow(
                AsyncPlayerPreLoginEvent.Result.KICK_WHITELIST,
                MiniMessage.miniMessage().deserialize(
                    "<red>У вас нет доступа к серверу выживания.<newline>" +
                     "<gray>Подать заявку на вход можно на нашем дискорд сервере: <blue>https://discord.gg/7mugTSbtr2"
                )
            )
        }
    }
}
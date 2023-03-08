package dev.plex

import dev.plex.commands.ShopCommand
import dev.plex.config.ModuleConfig
import dev.plex.listener.ShopListener
import dev.plex.module.PlexModule
import dev.plex.shop.ShopMenu
import dev.plex.shop.item.impl.FireballItem
import dev.plex.util.PlexLog
import dev.plex.util.item.ItemBuilder
import dev.plex.util.minimessage.SafeMiniMessage
import org.bukkit.Material

class ShopModule : PlexModule() {
    companion object {
        private var module: ShopModule? = null;
        fun get(): ShopModule
        {
            return module!!
        }
    }

    private var config: ModuleConfig? = null

    override fun load()
    {
        module = this;
        config = ModuleConfig(this, "shop/config.yml", "config.yml")
        getConfig().load()
    }

    override fun enable() {
        ShopMenu.registerItem(22, FireballItem(
                ItemBuilder(Material.FIRE_CHARGE).displayName(SafeMiniMessage.mmDeserialize("<!italic><red>Fireball")).build(),
                getConfig().getDouble("shop.prices.fireball", 0.0)
        ))

        registerListener(ShopListener())
        registerCommand(ShopCommand())
        PlexLog.debug("Shop loaded!")
    }

    fun getConfig(): ModuleConfig
    {
        return config!!
    }

}
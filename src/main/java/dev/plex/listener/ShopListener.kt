package dev.plex.listener

import dev.plex.cache.DataUtils
import dev.plex.shop.ShopMenu
import dev.plex.util.minimessage.SafeMiniMessage
import net.kyori.adventure.text.Component
import org.bukkit.event.EventHandler
import org.bukkit.event.inventory.InventoryClickEvent

class ShopListener : PlexListener()
{
    private val shopMenuTitle: Component = SafeMiniMessage.mmDeserialize("<gold>Shop")

    @EventHandler
    fun onClick(event: InventoryClickEvent)
    {
        if (!event.view.title().equals(shopMenuTitle))
        {
            return
        }
        event.isCancelled = true
        if (!ShopMenu.ITEMS.containsKey(event.slot))
        {
            return
        }
        ShopMenu.ITEMS[event.slot]!!.purchase(DataUtils.getPlayer(event.whoClicked.uniqueId))
    }

}
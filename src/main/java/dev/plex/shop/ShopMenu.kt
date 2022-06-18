package dev.plex.shop

import dev.plex.player.PlexPlayer
import dev.plex.shop.item.AbstractItem
import dev.plex.util.minimessage.SafeMiniMessage
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.inventory.Inventory

/**
 * @author Taah
 * @project plex-shop
 * @since 11:21 PM [10-06-2022]
 *
 */
class ShopMenu {
    companion object {
        val ITEMS = mapOf<Int, AbstractItem>()
    }

    fun open(plexPlayer: PlexPlayer)
    {
        val player: Player? = plexPlayer.player
        val inventory: Inventory = constructInventory()
        player?.openInventory(inventory)
    }

    private fun constructInventory(): Inventory
    {
        val inventory: Inventory = Bukkit.createInventory(null, 54, SafeMiniMessage.mmDeserialize("<gold>Shop"))
        ITEMS.forEach { (t, u) ->  inventory.setItem(t, u.item) }
        return inventory
    }
}
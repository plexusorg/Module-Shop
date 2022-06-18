package dev.plex.shop

import com.google.common.collect.Maps
import dev.plex.player.PlexPlayer
import dev.plex.shop.item.AbstractItem
import dev.plex.util.item.ItemBuilder
import dev.plex.util.minimessage.SafeMiniMessage
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.inventory.Inventory

/**
 * @author Taah
 * @project plex-shop
 * @since 11:21 PM [10-06-2022]
 *
 */
class ShopMenu
{
    companion object
    {
        val ITEMS: HashMap<Int, AbstractItem> = Maps.newHashMap()
        fun registerItem(index: Int, item: AbstractItem)
        {
            ITEMS.put(index, item);
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
            ITEMS.forEach { (t, u) -> inventory.setItem(t, u.item) }
            for (i in 0 until inventory.size)
            {
                if (inventory.getItem(i) == null || inventory.getItem(i)?.type == Material.AIR)
                {
                    inventory.setItem(i, ItemBuilder(Material.GRAY_STAINED_GLASS_PANE).displayName(SafeMiniMessage.mmDeserialize("<!italic> ")).build())
                }
            }
            return inventory
        }
    }
}
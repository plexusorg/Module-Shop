package dev.plex.shop

import com.google.common.collect.Lists
import com.google.common.collect.Maps
import dev.plex.Plex
import dev.plex.ShopModule
import dev.plex.player.PlexPlayer
import dev.plex.shop.item.AbstractItem
import dev.plex.util.item.ItemBuilder
import dev.plex.util.minimessage.SafeMiniMessage
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.minimessage.MiniMessage
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.NamespacedKey
import org.bukkit.entity.Player
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.meta.ItemMeta
import org.bukkit.persistence.PersistentDataContainer
import org.bukkit.persistence.PersistentDataType

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
            val meta: ItemMeta = item.item.itemMeta
            val dataContainer: PersistentDataContainer = meta.persistentDataContainer
            dataContainer.set(item.itemTag, PersistentDataType.STRING, item.itemTag.key)
            val lore = meta.lore() ?: Lists.newArrayList()
            lore.add(Component.empty())
            lore.add(MiniMessage.miniMessage().deserialize("<!italic><gold>Price: ${item.cost}"))
            meta.lore(lore)
            item.item.itemMeta = meta
            ITEMS[index] = item;
            ShopModule.get().registerListener(item)
        }

        fun open(plexPlayer: PlexPlayer)
        {
            val player: Player? = plexPlayer.player
            val inventory: Inventory = constructInventory(plexPlayer)
            player?.openInventory(inventory)
        }

        private fun constructInventory(plexPlayer: PlexPlayer): Inventory
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
            inventory.setItem(53, ItemBuilder(Material.SUNFLOWER).displayName(SafeMiniMessage.mmDeserialize("<!italic>${plexPlayer.coins} Coins")).build())
            return inventory
        }
    }
}
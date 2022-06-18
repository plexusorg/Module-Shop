package dev.plex.shop.item

import dev.plex.cache.DataUtils
import dev.plex.listener.PlexListener
import dev.plex.player.PlexPlayer
import dev.plex.util.PlexLog
import dev.plex.util.minimessage.SafeMiniMessage
import org.bukkit.Material
import org.bukkit.NamespacedKey
import org.bukkit.event.EventHandler
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.inventory.EquipmentSlot
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.ItemMeta
import org.bukkit.persistence.PersistentDataContainer
import java.util.concurrent.CompletableFuture

abstract class AbstractItem(val item: ItemStack, val cost: Double, val itemTag: NamespacedKey) : PlexListener()
{
    fun purchase(player: PlexPlayer)
    {
        if (player.coins < this.cost) {
            player.player?.sendMessage(SafeMiniMessage.mmDeserialize("<red>You need ${this.cost - player.coins} more coins to purchase this!"))
            return
        }
        if (player.player?.inventory!!.filter { it?.type != Material.AIR }.size == 53)
        {
            player.player?.sendMessage(SafeMiniMessage.mmDeserialize("<red>Your inventory is currently full!"))
            return
        }
        player.coins.minus(this.cost);
        player.player?.inventory?.addItem(this.item);
        CompletableFuture.runAsync {
            DataUtils.update(player);
        }
    }

    abstract fun interact(event: PlayerInteractEvent)

    @EventHandler
    fun onInteract(event: PlayerInteractEvent)
    {
        if (event.hand != EquipmentSlot.HAND)
        {
            return
        }

        if (event.item == null)
        {
            return
        }

        if (!event.item!!.hasItemMeta())
        {
            return
        }

        val meta: ItemMeta = event.item!!.itemMeta
        val dataContainer: PersistentDataContainer = meta.persistentDataContainer
        if (!dataContainer.has(itemTag))
        {
            return
        }

        interact(event)
    }
}

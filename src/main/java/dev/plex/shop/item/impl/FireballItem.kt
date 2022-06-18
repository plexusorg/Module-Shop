package dev.plex.shop.item.impl

import dev.plex.Plex
import dev.plex.shop.item.AbstractItem
import org.bukkit.NamespacedKey
import org.bukkit.entity.Fireball
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.inventory.ItemStack

class FireballItem(item: ItemStack, cost: Double) : AbstractItem(item, cost, ITEM_TAG)
{
    companion object {
        val ITEM_TAG: NamespacedKey = NamespacedKey(Plex.get(), "fireball_item")
    }

    override fun interact(event: PlayerInteractEvent)
    {
        event.player.launchProjectile(Fireball::class.java)
    }
}
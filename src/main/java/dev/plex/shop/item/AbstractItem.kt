package dev.plex.shop.item

import dev.plex.cache.DataUtils
import dev.plex.player.PlexPlayer
import org.bukkit.inventory.ItemStack
import java.util.concurrent.CompletableFuture

abstract class AbstractItem(val item: ItemStack, private val cost: Double)
{
    fun purchase(player: PlexPlayer)
    {
        player.coins.minus(this.cost);
        player.player?.inventory?.addItem(this.item);
        CompletableFuture.runAsync {
            DataUtils.update(player);
        }
    }
}

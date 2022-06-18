package dev.plex.commands

import dev.plex.cache.DataUtils
import dev.plex.command.PlexCommand
import dev.plex.command.annotation.CommandParameters
import dev.plex.command.annotation.CommandPermissions
import dev.plex.command.source.RequiredCommandSource
import dev.plex.rank.enums.Rank
import dev.plex.shop.ShopMenu
import net.kyori.adventure.text.Component
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

@CommandParameters(name = "shop", description = "Opens the shop menu")
@CommandPermissions(level = Rank.OP, source = RequiredCommandSource.IN_GAME, permission = "plex.shop.open")
class ShopCommand: PlexCommand()
{
    override fun execute(sender: CommandSender, player: Player?, args: Array<out String>?): Component?
    {
        ShopMenu.open(DataUtils.getPlayer(player!!.uniqueId))
        return null
    }
}
package dev.nours.atlantisgenerators.commands;

import dev.nours.atlantisgenerators.AtlantisGeneratorsPlugin;
import dev.nours.atlantisgenerators.items.ItemData;
import org.bukkit.entity.Player;
import revxrsal.commands.annotation.Command;

public class GiveItemCmd {
    @Command("atgen admin give")
    public void give(Player target, ItemData atItem) {
        AtlantisGeneratorsPlugin.getInstance().getLogger().info("give " + atItem.getId());
    }

}

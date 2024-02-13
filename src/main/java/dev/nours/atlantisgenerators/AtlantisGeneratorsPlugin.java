package dev.nours.atlantisgenerators;

import dev.nours.atlantisgenerators.managers.ConfigManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

public final class AtlantisGeneratorsPlugin extends JavaPlugin implements Listener {
    private ConfigManager configManager;
    @Override
    public void onEnable() {
        configManager = new ConfigManager(this);
        this.getServer().getPluginManager().registerEvents(this, this);
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        String itemName = configManager.getItemsConfig().getItemName();
        String itemLore = configManager.getItemsConfig().getItemLore();
        e.getPlayer().sendMessage(itemName + "  dfdsfg");
        e.getPlayer().sendMessage(itemLore);
    }
}

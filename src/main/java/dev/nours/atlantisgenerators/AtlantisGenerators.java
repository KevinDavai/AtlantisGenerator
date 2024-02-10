package dev.nours.atlantisgenerators;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

public final class AtlantisGenerators extends JavaPlugin implements Listener {
    @Override
    public void onEnable() {
        this.getLogger().info("Loading dev.nours.generator.AtlantisGenerator 1.0.0");
        this.getServer().getPluginManager().registerEvents(this, this);
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        String message = "KIIROO LE FDP";
        p.sendMessage(message);
    }
}

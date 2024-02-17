package dev.nours.atlantisgenerators;

import dev.nours.atlantisgenerators.managers.ConfigManager;
import dev.nours.atlantisgenerators.nms.NMSAdapter;
import dev.nours.atlantisgenerators.nms.NMSHandler;
import net.minecraft.world.item.AirItem;
import net.minecraft.world.level.block.Blocks;
import org.bukkit.block.Block;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.ItemFrame;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.world.ChunkLoadEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

public final class AtlantisGeneratorsPlugin extends JavaPlugin implements Listener {
    private ConfigManager configManager;

    @Override
    public void onEnable() {
        if (!NMSAdapter.isValidVersion()) {
            this.getLogger().severe(String.format("AtlantisGenerators only supports %s through %s. The plugin has been disabled.", "1.16.5", "1.20.4"));
            Bukkit.getPluginManager().disablePlugin(this);
            return;
        }

        Bukkit.getPluginManager().registerEvents(this, this);

        configManager = new ConfigManager(this);
    }

    @EventHandler
    public void onChunkLoad(ChunkLoadEvent chunk) {
        boolean f = NMSAdapter.getHandler().countBlockInChunk(chunk.getChunk());
    }

//    @EventHandler
//    public void paintingRotate(final PlayerInteractEvent event) {
//        for(Entity entity : event.getClickedBlock().getLocation().getNearbyEntities(1,1,1)) {
//            if(entity instanceof ItemFrame) {
//                this.getLogger().info("item: " + entity.toString());
//            }
//        }
//    }
}

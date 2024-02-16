package dev.nours.atlantisgenerators;

import dev.nours.atlantisgenerators.managers.ConfigManager;
import dev.nours.atlantisgenerators.nms.NMSAdapter;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class AtlantisGeneratorsPlugin extends JavaPlugin {
    private ConfigManager configManager;

    @Override
    public void onEnable() {
        if (!NMSAdapter.isValidVersion()) {
            this.getLogger().severe(String.format("AtlantisGenerators only supports %s through %s. The plugin has been disabled.", "1.16.5", "1.20.4"));
            Bukkit.getPluginManager().disablePlugin(this);
            return;
        }

        configManager = new ConfigManager(this);

        NMSAdapter.getHandler().isInChunk();
    }
}

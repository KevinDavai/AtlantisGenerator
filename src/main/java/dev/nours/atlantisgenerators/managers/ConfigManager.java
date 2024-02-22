package dev.nours.atlantisgenerators.managers;

import dev.nours.atlantisgenerators.AtlantisGeneratorsPlugin;
import dev.nours.atlantisgenerators.configurations.GeneratorsConfig;
import dev.nours.atlantisgenerators.configurations.ItemsConfig;
import dev.nours.atlantisgenerators.events.ChunkEvent;
import dev.nours.atlantisgenerators.items.ItemData;
import org.bukkit.Bukkit;
import org.bukkit.entity.Item;

public class ConfigManager {
    private final AtlantisGeneratorsPlugin plugin;
    private GeneratorsConfig generatorsConfig;
    private ItemsConfig itemsConfig;

    public ConfigManager(AtlantisGeneratorsPlugin plugin) {
        this.plugin = plugin;

        generatorsConfig = new GeneratorsConfig(plugin);
        itemsConfig = new ItemsConfig(plugin);
    }

    public void reload() {
        generatorsConfig.reloadFile();
        itemsConfig.reloadFile();
    }

    public GeneratorsConfig getGeneratorsConfig() {
        return generatorsConfig;
    }
    public ItemsConfig getItemsConfig() { return itemsConfig; }
}

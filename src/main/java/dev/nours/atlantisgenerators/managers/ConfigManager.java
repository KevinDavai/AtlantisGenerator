package dev.nours.atlantisgenerators.managers;

import dev.nours.atlantisgenerators.AtlantisGeneratorsPlugin;
import dev.nours.atlantisgenerators.configurations.ItemsConfig;

public class ConfigManager {
    private AtlantisGeneratorsPlugin plugin;
    private ItemsConfig itemsConfig;

    public ConfigManager(AtlantisGeneratorsPlugin plugin) {
        this.plugin = plugin;
        itemsConfig = new ItemsConfig(plugin);
    }

    public ItemsConfig getItemsConfig() {
        return itemsConfig;
    }
}

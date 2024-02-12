package dev.nours.atlantisgenerators.configurations;

import dev.nours.atlantisgenerators.AtlantisGeneratorsPlugin;

public class ItemsConfig extends ConfigBase {
    public ItemsConfig(AtlantisGeneratorsPlugin plugin) {
        super(plugin, "items.yml");
    }

    public String getItemName() {
        return config.getString("item-name", "test-default");
    }

    public String getItemLore() {
        return config.getString("item-lore", "lore-default");
    }
}

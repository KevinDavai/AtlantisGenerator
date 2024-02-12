package dev.nours.atlantisgenerators.configurations;

import dev.nours.atlantisgenerators.AtlantisGeneratorsPlugin;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public abstract class ConfigBase {
    protected AtlantisGeneratorsPlugin plugin;
    protected File configFile;
    protected FileConfiguration config;

    public ConfigBase(AtlantisGeneratorsPlugin plugin, String fileName) {
        this.plugin = plugin;
        this.configFile = new File(plugin.getDataFolder(), fileName);
        this.config = YamlConfiguration.loadConfiguration(configFile);

        if (!configFile.exists()) {
            configFile.getParentFile().mkdir();
            plugin.saveResource(fileName, false);
        } else {
            // Check for missing keys and add them with default values
            addMissingKeys();
        }
    }

    private void addMissingKeys() {
        ConfigurationSection defaultConfig = YamlConfiguration.loadConfiguration(new InputStreamReader(
                plugin.getResource(configFile.getName())));

        checkAndSetDefaults(config, defaultConfig);

        // Save the updated configuration to the file
        try {
            config.save(configFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void checkAndSetDefaults(ConfigurationSection config, ConfigurationSection defaults) {
        for (String key : defaults.getKeys(true)) {
            if (!config.contains(key)) {
                config.set(key, defaults.get(key));
            }
        }
    }

}

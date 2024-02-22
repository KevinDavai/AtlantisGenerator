package dev.nours.atlantisgenerators;

import dev.nours.atlantisgenerators.commands.GiveItemCmd;
import dev.nours.atlantisgenerators.commands.PrintGeneratorLoadedCmd;
import dev.nours.atlantisgenerators.commands.ReloadCmd;
import dev.nours.atlantisgenerators.configurations.ItemsConfig;
import dev.nours.atlantisgenerators.events.ChunkEvent;
import dev.nours.atlantisgenerators.events.PlayerEvent;
import dev.nours.atlantisgenerators.gui.GUIListener;
import dev.nours.atlantisgenerators.gui.GUIManager;
import dev.nours.atlantisgenerators.items.ItemData;
import dev.nours.atlantisgenerators.managers.ConfigManager;
import dev.nours.atlantisgenerators.managers.GeneratorManager;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import revxrsal.commands.autocomplete.SuggestionProvider;
import revxrsal.commands.bukkit.BukkitCommandHandler;

public final class AtlantisGeneratorsPlugin extends JavaPlugin {
    private static AtlantisGeneratorsPlugin instance;

    public static NamespacedKey GENERATOR_KEY;
    public static NamespacedKey ITEMIN_KEY;
    public static NamespacedKey LEVEL_KEY;
    public static NamespacedKey OWNER_KEY;

    private ConfigManager configManager;
    private GeneratorManager generatorManager;
    private GUIManager guiManager;

    private BukkitCommandHandler commandHandler;
    @Override
    public void onEnable() {
        instance = this;
        GENERATOR_KEY = new NamespacedKey(this, "generator");
        ITEMIN_KEY = new NamespacedKey(this, "itemIn");
        LEVEL_KEY = new NamespacedKey(this, "level");
        OWNER_KEY = new NamespacedKey(this, "owner");

        commandHandler = BukkitCommandHandler.create(this);

        // Register Manager
        guiManager = new GUIManager();
        configManager = new ConfigManager(this);
        generatorManager = new GeneratorManager(this);

        // Register events
        registerEvents();

        // Register commands
        registerCommands();

    }

    private void registerEvents() {
        PluginManager pluginManager = Bukkit.getPluginManager();

        pluginManager.registerEvents(new ChunkEvent(this), this);
        pluginManager.registerEvents(new PlayerEvent(this), this);
        pluginManager.registerEvents(new GUIListener(guiManager), this);
    }

    private void registerCommands() {
        commandHandler.register(
                new PrintGeneratorLoadedCmd(),
                new ReloadCmd(),
                new GiveItemCmd());



        commandHandler.registerBrigadier();
    }

    public static AtlantisGeneratorsPlugin getInstance() {
        return instance;
    }
    public ConfigManager getConfigManager() { return configManager; }
    public GeneratorManager getGeneratorManager() { return generatorManager; }
    public GUIManager getGuiManager() { return guiManager; }

    public BukkitCommandHandler getCommandHandler() {
        return commandHandler;
    }
}
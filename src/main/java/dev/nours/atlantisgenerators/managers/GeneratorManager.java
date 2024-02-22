package dev.nours.atlantisgenerators.managers;

import dev.lone.itemsadder.api.CustomFurniture;
import dev.nours.atlantisgenerators.AtlantisGeneratorsPlugin;
import dev.nours.atlantisgenerators.configurations.GeneratorsConfig;
import dev.nours.atlantisgenerators.generators.Generator;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class GeneratorManager {
    private final AtlantisGeneratorsPlugin plugin;
    private Map<Location, Generator> loadedGenerators;
    public GeneratorManager(AtlantisGeneratorsPlugin plugin) {
        this.plugin = plugin;
        this.loadedGenerators = new ConcurrentHashMap<>();
    }

    public void addLoadedGenerator(@NotNull CustomFurniture customFurniture, Player player) {
        loadedGenerators.put(customFurniture.getEntity().getLocation(), new Generator(customFurniture, player));
    }

    public void addLoadedGenerator(@NotNull CustomFurniture customFurniture) {
        loadedGenerators.put(customFurniture.getEntity().getLocation(), new Generator(customFurniture, null));
    }

    public void removeLoadedGenerator(CustomFurniture customFurniture) {
        loadedGenerators.remove(customFurniture.getEntity().getLocation());
    }

    public Generator getLoadedGeneratorByEntity(Entity entity) {
       return loadedGenerators.get(entity.getLocation());
    }

    public Generator getLoadedGeneratorByFurniture(CustomFurniture furniture) {
        return getLoadedGeneratorByEntity(furniture.getEntity());
    }

    public void reload() {
        this.loadedGenerators = new ConcurrentHashMap<>();
    }

    public void printLoadedGenerators() {
        plugin.getLogger().info("Loaded Generators:");

        for (Map.Entry<Location, Generator> entry : loadedGenerators.entrySet()) {
            Location location = entry.getKey();
            Generator generator = entry.getValue();

            plugin.getLogger().info("Location: " + location + ", Generator: " + generator);
        }
    }

    public Map<Location, Generator> getLoadedGenerators() {
        return this.loadedGenerators;
    }
}

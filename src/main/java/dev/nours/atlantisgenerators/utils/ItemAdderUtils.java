package dev.nours.atlantisgenerators.utils;

import dev.lone.itemsadder.api.CustomFurniture;
import org.bukkit.Chunk;
import org.bukkit.entity.Entity;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class ItemAdderUtils {
    public static List<CustomFurniture> getCustomFurniture(@NotNull Chunk chunk) {
        // Initialize a list to store the found CustomFurniture
        List<CustomFurniture> customFurnitures = new ArrayList<>();

        // Iterate through all entities in the chunk
        for (Entity entity : chunk.getEntities()) {
            CustomFurniture customFurniture = CustomFurniture.byAlreadySpawned(entity);

            if (customFurniture != null) {
                customFurnitures.add(customFurniture);
            }
        }

        return customFurnitures;
    }

    public static List<CustomFurniture> getCustomFurniture(@NotNull List<Entity> entities) {
        // Initialize a list to store the found CustomFurniture
        List<CustomFurniture> customFurnitures = new ArrayList<>();

        // Iterate through all entities in the chunk
        for (Entity entity : entities) {
            CustomFurniture customFurniture = CustomFurniture.byAlreadySpawned(entity);

            if (customFurniture != null) {
                customFurnitures.add(customFurniture);
            }
        }

        return customFurnitures;
    }
}

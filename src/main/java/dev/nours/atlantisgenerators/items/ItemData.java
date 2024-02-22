package dev.nours.atlantisgenerators.items;

import org.bukkit.Material;
import org.jetbrains.annotations.NotNull;

public class ItemData {
    private String id;
    private Material material;
    private int customModelData;

    public ItemData(@NotNull String id, @NotNull Material material, @NotNull int customModelData) {
        this.id = id;
        this.customModelData = customModelData;
        this.material = material;
    }

    public String getId() {
        return this.id;
    }

    public Material getMaterial() {
        return this.material;
    }

    public int getCustomModelData() {
        return this.customModelData;
    }
}

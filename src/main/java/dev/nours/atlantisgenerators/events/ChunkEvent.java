package dev.nours.atlantisgenerators.events;

import dev.lone.itemsadder.api.CustomFurniture;
import dev.nours.atlantisgenerators.AtlantisGeneratorsPlugin;
import dev.nours.atlantisgenerators.generators.Generator;
import dev.nours.atlantisgenerators.utils.GeneratorUtils;
import dev.nours.atlantisgenerators.utils.ItemAdderUtils;
import org.bukkit.Chunk;
import org.bukkit.entity.Entity;
import org.bukkit.entity.ItemFrame;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.ChunkLoadEvent;
import org.bukkit.event.world.EntitiesUnloadEvent;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class ChunkEvent implements Listener {

    private final AtlantisGeneratorsPlugin plugin;

    public ChunkEvent(AtlantisGeneratorsPlugin plugin) {
        this.plugin = plugin;
    }
    @EventHandler
    public void onChunkLoad(ChunkLoadEvent event) {
        Chunk chunk = event.getChunk();

        for(CustomFurniture customFurniture : ItemAdderUtils.getCustomFurniture(chunk)) {
            if(!GeneratorUtils.isGenerators(customFurniture)) continue;

            plugin.getGeneratorManager().addLoadedGenerator(customFurniture);
        }
    }

    @EventHandler
    public void onEntitiesUnload(EntitiesUnloadEvent event) {
        List<Entity> entities = event.getEntities();

        for(CustomFurniture customFurniture : ItemAdderUtils.getCustomFurniture(entities)) {
            if(!GeneratorUtils.isGenerators(customFurniture)) continue;

            plugin.getGeneratorManager().removeLoadedGenerator(customFurniture);
        }
    }
}

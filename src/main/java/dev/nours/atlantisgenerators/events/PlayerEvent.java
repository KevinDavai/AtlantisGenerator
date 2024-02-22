package dev.nours.atlantisgenerators.events;

import dev.lone.itemsadder.api.CustomFurniture;
import dev.lone.itemsadder.api.Events.FurnitureBreakEvent;
import dev.lone.itemsadder.api.Events.FurnitureInteractEvent;
import dev.lone.itemsadder.api.Events.FurniturePlaceSuccessEvent;
import dev.nours.atlantisgenerators.AtlantisGeneratorsPlugin;
import dev.nours.atlantisgenerators.generators.Generator;
import dev.nours.atlantisgenerators.utils.GeneratorUtils;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Panda;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class PlayerEvent implements Listener {
    private final AtlantisGeneratorsPlugin plugin;
    public PlayerEvent(AtlantisGeneratorsPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void generatorPlace(FurniturePlaceSuccessEvent event) {
        CustomFurniture furniture = event.getFurniture();

        if(!GeneratorUtils.isGenerators(furniture)) return;

        plugin.getGeneratorManager().addLoadedGenerator(furniture, event.getPlayer());
    }
    @EventHandler
    public void cancelGeneratorBreak(FurnitureBreakEvent event) {
        CustomFurniture furniture = event.getFurniture();

        if(!GeneratorUtils.isGenerators(furniture)) return;

        Generator generator = plugin.getGeneratorManager().getLoadedGeneratorByFurniture(furniture);

        if(generator == null) return;

        event.setCancelled(true);

        Player p = event.getPlayer();

        if(!event.getPlayer().isSneaking()) {
            p.playSound(p.getLocation(), Sound.ENTITY_VILLAGER_NO, 1, 1);
            return;
        }

        generator.pickUp(p);
    }
    @EventHandler
    public void generatorInteract(FurnitureInteractEvent event) {
        Generator clickedGenerator = plugin.getGeneratorManager().getLoadedGeneratorByEntity(event.getFurniture().getEntity());

        if(clickedGenerator == null) return;

        clickedGenerator.openGui(event.getPlayer());
    }

}

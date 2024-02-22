package dev.nours.atlantisgenerators.gui.impl;

import dev.nours.atlantisgenerators.AtlantisGeneratorsPlugin;
import dev.nours.atlantisgenerators.generators.Generator;
import dev.nours.atlantisgenerators.gui.InventoryHandler;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;


public class GeneratorGUI implements InventoryHandler {
    private Generator generator;
    public GeneratorGUI(Generator generator) {
        this.generator = generator;
    }

    @Override
    public void onClick(InventoryClickEvent event) {
        AtlantisGeneratorsPlugin.getInstance().getLogger().info("Slot clicked: " + event.getSlot());
    }

    @Override
    public void onOpen(InventoryOpenEvent event) {

    }

    @Override
    public void onClose(InventoryCloseEvent event) {
        AtlantisGeneratorsPlugin.getInstance().getLogger().info("CLOSE");
    }
}

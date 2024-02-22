package dev.nours.atlantisgenerators.generators;

import com.jeff_media.morepersistentdatatypes.DataType;
import dev.lone.itemsadder.api.CustomFurniture;
import dev.nours.atlantisgenerators.AtlantisGeneratorsPlugin;
import dev.nours.atlantisgenerators.gui.impl.GeneratorGUI;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataAdapterContext;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.logging.Logger;

public class Generator {
    private final CustomFurniture furniture;
    private final Entity entity;
    private Integer level;
    private String owner;
    private ItemStack itemUsed;


    public Generator(CustomFurniture furniture, Player playerWhoPlace) {
        this.furniture = furniture;
        this.entity = furniture.getEntity();

        PersistentDataContainer pdc = entity.getPersistentDataContainer();

        if(pdc.has(AtlantisGeneratorsPlugin.GENERATOR_KEY, PersistentDataType.TAG_CONTAINER)) {
            PersistentDataContainer root = pdc.get(AtlantisGeneratorsPlugin.GENERATOR_KEY, PersistentDataType.TAG_CONTAINER);

            owner = root.get(AtlantisGeneratorsPlugin.OWNER_KEY, PersistentDataType.STRING);
            level = root.get(AtlantisGeneratorsPlugin.LEVEL_KEY, PersistentDataType.INTEGER);
            itemUsed = root.get(AtlantisGeneratorsPlugin.ITEMIN_KEY, DataType.ITEM_STACK);

        } else {
            level = 0;
            owner = playerWhoPlace.getName();
            itemUsed = new ItemStack(Material.AIR);

            PersistentDataAdapterContext persistentDataAdapterContext = entity.getPersistentDataContainer().getAdapterContext();
            PersistentDataContainer root = persistentDataAdapterContext.newPersistentDataContainer();

            root.set(AtlantisGeneratorsPlugin.OWNER_KEY, PersistentDataType.STRING, owner);
            root.set(AtlantisGeneratorsPlugin.LEVEL_KEY, PersistentDataType.INTEGER, level);
            root.set(AtlantisGeneratorsPlugin.ITEMIN_KEY, DataType.ITEM_STACK, itemUsed);

            pdc.set(AtlantisGeneratorsPlugin.GENERATOR_KEY, PersistentDataType.TAG_CONTAINER, root);
        }

        printGeneratorInformation();
    }

    public void openGui(Player player) {
        GeneratorGUI gui = new GeneratorGUI(this);
        Inventory inventory = Bukkit.createInventory(null, 54, "Generator GUI");
        AtlantisGeneratorsPlugin.getInstance()
                .getGuiManager()
                .registerHandledInventory(inventory, gui);

        player.openInventory(inventory);
    }

    public void pickUp(Player player) {
        ItemStack itemAssociated = transfertPersistentData(furniture.getItemStack());

        player.playSound(player.getLocation(), Sound.BLOCK_ANVIL_DESTROY, 1, 1);

        entity.getWorld().getBlockAt(entity.getLocation()).setType(Material.AIR);
        entity.remove();
        furniture.remove(false);

        player.getInventory().addItem(itemAssociated);
    }

    private ItemStack transfertPersistentData(ItemStack item) {
        ItemMeta itemMeta = item.getItemMeta();

        PersistentDataContainer pdc = entity.getPersistentDataContainer();
        PersistentDataContainer root = pdc.get(AtlantisGeneratorsPlugin.GENERATOR_KEY, PersistentDataType.TAG_CONTAINER);

        itemMeta.getPersistentDataContainer().set(AtlantisGeneratorsPlugin.GENERATOR_KEY, PersistentDataType.TAG_CONTAINER, root);

        item.setItemMeta(itemMeta);

        return item;
    }

    private void printGeneratorInformation() {
        Logger logger = AtlantisGeneratorsPlugin.getInstance().getLogger();

        logger.info("Owner: " + this.owner);
        logger.info("ItemUsed: " + this.itemUsed);
        logger.info("Level: " + this.level);
    }

    public CustomFurniture getFurniture() {
        return this.furniture;
    }
    public Entity getEntity() {
        return this.entity;
    }
}

package dev.nours.atlantisgenerators.utils;

import dev.lone.itemsadder.api.CustomFurniture;
import dev.nours.atlantisgenerators.AtlantisGeneratorsPlugin;
import dev.nours.atlantisgenerators.generators.GeneratorData;
import org.bukkit.Material;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.ItemFrame;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Map;

public class GeneratorUtils {

    /**
     * Checks if the item in the given CustomFurniture corresponds to a GeneratorData.
     *
     * @param customFurniture The ItemFrame to check.
     * @return True if it's a generator, false if it's not a generator.
     */
    public static boolean isGenerators(CustomFurniture customFurniture) {
        Entity entity = customFurniture.getEntity();

        if (entity instanceof ItemFrame || entity instanceof ArmorStand) {
            ItemStack itemStackIn = (entity instanceof ItemFrame) ?
                    ((ItemFrame) entity).getItem() :
                    ((ArmorStand) entity).getEquipment().getHelmet();

            if (itemStackIn != null) {
                ItemMeta itemMetaIn = itemStackIn.getItemMeta();

                Material material = itemStackIn.getType();
                int customModelData = (itemMetaIn != null && itemMetaIn.hasCustomModelData()) ? itemMetaIn.getCustomModelData() : 0;

                Map<String, GeneratorData> generatorDatas = AtlantisGeneratorsPlugin.getInstance().getConfigManager().getGeneratorsConfig().getGeneratorsData();

                for (GeneratorData generatorData : generatorDatas.values()) {
                    if (generatorData.getMaterial() == material && generatorData.getCustomModelData() == customModelData) {
                        return true;
                    }
                }
            }
        }

        return false;
    }
}

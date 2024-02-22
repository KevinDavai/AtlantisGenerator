package dev.nours.atlantisgenerators.configurations;

import dev.dejvokep.boostedyaml.block.implementation.Section;
import dev.nours.atlantisgenerators.AtlantisGeneratorsPlugin;
import dev.nours.atlantisgenerators.generators.GeneratorData;
import dev.nours.atlantisgenerators.items.ItemData;
import org.bukkit.Material;
import org.bukkit.entity.Item;
import revxrsal.commands.autocomplete.SuggestionProvider;

import java.util.HashMap;
import java.util.Map;

public class ItemsConfig extends ConfigBase {
    private final static String IGNORED_ROUTE = "items";
    private Map<String, ItemData> itemsData;
    public ItemsConfig(AtlantisGeneratorsPlugin plugin) {
        super(plugin, "items.yml", IGNORED_ROUTE);

        this.itemsData = new HashMap<>();
        loadItemsDataFromConfig();
    }

    private void loadItemsDataFromConfig() {
        Section section = config.getSection("items");

        for(String key : section.getRoutesAsStrings(false)) {
            String materialStr = section.getString(key + ".material");
            int customModelData = section.getInt(key + ".custom-model-data");

            // Skip iteration if material is not provided
            if (materialStr == null) {
                AtlantisGeneratorsPlugin.getInstance().getLogger().warning("Failed to load " + key + " item. Material is not provided. Skipping.");
                continue;
            }

            // Skip iteration if customModelData is not provided
            if (customModelData == 0) {
                AtlantisGeneratorsPlugin.getInstance().getLogger().warning("Failed to load " + key + " item. Invalid or missing custom-model-data. Skipping.");
                continue;
            }

            // Validate material
            Material material;
            try {
                material = Material.valueOf(materialStr);
            } catch (IllegalArgumentException exception) {
                AtlantisGeneratorsPlugin.getInstance().getLogger().warning("Failed to load " + key + " item. The material value is not a valid material. Skipping.");
                continue;
            }


            ItemData itemData = new ItemData(key, material, customModelData);
            this.itemsData.put(key, itemData);

            plugin.getCommandHandler().getAutoCompleter().registerParameterSuggestions(ItemData.class, SuggestionProvider.of(itemsData::keySet));
            plugin.getCommandHandler().registerValueResolver(ItemData.class, (arguments) -> itemsData.get(arguments.pop()));
        }
    }

    public void printItems() {
        for (Map.Entry<String, ItemData> entry : itemsData.entrySet()) {
            String key = entry.getKey();
            ItemData itemData = entry.getValue();

            AtlantisGeneratorsPlugin.getInstance().getLogger().info("Key: " + key + ", Material: " + itemData.getMaterial() + ", CustomModelData: " + itemData.getCustomModelData());
        }
    }

    @Override
    public void reloadFile() {
        super.reloadFile();

        this.itemsData = new HashMap<>();
        loadItemsDataFromConfig();
    }

    public Map<String, ItemData> getGeneratorsData() {
        return this.itemsData;
    }
}

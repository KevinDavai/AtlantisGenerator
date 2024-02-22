package dev.nours.atlantisgenerators.configurations;

import dev.dejvokep.boostedyaml.block.implementation.Section;
import dev.nours.atlantisgenerators.AtlantisGeneratorsPlugin;
import dev.nours.atlantisgenerators.generators.GeneratorData;
import org.bukkit.Material;

import java.util.HashMap;
import java.util.Map;

public class GeneratorsConfig extends ConfigBase {
    private final static String IGNORED_ROUTE = "generators";
    private Map<String, GeneratorData> generatorsData;
    public GeneratorsConfig(AtlantisGeneratorsPlugin plugin) {
        super(plugin, "generators.yml", IGNORED_ROUTE);

        this.generatorsData = new HashMap<>();
        loadGeneratorsDataFromConfig();
    }

    private void loadGeneratorsDataFromConfig() {
        Section section = config.getSection("generators");

        for(String key : section.getRoutesAsStrings(false)) {
            String materialStr = section.getString(key + ".material");
            int customModelData = section.getInt(key + ".custom-model-data");

            // Skip iteration if material is not provided
            if (materialStr == null) {
                AtlantisGeneratorsPlugin.getInstance().getLogger().warning("Failed to load " + key + " generator. Material is not provided. Skipping.");
                continue;
            }

            // Skip iteration if customModelData is not provided
            if (customModelData == 0) {
                AtlantisGeneratorsPlugin.getInstance().getLogger().warning("Failed to load " + key + " generator. Invalid or missing custom-model-data. Skipping.");
                continue;
            }

            // Validate material
            Material material;
            try {
                material = Material.valueOf(materialStr);
            } catch (IllegalArgumentException exception) {
                AtlantisGeneratorsPlugin.getInstance().getLogger().warning("Failed to load " + key + " generator. The material value is not a valid material. Skipping.");
                continue;
            }


            GeneratorData genData = new GeneratorData(key, material, customModelData);
            this.generatorsData.put(key, genData);
        }
    }

    public void printGenerators() {
        for (Map.Entry<String, GeneratorData> entry : generatorsData.entrySet()) {
            String key = entry.getKey();
            GeneratorData generatorData = entry.getValue();

            AtlantisGeneratorsPlugin.getInstance().getLogger().info("Key: " + key + ", Material: " + generatorData.getMaterial() + ", CustomModelData: " + generatorData.getCustomModelData());
        }
    }

    @Override
    public void reloadFile() {
        super.reloadFile();

        this.generatorsData = new HashMap<>();
        loadGeneratorsDataFromConfig();
    }

    public Map<String, GeneratorData> getGeneratorsData() {
        return this.generatorsData;
    }

}

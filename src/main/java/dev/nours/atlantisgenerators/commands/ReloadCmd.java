package dev.nours.atlantisgenerators.commands;

import dev.nours.atlantisgenerators.AtlantisGeneratorsPlugin;
import dev.nours.atlantisgenerators.managers.ConfigManager;
import dev.nours.atlantisgenerators.managers.GeneratorManager;
import revxrsal.commands.annotation.Command;

public class ReloadCmd {
    @Command("atgen admin reload")
    public void reload() {
        AtlantisGeneratorsPlugin atlantisGeneratorsPlugin = AtlantisGeneratorsPlugin.getInstance();
        ConfigManager configManager = atlantisGeneratorsPlugin.getConfigManager();
        GeneratorManager generatorManager = atlantisGeneratorsPlugin.getGeneratorManager();

        configManager.reload();
        generatorManager.reload();
    }
}

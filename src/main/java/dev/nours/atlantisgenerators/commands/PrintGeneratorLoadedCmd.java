package dev.nours.atlantisgenerators.commands;

import dev.nours.atlantisgenerators.AtlantisGeneratorsPlugin;
import dev.nours.atlantisgenerators.managers.GeneratorManager;
import revxrsal.commands.annotation.Command;

public class PrintGeneratorLoadedCmd {
    @Command("atgen debug print")
    public void printGenerators() {
        GeneratorManager generatorManager = AtlantisGeneratorsPlugin.getInstance().getGeneratorManager();
        generatorManager.printLoadedGenerators();
    }
}

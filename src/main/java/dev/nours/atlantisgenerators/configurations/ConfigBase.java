package dev.nours.atlantisgenerators.configurations;

import dev.dejvokep.boostedyaml.YamlDocument;
import dev.dejvokep.boostedyaml.dvs.Version;
import dev.dejvokep.boostedyaml.dvs.versioning.BasicVersioning;
import dev.dejvokep.boostedyaml.settings.dumper.DumperSettings;
import dev.dejvokep.boostedyaml.settings.general.GeneralSettings;
import dev.dejvokep.boostedyaml.settings.loader.LoaderSettings;
import dev.dejvokep.boostedyaml.settings.updater.UpdaterSettings;
import dev.dejvokep.boostedyaml.spigot.SpigotSerializer;
import dev.dejvokep.boostedyaml.updater.operators.Merger;
import dev.nours.atlantisgenerators.AtlantisGeneratorsPlugin;

import java.io.*;
import java.util.*;

public class ConfigBase {

    protected AtlantisGeneratorsPlugin plugin;
    protected File configFile;
    protected YamlDocument config;

    private String defaultVersion;

    public ConfigBase(AtlantisGeneratorsPlugin plugin, String fileName) {
        this.plugin = plugin;
        this.configFile = new File(plugin.getDataFolder(), fileName);
        initializeConfig(fileName);
        updateConfigIfNeeded();
    }

    public void reloadFile() {
        try {
            config.reload();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void initializeConfig(String fileName) {
        try {
            config = YamlDocument.create(configFile, plugin.getResource(fileName),
                    GeneralSettings.builder().setSerializer(SpigotSerializer.getInstance()).setUseDefaults(true).build(),
                    LoaderSettings.builder().setAutoUpdate(false).build(),
                    DumperSettings.DEFAULT,
                    UpdaterSettings.builder()
                            .setVersioning(new BasicVersioning("file-version"))
                            .build());

            Version version = (Version) Objects.requireNonNull(config.getUpdaterSettings().getVersioning().getDocumentVersion(config.getDefaults(), true), "Version ID of the defaults cannot be null! Is it malformed or not specified?");
            this.defaultVersion = version.asID();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void updateConfigIfNeeded() {
        UpdaterSettings actual = config.getUpdaterSettings();
        Version documentVersion = actual.getVersioning().getDocumentVersion(config, false);

        config.setUpdaterSettings(UpdaterSettings.builder()
                .setVersioning(actual.getVersioning())
                .addIgnoredRoute(this.defaultVersion, "Object.items", '.')
                .build());

        if (!documentVersion.asID().equals(this.defaultVersion)) {
            performUpdate();
        } else {
            mergeAndSaveConfig();
        }
    }

    private void performUpdate() {
        try {
            config.update();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        plugin.getLogger().warning("Updating...");
    }

    private void mergeAndSaveConfig() {
        YamlDocument current = getCurrentYamlDocument();
        boolean missingKey = checkForMissingKeys(current);

        if (missingKey) {
            restoreMissingKeys();
        }
    }

    private YamlDocument getCurrentYamlDocument() {
        try {
            return YamlDocument.create(config.getFile());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private boolean checkForMissingKeys(YamlDocument current) {
        boolean missingKey = false;
        for (String key : config.getRoutesAsStrings(true)) {
            if (!current.contains(key)) {
                missingKey = true;
            }
        }
        return missingKey;
    }

    private void restoreMissingKeys() {
        plugin.getLogger().warning("Detect missing key, restoring them...");
        config.getUpdaterSettings().getIgnoredRoutes(this.defaultVersion, config.getGeneralSettings().getRouteSeparator()).forEach((route) -> {
            config.getOptionalBlock(route).ifPresent((block) -> {
                block.setIgnored(true);
            });
        });

        Merger.merge(config, config.getDefaults(), config.getUpdaterSettings());
        try {
            config.save();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

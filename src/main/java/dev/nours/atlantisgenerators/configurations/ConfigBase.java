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
import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.util.*;

public class ConfigBase {

    protected AtlantisGeneratorsPlugin plugin;
    protected File configFile;
    protected YamlDocument config;
    private String defaultVersion;
    private String ignoredRoute;

    public ConfigBase(AtlantisGeneratorsPlugin plugin, @NotNull String fileName, String ignoredRoute) {
        this.plugin = plugin;
        this.configFile = new File(plugin.getDataFolder(), fileName);
        this.ignoredRoute = ignoredRoute;

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
            config = YamlDocument.create(configFile, Objects.requireNonNull(plugin.getResource(fileName)),
                    GeneralSettings.builder().setSerializer(SpigotSerializer.getInstance()).setUseDefaults(false).build(),
                    LoaderSettings.builder().setAutoUpdate(false).build(),
                    DumperSettings.DEFAULT,
                    UpdaterSettings.builder()
                            .setVersioning(new BasicVersioning("file-version"))
                            .build());

            Version version = Objects.requireNonNull(config.getUpdaterSettings().getVersioning().getDocumentVersion(Objects.requireNonNull(config.getDefaults()), true), "Version ID of the defaults cannot be null! Is it malformed or not specified?");
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
                .addIgnoredRoute(this.defaultVersion, ignoredRoute, '.')
                .build());

        if (documentVersion != null && !documentVersion.asID().equals(this.defaultVersion)) {
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
            return YamlDocument.create(Objects.requireNonNull(config.getFile()));
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
        config.getUpdaterSettings().getIgnoredRoutes(this.defaultVersion, config.getGeneralSettings().getRouteSeparator()).forEach((route) -> config.getOptionalBlock(route).ifPresent((block) -> block.setIgnored(true)));

        Merger.merge(config, Objects.requireNonNull(config.getDefaults()), config.getUpdaterSettings());
        try {
            config.save();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
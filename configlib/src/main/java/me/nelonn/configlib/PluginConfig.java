package me.nelonn.configlib;

import org.bukkit.configuration.Configuration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.io.*;

public class PluginConfig extends SectionConfig {
    private final JavaPlugin plugin;
    private final ConfigLoader loader;
    private String resourcePath;
    private File file;

    public PluginConfig(@NotNull JavaPlugin plugin, @NotNull String resourcePath, @NotNull File file, @NotNull ConfigLoader loader) {
        super(null);
        this.plugin = plugin;
        this.resourcePath = resourcePath;
        this.file = file;
        this.loader = loader;
    }

    public PluginConfig(@NotNull JavaPlugin plugin, @NotNull String resourcePath, @NotNull String fileName, @NotNull ConfigLoader loader) {
        this(plugin, resourcePath, new File(plugin.getDataFolder(), fileName), loader);
    }

    public PluginConfig(@NotNull JavaPlugin plugin, @NotNull String fileName, @NotNull ConfigLoader loader) {
        this(plugin, fileName, fileName, loader);
    }

    public PluginConfig(@NotNull JavaPlugin plugin, @NotNull String resourcePath, @NotNull File file) {
        this(plugin, resourcePath, file, YamlConfiguration::loadConfiguration);
    }

    public PluginConfig(@NotNull JavaPlugin plugin, @NotNull String resourcePath, @NotNull String fileName) {
        this(plugin, resourcePath, new File(plugin.getDataFolder(), fileName));
    }

    public PluginConfig(@NotNull JavaPlugin plugin, @NotNull String fileName) {
        this(plugin, fileName, fileName);
    }

    public @NotNull JavaPlugin getPlugin() {
        return plugin;
    }

    public @NotNull String getResourcePath() {
        return resourcePath;
    }

    public void setResourcePath(@NotNull String resourcePath) {
        this.resourcePath = resourcePath;
    }

    public @NotNull File getFile() {
        return file;
    }

    public void setFile(@NotNull File file) {
        this.file = file;
    }

    public void load() {
        PluginResource.extractIfNotExist(plugin, resourcePath, file);
        setRaw(loader.loadConfiguration(file));
        // TODO: match
    }

    @FunctionalInterface
    public interface ConfigLoader {
        @NotNull Configuration loadConfiguration(@NotNull File file);
    }
}

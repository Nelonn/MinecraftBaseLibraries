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
        if (!file.exists()) {
            try (InputStream in = plugin.getResource(resourcePath)) {
                if (in == null) {
                    throw new IllegalArgumentException("The embedded resource '" + resourcePath + "' cannot be found in " + plugin.getName());
                }
                File outDir = file.getParentFile();
                if (!outDir.exists()) {
                    outDir.mkdirs();
                }
                OutputStream out = new FileOutputStream(file);
                byte[] buf = new byte[1024];
                int len;
                while ((len = in.read(buf)) > 0) {
                    out.write(buf, 0, len);
                }
                out.close();
                in.close();
                plugin.getLogger().info("New " + file.getName() + " created");
            } catch (IOException e) {
                plugin.getLogger().severe("Could not save " + resourcePath + " to " + file);
                throw new RuntimeException(e);
            }
        }
        setRaw(loader.loadConfiguration(file));
        // TODO: match
    }

    @FunctionalInterface
    public interface ConfigLoader {
        @NotNull Configuration loadConfiguration(@NotNull File file);
    }
}

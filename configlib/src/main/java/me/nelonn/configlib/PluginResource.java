package me.nelonn.configlib;

import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.io.*;

public class PluginResource {

    public static void extractIfNotExist(@NotNull JavaPlugin plugin, @NotNull String resourcePath, @NotNull String fileName) {
        extractIfNotExist(plugin, resourcePath, new File(plugin.getDataFolder(), fileName));
    }

    public static void extractIfNotExist(@NotNull JavaPlugin plugin, @NotNull String resourcePath, @NotNull File file) {
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
    }
}

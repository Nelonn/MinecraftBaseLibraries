package me.nelonn.pluginlib;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.nio.file.Path;

public class PluginHelper {
    public static void extract(@NotNull String pluginName, @NotNull String resource, @NotNull String... other) {
        new File(pluginName).mkdirs();
        extract(PluginHelper.class, pluginName, resource);
        for (String res : other) {
            extract(PluginHelper.class, pluginName, res);
        }
    }

    private static void extract(@NotNull Class<?> source, @NotNull String pluginName, @NotNull String path) {
        Path formattedPath = Path.of("resources", path);
        File out = Path.of("./", pluginName, formattedPath.getFileName().toString()).toFile();
        if (path.endsWith("/")) {
            JarResources.extractDirectory(source, formattedPath.toString(), out);
        } else {
            JarResources.extractFile(source, formattedPath.toString(), out);
        }
    }
}

package me.nelonn.commandlib.bukkit;

import me.nelonn.commandlib.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

public final class BukkitCommands {
    @SafeVarargs
    public static void register(@NotNull Plugin plugin, @NotNull Command<CommandSender>... commands) {
        BukkitCommandsManager.INSTANCE.register(plugin, commands);
    }

    public static boolean containAlias(@NotNull String name) {
        return BukkitCommandsManager.INSTANCE.containAlias(name);
    }

    public static void unregisterAlias(@NotNull String name) {
        BukkitCommandsManager.INSTANCE.unregisterAlias(name);
    }

    private BukkitCommands() {
        throw new UnsupportedOperationException();
    }
}

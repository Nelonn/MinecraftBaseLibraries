package me.nelonn.commandlib.bukkit;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SafeCommandMap implements CommandMap {
    public static final SafeCommandMap INSTANCE = new SafeCommandMap();
    private final SimpleCommandMap handle;

    private boolean warn() {
        if (handle == null) {
            Bukkit.getLogger().severe("Mandatory CommandLib cannot obtain command map");
            return true;
        } else {
            return false;
        }
    }

    protected SafeCommandMap() {
        SimpleCommandMap simpleCommandMap;
        try {
            simpleCommandMap = (SimpleCommandMap) Bukkit.getServer().getClass().getMethod("getCommandMap").invoke(Bukkit.getServer());
        } catch (Throwable e) {
            simpleCommandMap = null;
        }
        handle = simpleCommandMap;
        warn();
    }

    @Override
    public void registerAll(@NotNull String fallbackPrefix, @NotNull List<Command> commands) {
        if (warn()) return;
        handle.registerAll(fallbackPrefix, commands);
    }

    @Override
    public boolean register(@NotNull String label, @NotNull String fallbackPrefix, @NotNull Command command) {
        if (warn()) return false;
        return handle.register(label, fallbackPrefix, command);
    }

    @Override
    public boolean register(@NotNull String fallbackPrefix, @NotNull Command command) {
        if (warn()) return false;
        return handle.register(fallbackPrefix, command);
    }

    @Override
    public boolean dispatch(@NotNull CommandSender sender, @NotNull String cmdLine) throws CommandException {
        if (warn()) return false;
        return handle.dispatch(sender, cmdLine);
    }

    @Override
    public void clearCommands() {
        if (warn()) return;
        handle.clearCommands();
    }

    @Override
    public @Nullable Command getCommand(@NotNull String name) {
        if (warn()) return null;
        return handle.getCommand(name);
    }

    @Override
    public @Nullable List<String> tabComplete(@NotNull CommandSender sender, @NotNull String cmdLine) throws IllegalArgumentException {
        if (warn()) return null;
        return handle.tabComplete(sender, cmdLine);
    }

    @Override
    public @Nullable List<String> tabComplete(@NotNull CommandSender sender, @NotNull String cmdLine, @Nullable Location location) throws IllegalArgumentException {
        if (warn()) return null;
        return handle.tabComplete(sender, cmdLine, location);
    }

    @Override
    public @NotNull Map<String, Command> getKnownCommands() {
        if (warn()) return new HashMap<>();
        return handle.getKnownCommands();
    }
}

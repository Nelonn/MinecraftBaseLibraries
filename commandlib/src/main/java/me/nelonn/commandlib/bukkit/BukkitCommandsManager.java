package me.nelonn.commandlib.bukkit;

import me.nelonn.commandlib.Command;
import me.nelonn.commandlib.CommandContext;
import me.nelonn.commandlib.suggestion.Suggestions;
import org.bukkit.command.CommandException;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginIdentifiableCommand;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Locale;

public class BukkitCommandsManager {
    public static final BukkitCommandsManager INSTANCE = new BukkitCommandsManager();

    private final SafeCommandMap commandMap = SafeCommandMap.INSTANCE;

    private BukkitCommandsManager() {
    }

    @SafeVarargs
    public final void register(@NotNull Plugin plugin, @NotNull Command<CommandSender>... commands) {
        if (commands.length == 0) return;
        String namespace = plugin.getName().toLowerCase(Locale.ROOT);
        for (Command<CommandSender> command : commands) {
            commandMap.register(command.getName(), namespace, new BukkitWrapper(plugin, command));
        }
    }

    public boolean containAlias(@NotNull String name) {
        return commandMap.getCommand(name) != null;
    }

    public void unregisterAlias(@NotNull String name) {
        commandMap.getKnownCommands().remove(name);
    }

    @FunctionalInterface
    public interface CommandAction<R> {
        R action(@NotNull Command<CommandSender> command, @NotNull CommandContext<CommandSender> context);
    }

    public static <R> R commandAction(@NotNull Command<CommandSender> command,
                                        @NotNull CommandAction<R> action,
                                        @Nullable R failResult,
                                        boolean requireMinimumTwoArguments,
                                        @NotNull CommandSender source,
                                        @NotNull String input,
                                        @NotNull String[] arguments) {
        if (!command.getRequirement().test(source)) {
            return failResult;
        }
        int argQuote = requireMinimumTwoArguments ? 1 : 0;
        if (arguments.length > argQuote) {
            Command<CommandSender> childCommand = null;
            String alias = arguments[0].toLowerCase(Locale.ENGLISH);
            for (Command<CommandSender> child : command.getChildren()) {
                if (child.getName().equals(alias)) {
                    childCommand = child;
                    break;
                }
                boolean matches = false;
                for (String childAlias : child.getAliases()) {
                    if (childAlias.equals(alias)) {
                        matches = true;
                        break;
                    }
                }
                if (matches) {
                    childCommand = child;
                    break;
                }
            }
            if (childCommand != null) {
                String[] subArguments = new String[arguments.length - 1];
                if (subArguments.length > 0) {
                    System.arraycopy(arguments, 1, subArguments, 0, subArguments.length);
                }
                return commandAction(childCommand, action, failResult, requireMinimumTwoArguments, source, input, subArguments);
            }
        }
        return action.action(command, new CommandContext<>(source, input, command, List.of(arguments)));
    }

    public static class BukkitWrapper extends org.bukkit.command.Command implements PluginIdentifiableCommand {
        private final Plugin owningPlugin;
        private final Command<CommandSender> command;

        public BukkitWrapper(@NotNull Plugin owner, @NotNull Command<CommandSender> command) {
            super(command.getName(), "", "", command.getAliases().stream().toList());
            this.owningPlugin = owner;
            this.command = command;
        }

        @Override
        public boolean execute(@NotNull CommandSender sender, @NotNull String alias, @NotNull String[] args) {
            boolean success;

            if (!owningPlugin.isEnabled()) {
                throw new CommandException("Cannot execute command '" + alias + "' in plugin " +
                        owningPlugin.getDescription().getFullName() + " - plugin is disabled.");
            }

            if (!testPermission(sender)) {
                return true;
            }

            try {
                success = commandAction(command, Command::run, false, false, sender, alias, args);
            } catch (Throwable ex) {
                throw new CommandException("Unhandled exception executing command '" + alias + "' in plugin " +
                        owningPlugin.getDescription().getFullName(), ex);
            }

            return success;
        }

        @Override
        @NotNull
        public List<String> tabComplete(@NotNull CommandSender sender, @NotNull String alias, @NotNull String[] args) {
            List<String> completions;
            try {
                completions = commandAction(command, Command::suggest, Suggestions.EMPTY, true, sender, alias, args);
            } catch (Throwable ex) {
                StringBuilder message = new StringBuilder();
                message.append("Unhandled exception during tab completion for command '/").append(alias).append(' ');
                for (String arg : args) {
                    message.append(arg).append(' ');
                }
                message.deleteCharAt(message.length() - 1)
                        .append("' in plugin ")
                        .append(owningPlugin.getDescription().getFullName());
                throw new CommandException(message.toString(), ex);
            }
            if (completions == null) {
                return super.tabComplete(sender, alias, args);
            }
            return completions;
        }

        @Override
        @NotNull
        public Plugin getPlugin() {
            return owningPlugin;
        }

        @NotNull
        public Command<CommandSender> getCommand() {
            return command;
        }
    }

}

package me.nelonn.commandlib;

import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.Collections;

public class CommandContext<S> {
    private final S source;
    private final String input;
    private final Command<S> command;
    private final Collection<String> arguments;

    public CommandContext(@NotNull S source, @NotNull String input, @NotNull Command<S> command, @NotNull Collection<String> arguments) {
        this.source = source;
        this.input = input;
        this.command = command;
        this.arguments = Collections.unmodifiableCollection(arguments);
    }

    public @NotNull S getSource() {
        return source;
    }

    public @NotNull String getInput() {
        return input;
    }

    public @NotNull Command<S> getCommand() {
        return command;
    }

    public String[] getArguments() {
        return arguments.toArray(new String[]{});
    }
}

package me.nelonn.commandlib;

import com.google.errorprone.annotations.ForOverride;
import me.nelonn.commandlib.suggestion.Suggestions;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.function.Predicate;

public abstract class Command<S> {
    private final String name;
    private final Set<String> aliases;
    private Predicate<S> requirement = s -> true;
    private Set<Command<S>> children = Set.of();

    public Command(@NotNull String name, @NotNull String... aliases) {
        this.name = name.toLowerCase(Locale.ENGLISH);
        for (int i = 0; i < aliases.length; i++) {
            aliases[i] = aliases[i].toLowerCase(Locale.ENGLISH);
        }
        this.aliases = Set.of(aliases); // immutable
    }

    public @NotNull Command<S> requires(final @NotNull Predicate<S> requirement) {
        this.requirement = requirement;
        return this;
    }

    @SafeVarargs
    public final @NotNull Command<S> children(final @NotNull Command<S>... children) {
        this.children = Set.of(children);
        return this;
    }

    public @NotNull String getName() {
        return name;
    }

    public @NotNull Set<String> getAliases() {
        return aliases;
    }

    public @NotNull Predicate<S> getRequirement() {
        return requirement;
    }

    public @NotNull Set<Command<S>> getChildren() {
        return children;
    }

    public abstract boolean run(@NotNull CommandContext<S> context);

    @ForOverride
    public @Nullable List<String> suggest(@NotNull CommandContext<S> context) {
        return Suggestions.EMPTY;
    }
}

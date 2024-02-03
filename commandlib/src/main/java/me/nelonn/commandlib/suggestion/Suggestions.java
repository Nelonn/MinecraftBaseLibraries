package me.nelonn.commandlib.suggestion;

import me.nelonn.commandlib.Command;
import me.nelonn.commandlib.CommandContext;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public class Suggestions {
    public static final List<String> EMPTY = Collections.emptyList();
    public static final List<String> SUGGEST_PLAYERS = null;

    public static List<String> simple(@Nullable String cursor, @NotNull Iterable<String> values) {
        String input = cursor == null ? "" : cursor.toLowerCase(Locale.ENGLISH);
        String input2 = RussianKeyboard.ruToEn(input);
        List<String> suggestions = new ArrayList<>();
        for (String value : values) {
            String lower = value.toLowerCase(Locale.ENGLISH);
            if (lower.startsWith(input) || lower.contains(input) || lower.startsWith(input2) || lower.contains(input2)) {
                suggestions.add(value);
            }
        }
        return suggestions;
    }

    public static <S> List<String> children(CommandContext<S> context) {
        if (context.getArguments().length > 1) return EMPTY;
        Set<String> values = new HashSet<>();
        for (Command<S> child : context.getCommand().getChildren()) {
            values.add(child.getName());
            values.addAll(child.getAliases());
        }
        return simple(context.getArguments()[0], values);
    }
}

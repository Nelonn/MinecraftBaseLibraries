package me.nelonn.commandlib.suggestion;

import me.nelonn.commandlib.Command;
import me.nelonn.commandlib.CommandContext;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

public class Suggestions {
    public static final List<String> EMPTY = Collections.emptyList();
    public static final List<String> SUGGEST_PLAYERS = null;

    public static List<String> util(@Nullable String cursor, @NotNull List<String> values) {
        String lower = cursor == null ? "" : cursor.toLowerCase(Locale.ENGLISH);
        String lower2 = RussianKeyboard.ruToEn(lower);
        List<String> suggestions = new ArrayList<>();
        for (String value : values) {
            value = value.toLowerCase(Locale.ENGLISH);
            if (value.startsWith(lower) || value.startsWith(lower2)) {
                suggestions.add(value);
            }
        }
        return suggestions;
    }

    public static <S> List<String> children(CommandContext<S> context) {
        if (context.getArguments().length > 1) return EMPTY;
        List<String> values = new ArrayList<>();
        for (Command<S> child : context.getCommand().getChildren()) {
            values.add(child.getName());
            values.addAll(child.getAliases());
        }
        return util(context.getArguments()[0], values);
    }
}

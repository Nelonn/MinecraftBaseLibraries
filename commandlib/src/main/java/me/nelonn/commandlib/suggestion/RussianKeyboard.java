package me.nelonn.commandlib.suggestion;

import com.google.common.collect.ImmutableBiMap;
import org.jetbrains.annotations.NotNull;

public final class RussianKeyboard {
    public static final ImmutableBiMap<Character, Character> MAPPING;

    public static @NotNull String ruToEn(@NotNull String input) {
        char[] output = input.toCharArray();
        for (int i = 0; i < output.length; i++) {
            Character replace = MAPPING.inverse().get(output[i]);
            if (replace == null) continue;
            output[i] = replace;
        }
        return new String(output);
    }

    static {
        ImmutableBiMap.Builder<Character, Character> builder = ImmutableBiMap.builder();
        builder.put('`', 'ё');
        builder.put('q', 'й');
        builder.put('w', 'ц');
        builder.put('e', 'у');
        builder.put('r', 'к');
        builder.put('t', 'е');
        builder.put('y', 'н');
        builder.put('u', 'г');
        builder.put('i', 'ш');
        builder.put('o', 'щ');
        builder.put('p', 'з');
        builder.put('[', 'х');
        builder.put(']', 'ъ');
        builder.put('a', 'ф');
        builder.put('s', 'ы');
        builder.put('d', 'в');
        builder.put('f', 'а');
        builder.put('g', 'п');
        builder.put('h', 'р');
        builder.put('j', 'о');
        builder.put('k', 'л');
        builder.put('l', 'д');
        builder.put(';', 'ж');
        builder.put('\'', 'э');
        builder.put('z', 'я');
        builder.put('x', 'ч');
        builder.put('c', 'с');
        builder.put('v', 'м');
        builder.put('b', 'и');
        builder.put('n', 'т');
        builder.put('m', 'ь');
        builder.put(',', 'б');
        builder.put('.', 'ю');
        MAPPING = builder.build();
    }
}

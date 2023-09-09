package me.nelonn.configlib;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import org.jetbrains.annotations.NotNull;

public interface MiniMessageText {
    ConfigValue.Deserializer<MiniMessageText> DESERIALIZER =
            obj -> (MiniMessageText) tagResolvers -> MiniMessage.miniMessage().deserialize((String) obj, tagResolvers);

    @NotNull Component accept(final @NotNull TagResolver... tagResolvers);

}
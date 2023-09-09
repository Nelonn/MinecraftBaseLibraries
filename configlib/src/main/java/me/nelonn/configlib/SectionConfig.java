package me.nelonn.configlib;

import org.bukkit.configuration.ConfigurationSection;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class SectionConfig {
    private ConfigurationSection config;

    public SectionConfig(@Nullable ConfigurationSection config) {
        this.config = config;
    }

    public void setRaw(ConfigurationSection config) {
        this.config = config;
    }

    public ConfigurationSection getRaw() {
        return config;
    }

    @Nullable
    public <T> T getNullable(@NotNull ConfigValue<T> configValue) {
        if (config == null) {
            throw new NullPointerException();
        }
        String path = configValue.getPath();
        Object obj = config.get(path);
        if (obj == null) {
            return configValue.getDefaultValue();
        }
        return configValue.getDeserializer().deserialize(obj);
    }

    @NotNull
    public <T> T get(@NotNull ConfigValue<T> configValue) {
        T value = getNullable(configValue);
        if (value == null) {
            throw new NullPointerException("Config value at '" + configValue.getPath() + "' is null");
        }
        return value;
    }
}

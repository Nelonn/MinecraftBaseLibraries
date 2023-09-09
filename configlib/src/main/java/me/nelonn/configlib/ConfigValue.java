package me.nelonn.configlib;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ConfigValue<T> {
    private final String path;
    private final T defaultValue;
    private final Class<T> clazz;
    private final Deserializer<T> deserializer;

    public ConfigValue(@NotNull String path, @Nullable T defaultValue, @Nullable Class<T> clazz, @Nullable Deserializer<T> deserializer) {
        this.path = path;
        this.defaultValue = defaultValue;
        this.clazz = clazz == null ? getOwnGeneric() : clazz;
        this.deserializer = deserializer == null ? new DefaultDeserializer<>() : deserializer;
    }

    public ConfigValue(@NotNull String path, @Nullable T defaultValue, @Nullable Deserializer<T> deserializer) {
        this(path, defaultValue, null, deserializer);
    }

    public ConfigValue(@NotNull String path, @Nullable T defaultValue, @Nullable Class<T> clazz) {
        this(path, defaultValue, clazz, null);
    }

    public ConfigValue(@NotNull String path, @Nullable Deserializer<T> deserializer) {
        this(path, null, deserializer);
    }

    public ConfigValue(@NotNull String path, @Nullable Class<T> clazz) {
        this(path, null, clazz);
    }

    public ConfigValue(@NotNull String path, @Nullable T defaultValue) {
        this(path, defaultValue, (Class<T>) null);
    }

    public ConfigValue(@NotNull String path) {
        this(path, (T) null);
    }

    public @NotNull String getPath() {
        return path;
    }

    public @Nullable T getDefaultValue() {
        return defaultValue;
    }

    public @NotNull Deserializer<T> getDeserializer() {
        return deserializer;
    }

    public @NotNull Class<T> getClazz() {
        return clazz;
    }

    @SuppressWarnings("unchecked")
    private Class<T> getOwnGeneric() {
        return (Class<T>) getClass().getGenericSuperclass().getClass();
    }

    public interface Deserializer<T> {
        @NotNull
        T deserialize(Object obj);
    }

    public static class DefaultDeserializer<T> implements Deserializer<T> {

        @SuppressWarnings("unchecked")
        @Override
        @NotNull
        public T deserialize(Object obj) {
            return (T) obj;
        }

    }
}
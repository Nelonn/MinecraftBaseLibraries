# MinecraftBaseLibraries

Standart libraries for minecraft server plugin


## commandlib Example

```java
public class ExampleCommand extends Command<CommandSender> {
    public ExampleCommand() {
        super("example");
        requires(s -> s.hasPermission("example") && s instanceof Player);
    }

    @Override
    public boolean run(@NotNull CommandContext<CommandSender> context) {
        if (context.getArguments().length == 0) {
            player.sendMessage(Component.text("Example"));
            return false;
        }
        context.getSource().sendMessage(Component
                .text("Example: " + context.getArguments()[0]));
        return true;
    }

    @Override
    public @Nullable List<String> suggest(@NotNull CommandContext<CommandSender> context) {
        if (context.getArguments().length > 1) return Suggestions.EMPTY;
        String cursor = context.getArguments()[0]; // Nullable
        List<String> values = Bukkit.getWorlds().stream().map(World::getName).toList();
        return Suggestions.util(cursor, values);
    }
}
```


## configlib Example

```java
public final class Config {
    public static final ConfigValue<Boolean> featureEnabled = new ConfigValue<>("test.feature_enabled", true);
    public static final ConfigValue<MiniMessageText> message = new ConfigValue<>("message", MiniMessageText.DESERIALIZER);

    private Config() {
        throw new UnsupportedOperationException();
    }
}

public final class ExamplePlugin extends JavaPlugin implements Listener {
    private PluginConfig config;

    @Override
    public void onEnable() {
        config = new PluginConfig(this, "test/config.yml", "config.yml");
        config.load();

        Bukkit.getPluginManager().registerEvents(this, this);
    }
    
    @NotNull
    public PluginConfig config() {
        return config;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        if (!config().get(Config.featureEnabled)) return;
        event.getPlayer().sendMessage(config().get(Config.message).accept());
    }
}
```

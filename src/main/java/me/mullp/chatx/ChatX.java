package me.mullp.chatx;

import me.mullp.chatx.format.ChatTabCompletions;
import me.mullp.chatx.listeners.*;
import org.bstats.bukkit.Metrics;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import javax.annotation.Nullable;

public final class ChatX extends JavaPlugin {
    private static final ChatTabCompletions TAB_COMPLETIONS = new ChatTabCompletions();
    private static ChatX INSTANCE;
    private @Nullable Metrics metrics;

    public static ChatX getInstance() {
        if (INSTANCE == null) {
            INSTANCE = getPlugin(ChatX.class);
        }
        return INSTANCE;
    }

    public static ChatTabCompletions getTabCompletions() {
        return TAB_COMPLETIONS;
    }

    @Override
    public void onEnable() {
        saveDefaultConfig();

        registerEvents();

        TAB_COMPLETIONS.reloadChatCompletions();
        TAB_COMPLETIONS.setChatCompletions(this.getServer().getOnlinePlayers());

        if (metrics == null) {
            metrics = new Metrics(this, 25058);
        }
    }

    @Override
    public void onDisable() {
        HandlerList.unregisterAll(this);
    }

    private void registerEvents() {
        PluginManager pluginManager = this.getServer().getPluginManager();
        pluginManager.registerEvents(new ChatFormatListener(), this);
        pluginManager.registerEvents(new ChatCompletionListener(), this);

        if (getConfig().getBoolean("mentions")) {
            pluginManager.registerEvents(new ChatMentionListener(), this);
        }

        pluginManager.registerEvents(new ChatPlaceholderListener(), this);

        if (!getConfig().getString("join-format", "").isBlank()
                || !getConfig().getString("leave-format", "").isBlank()) {
            pluginManager.registerEvents(new ConnectionListener(), this);
        }

        if (getConfig().getBoolean("proximity-chat")) {
            pluginManager.registerEvents(new ChatProximityListener(), this);
        }
    }
}
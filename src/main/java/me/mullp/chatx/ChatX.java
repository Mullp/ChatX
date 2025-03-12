package me.mullp.chatx;

import me.mullp.chatx.format.TabCompletions;
import me.mullp.chatx.listeners.*;
import org.bstats.bukkit.Metrics;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import javax.annotation.Nullable;

public final class ChatX extends JavaPlugin {
    private static final TabCompletions TAB_COMPLETIONS = new TabCompletions();
    private static ChatX INSTANCE;
    private @Nullable Metrics metrics;

    public static ChatX getInstance() {
        if (INSTANCE == null) {
            INSTANCE = getPlugin(ChatX.class);
        }
        return INSTANCE;
    }

    public static TabCompletions getTabCompletions() {
        return TAB_COMPLETIONS;
    }

    @Override
    public void onEnable() {
        saveDefaultConfig();

        registerEvents();

        TAB_COMPLETIONS.reloadCompletions();
        TAB_COMPLETIONS.setCompletions(this.getServer().getOnlinePlayers());

        metrics = new Metrics(this, 25058);
    }

    @Override
    public void onDisable() {
        HandlerList.unregisterAll(this);

        if (metrics != null) {
            metrics.shutdown();
        }
    }

    private void registerEvents() {
        PluginManager pluginManager = this.getServer().getPluginManager();
        pluginManager.registerEvents(new ChatFormatListener(), this);
        pluginManager.registerEvents(new ChatCompletionListener(), this);

        if (getConfig().getBoolean("mentions", false)) {
            pluginManager.registerEvents(new ChatMentionListener(), this);
        }

        pluginManager.registerEvents(new ChatPlaceholderListener(), this);

        if (!getConfig().getString("join-format", "").isEmpty()
                || !getConfig().getString("leave-format", "").isEmpty()) {
            pluginManager.registerEvents(new ConnectionListener(), this);
        }
    }
}
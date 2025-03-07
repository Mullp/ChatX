package me.mullp.chatx;

import me.mullp.chatx.listeners.ChatCompletionListener;
import me.mullp.chatx.listeners.ChatMentionListener;
import me.mullp.chatx.listeners.ChatFormatListener;
import me.mullp.chatx.format.TabCompletions;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class ChatX extends JavaPlugin {
    private static ChatX INSTANCE;
    private static final TabCompletions TAB_COMPLETIONS = new TabCompletions();

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
    }

    @Override
    public void onDisable() {
        HandlerList.unregisterAll(this);
    }

    private void registerEvents() {
        PluginManager pluginManager = this.getServer().getPluginManager();
        pluginManager.registerEvents(new ChatFormatListener(), this);
        pluginManager.registerEvents(new ChatCompletionListener(), this);

        if (getConfig().getBoolean("mentions", false)) {
            pluginManager.registerEvents(new ChatMentionListener(), this);
        }
    }
}
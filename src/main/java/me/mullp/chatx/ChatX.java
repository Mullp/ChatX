package me.mullp.chatx;

import me.mullp.chatx.listeners.ChatCompletionListener;
import me.mullp.chatx.listeners.ChatFormatListener;
import me.mullp.chatx.tags.TabCompletions;
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

    @Override
    public void onEnable() {
        saveDefaultConfig();

        PluginManager pluginManager = this.getServer().getPluginManager();
        pluginManager.registerEvents(new ChatFormatListener(), this);
        pluginManager.registerEvents(new ChatCompletionListener(), this);

        TAB_COMPLETIONS.reloadCompletions();
    }

    public static TabCompletions getTabCompletions() {
        return TAB_COMPLETIONS;
    }
}
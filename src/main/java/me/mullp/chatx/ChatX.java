package me.mullp.chatx;

import me.mullp.chatx.listeners.ChatFormatListener;
import org.bukkit.plugin.java.JavaPlugin;

public final class ChatX extends JavaPlugin {
    private static ChatX INSTANCE;

    public static ChatX getInstance() {
        if (INSTANCE == null) {
            INSTANCE = getPlugin(ChatX.class);
        }
        return INSTANCE;
    }

    @Override
    public void onEnable() {
        saveDefaultConfig();

        this.getServer().getPluginManager().registerEvents(new ChatFormatListener(), this);
    }
}
package me.mullp.chatx.listeners;

import me.mullp.chatx.ChatX;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.jetbrains.annotations.NotNull;

public class ChatCompletionListener implements Listener {
    @EventHandler
    public void onPlayerJoin(@NotNull PlayerJoinEvent event) {
        ChatX.getTabCompletions().setCompletions(event.getPlayer());
    }
}

package me.mullp.chatx.listeners;

import io.papermc.paper.threadedregions.scheduler.AsyncScheduler;
import me.mullp.chatx.ChatX;
import me.mullp.chatx.format.ChatFormatter;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.concurrent.TimeUnit;

public class ConnectionListener implements Listener {
    private final static ChatX PLUGIN = ChatX.getInstance();

    @EventHandler(priority = EventPriority.HIGH)
    public void onPlayerJoinFormat(PlayerJoinEvent event) {
        String format = PLUGIN.getConfig().getString("join-format", "");
        if (format.isBlank()) {
            return;
        }

        event.joinMessage(ChatFormatter.deserializePlaceholders(format, event.getPlayer()));
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onPlayerLeaveFormat(PlayerQuitEvent event) {
        String format = PLUGIN.getConfig().getString("leave-format", "");
        if (format.isBlank()) {
            return;
        }

        event.quitMessage(ChatFormatter.deserializePlaceholders(format, event.getPlayer()));
    }

    @EventHandler
    public void onPlayerJoinMessage(PlayerJoinEvent event) {
        String format = PLUGIN.getConfig().getString("join-message", "");
        if (format.isBlank()) {
            return;
        }

        Player player = event.getPlayer();

        // Delay message 1 tick to avoid being sent before the global join message
        AsyncScheduler asyncScheduler = PLUGIN.getServer().getAsyncScheduler();
        asyncScheduler.runDelayed(PLUGIN, task -> {
            player.sendMessage(ChatFormatter.deserializePlaceholders(format, player));
        }, 50, TimeUnit.MILLISECONDS);
    }
}

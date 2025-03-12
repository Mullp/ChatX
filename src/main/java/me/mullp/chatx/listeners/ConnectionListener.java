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
    @EventHandler(priority = EventPriority.HIGH)
    public void onPlayerJoinFormat(PlayerJoinEvent event) {
        String format = ChatX.getInstance().getConfig().getString("join-format", "");
        if (format.isEmpty()) {
            return;
        }

        event.joinMessage(ChatFormatter.deserializePlaceholders(format, event.getPlayer()));
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onPlayerLeaveFormat(PlayerQuitEvent event) {
        String format = ChatX.getInstance().getConfig().getString("leave-format", "");
        if (format.isEmpty()) {
            return;
        }

        event.quitMessage(ChatFormatter.deserializePlaceholders(format, event.getPlayer()));
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onPlayerJoinMessage(PlayerJoinEvent event) {
        String format = ChatX.getInstance().getConfig().getString("join-message", "");
        if (format.isEmpty()) {
            return;
        }

        Player player = event.getPlayer();

        // Delay message 1 tick to avoid being sent before the global join message
        AsyncScheduler asyncScheduler = ChatX.getInstance().getServer().getAsyncScheduler();
        asyncScheduler.runDelayed(ChatX.getInstance(), task -> {
            player.sendMessage(ChatFormatter.deserializePlaceholders(format, player));
        }, 50, TimeUnit.MILLISECONDS);
    }
}

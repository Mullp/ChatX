package me.mullp.chatx.listeners;

import io.papermc.paper.chat.ChatRenderer;
import io.papermc.paper.event.player.AsyncChatEvent;
import me.mullp.chatx.ChatX;
import me.mullp.chatx.format.ChatFormatter;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.jetbrains.annotations.NotNull;

public class ChatFormatListener implements Listener, ChatRenderer {
    private final static ChatX PLUGIN = ChatX.getInstance();

    @EventHandler
    public void onChatFormat(@NotNull AsyncChatEvent event) {
        if (PLUGIN.getConfig().getString("format", "").isBlank()) {
            return;
        }

        event.renderer(this);
    }

    @Override
    @NotNull
    public Component render(@NotNull Player source,
                            @NotNull Component sourceDisplayName,
                            @NotNull Component message,
                            @NotNull Audience viewer) {
        String format = PLUGIN.getConfig().getString("format", "<name>: <message>");

        return ChatFormatter.deserializeMessage(format, source, message);
    }
}

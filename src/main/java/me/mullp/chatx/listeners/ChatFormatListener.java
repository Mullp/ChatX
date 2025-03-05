package me.mullp.chatx.listeners;

import io.papermc.paper.chat.ChatRenderer;
import io.papermc.paper.event.player.AsyncChatEvent;
import me.mullp.chatx.ChatX;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import net.kyori.adventure.text.minimessage.tag.standard.StandardTags;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.jetbrains.annotations.NotNull;

public class ChatFormatListener implements Listener, ChatRenderer {
    private final MiniMessage miniMessage = MiniMessage.builder()
            .tags(StandardTags.color())
            .build();

    @EventHandler
    public void onChatFormat(@NotNull AsyncChatEvent event) {
        event.renderer(this);
    }

    @Override
    @NotNull
    public Component render(@NotNull Player source,
                            @NotNull Component sourceDisplayName,
                            @NotNull Component message,
                            @NotNull Audience viewer) {
        String format = ChatX.getInstance().getConfig().getString("format");
        if (format == null) {
            return Component.text()
                    .append(sourceDisplayName)
                    .append(Component.text(": "))
                    .append(message)
                    .build();
        }

        return miniMessage.deserialize(format,
                Placeholder.component("name", sourceDisplayName),
                Placeholder.component("message", message));
    }
}

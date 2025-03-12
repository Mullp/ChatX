package me.mullp.chatx.listeners;

import io.papermc.paper.event.player.AsyncChatDecorateEvent;
import me.mullp.chatx.ChatX;
import me.mullp.chatx.format.ChatFormatter;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.HoverEvent;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

public class ChatPlaceholderListener implements Listener {
    @EventHandler(priority = EventPriority.NORMAL)
    public void onChatDecoratePlaceholder(@NotNull AsyncChatDecorateEvent event) {
        Player source = event.player();
        if (source == null) {
            return;
        }

        Component result = event.result();

        FileConfiguration config = ChatX.getInstance().getConfig();
        for (Map<?, ?> placeholder : config.getMapList("placeholders")) {
            result = result.replaceText(builder -> {
                String name = placeholder.get("name").toString();
                Component replacement = ChatFormatter.deserializePlaceholders(placeholder.get("value").toString(), source);

                if (replacement.hoverEvent() == null) {
                    replacement = replacement.hoverEvent(HoverEvent.showText(Component.text(name)
                            .color(TextColor.color(0xc0c0c0))));
                }

                builder.matchLiteral(name)
                        .replacement(replacement);
            });
        }

        event.result(result);
    }
}

package me.mullp.chatx.listeners;

import io.papermc.paper.event.player.AsyncChatDecorateEvent;
import me.mullp.chatx.ChatX;
import me.mullp.chatx.format.ChatFormatter;
import net.kyori.adventure.text.Component;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

public class ChatReplacementsListener implements Listener {
    @EventHandler(priority = EventPriority.NORMAL)
    public void onChatMention(@NotNull AsyncChatDecorateEvent event) {
        Player source = event.player();
        if (source == null) {
            return;
        }

        Component result = event.result();

        FileConfiguration config = ChatX.getInstance().getConfig();
        for (Map<?, ?> placeholder : config.getMapList("replacements")) {
            result = result.replaceText(builder -> builder
                    .matchLiteral(placeholder.get("name").toString())
                    .replacement(ChatFormatter.deserializePlaceholders(placeholder.get("value").toString(), source)));
        }

        event.result(result);
    }
}

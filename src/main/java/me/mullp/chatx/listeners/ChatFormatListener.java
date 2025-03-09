package me.mullp.chatx.listeners;

import io.papermc.paper.chat.ChatRenderer;
import io.papermc.paper.event.player.AsyncChatEvent;
import me.mullp.chatx.ChatX;
import me.mullp.chatx.format.ChatFormatter;
import me.mullp.chatx.format.placeholders.ItemPlaceholder;
import me.mullp.chatx.format.placeholders.MessagePlaceholder;
import me.mullp.chatx.format.placeholders.NamePlaceholder;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.tag.standard.StandardTags;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.jetbrains.annotations.NotNull;

public class ChatFormatListener implements Listener, ChatRenderer {
    private final MiniMessage miniMessage = MiniMessage.builder()
            .tags(StandardTags.defaults())
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
        FileConfiguration config = ChatX.getInstance().getConfig();

        String format = config.getString("format", "<name>: <message>");

        return ChatFormatter.deserializeMessage(format, source, message);
//        return getDeserialized(format, source, sourceDisplayName, message);
    }

//    private @NotNull Component getDeserialized(@NotNull String format, @NotNull Player source, @NotNull Component sourceDisplayName, @NotNull Component message) {
//        return miniMessage.deserialize(format,
//                new NamePlaceholder(sourceDisplayName),
//                new ItemPlaceholder(source.getInventory().getItemInMainHand()),
//                new MessagePlaceholder(message));
//    }
}

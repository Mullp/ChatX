package me.mullp.chatx.tags.placeholders;

import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;

public class MessagePlaceholder extends AbstractPlaceholder {
    public MessagePlaceholder(@NotNull Component component) {
        super("message", component);
    }
}

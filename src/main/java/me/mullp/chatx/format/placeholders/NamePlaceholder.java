package me.mullp.chatx.format.placeholders;

import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class NamePlaceholder extends AbstractPlaceholder {
    public NamePlaceholder(@NotNull Component component) {
        super("name", component);
    }

    public NamePlaceholder(@NotNull Player player) {
        this(Component.text(player.getName()));
    }
}

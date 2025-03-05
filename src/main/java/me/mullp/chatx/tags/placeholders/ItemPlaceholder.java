package me.mullp.chatx.tags.placeholders;

import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public class ItemPlaceholder extends AbstractPlaceholder {
    public ItemPlaceholder(@NotNull Component component) {
        super("item", component);
    }

    public ItemPlaceholder(@NotNull ItemStack itemStack) {
        this(Component.text()
                .append(itemStack.effectiveName())
                .hoverEvent(itemStack)
                .build());
    }

    public ItemPlaceholder(@NotNull Player player) {
        this(player.getInventory().getItemInMainHand());
    }
}
